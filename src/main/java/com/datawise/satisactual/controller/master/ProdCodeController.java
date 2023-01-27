package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.ProdCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.ProdCodeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.ProdCodeDTO;
import com.datawise.satisactual.repository.master.ProdCodeRepository;
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
@RequestMapping("/mst/prod-code")
public class ProdCodeController {

    @Autowired
    private CommonMasterService<ProdCodeEntity, ProdCodeEmbeddedKey, ProdCodeDTO> service;
    @Autowired
    private ProdCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ProdCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ProdCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, ProdCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ProdCodeDTO dto) {
        service.save(
                repository,
                dto,
                ProdCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodProduct()),
                dto.getCodProduct(),
                mapper,
                this.id.apply(dto.getCodProduct(), CodRecordStatus.N),
                this.id.apply(dto.getCodProduct(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ProdCodeDTO dto) {
        service.update(
                repository,
                dto,
                ProdCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodProduct()),
                dto.getCodProduct(),
                mapper,
                this.id.apply(dto.getCodProduct(), CodRecordStatus.M),
                this.id.apply(dto.getCodProduct(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                ProdCodeDTO.class,
                ProdCodeEntity.class,
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
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.reopen(
                repository,
                ProdCodeDTO.class,
                ProdCodeEntity.class,
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
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.authorize(
                repository,
                ProdCodeDTO.class,
                ProdCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<ProdCodeEntity, ProdCodeDTO> dtoMap = mapper.createTypeMap(ProdCodeEntity.class, ProdCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodProduct(), ProdCodeDTO::setCodProduct);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ProdCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ProdCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ProdCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ProdCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ProdCodeDTO::setLastCheckerDateTime);

        TypeMap<ProdCodeDTO, ProdCodeEntity> entityMap = mapper.createTypeMap(ProdCodeDTO.class, ProdCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ProdCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ProdCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ProdCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ProdCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, ProdCodeEmbeddedKey> id = (code, status) -> ProdCodeEmbeddedKey.builder().codProduct(code).codRecordStatus(status.name()).build();

    private Specification<ProdCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codProduct"));
    }
}
