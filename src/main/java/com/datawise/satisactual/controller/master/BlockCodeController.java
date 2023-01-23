package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.BlockCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.BlockCodeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.BlockCodeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.BlockCodeRepository;
import com.datawise.satisactual.service.master.CommonMasterService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

@Validated
@RestController
@RequestMapping("/mst/block-code")
public class BlockCodeController {

    @Autowired
    private CommonMasterService<BlockCodeEntity, BlockCodeEmbeddedKey, BlockCodeDTO> service;
    @Autowired
    private BlockCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<BlockCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, BlockCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlockCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, BlockCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody BlockCodeDTO dto) {
        service.save(
                repository,
                dto,
                BlockCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodBlock()),
                dto.getCodBlock(),
                mapper,
                this.id.apply(dto.getCodBlock(), CodRecordStatus.N),
                this.id.apply(dto.getCodBlock(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody BlockCodeDTO dto) {
        service.update(
                repository,
                dto,
                BlockCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodBlock()),
                dto.getCodBlock(),
                mapper,
                this.id.apply(dto.getCodBlock(), CodRecordStatus.M),
                this.id.apply(dto.getCodBlock(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("id") String id) {
        service.delete(
                repository,
                BlockCodeDTO.class,
                BlockCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.X),
                this.id.apply(id, CodRecordStatus.C),
                this.id.apply(id, CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}")
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("id") String id) {
        service.reopen(
                repository,
                BlockCodeDTO.class,
                BlockCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.R),
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("id") String id) {
        service.authorize(
                repository,
                BlockCodeDTO.class,
                BlockCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.R, CodRecordStatus.X, CodRecordStatus.M, CodRecordStatus.N), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<BlockCodeEntity, BlockCodeDTO> dtoMap = mapper.createTypeMap(BlockCodeEntity.class, BlockCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodBlock(), BlockCodeDTO::setCodBlock);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), BlockCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, BlockCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, BlockCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, BlockCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, BlockCodeDTO::setLastCheckerDateTime);

        TypeMap<BlockCodeDTO, BlockCodeEntity> entityMap = mapper.createTypeMap(BlockCodeDTO.class, BlockCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, BlockCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, BlockCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, BlockCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, BlockCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, BlockCodeEmbeddedKey> id = (code, status) -> BlockCodeEmbeddedKey.builder().codBlock(code).codRecordStatus(status.name()).build();

    private Specification<BlockCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codBlock"));
    }
}
