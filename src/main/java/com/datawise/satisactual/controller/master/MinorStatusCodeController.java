package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.MinorStatusCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.MinorStatusCodeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.MinorStatusCodeDTO;
import com.datawise.satisactual.repository.master.MinorStatusCodeRepository;
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
@RequestMapping("/mst/minor-status-code")
@CrossOrigin
public class MinorStatusCodeController {

    @Autowired
    private CommonMasterService<MinorStatusCodeEntity, MinorStatusCodeEmbeddedKey, MinorStatusCodeDTO> service;
    @Autowired
    private MinorStatusCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<MinorStatusCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, MinorStatusCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MinorStatusCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, MinorStatusCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody MinorStatusCodeDTO dto) {
        service.save(
                repository,
                dto,
                MinorStatusCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodStatus()),
                dto.getCodStatus(),
                mapper,
                this.id.apply(dto.getCodStatus(), CodRecordStatus.N),
                this.id.apply(dto.getCodStatus(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody MinorStatusCodeDTO dto) {
        service.update(
                repository,
                dto,
                MinorStatusCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodStatus()),
                dto.getCodStatus(),
                mapper,
                this.id.apply(dto.getCodStatus(), CodRecordStatus.M),
                this.id.apply(dto.getCodStatus(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                MinorStatusCodeDTO.class,
                MinorStatusCodeEntity.class,
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
                MinorStatusCodeDTO.class,
                MinorStatusCodeEntity.class,
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
                MinorStatusCodeDTO.class,
                MinorStatusCodeEntity.class,
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
        TypeMap<MinorStatusCodeEntity, MinorStatusCodeDTO> dtoMap = mapper.createTypeMap(MinorStatusCodeEntity.class, MinorStatusCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodStatus(), MinorStatusCodeDTO::setCodStatus);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), MinorStatusCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, MinorStatusCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, MinorStatusCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, MinorStatusCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, MinorStatusCodeDTO::setLastCheckerDateTime);

        TypeMap<MinorStatusCodeDTO, MinorStatusCodeEntity> entityMap = mapper.createTypeMap(MinorStatusCodeDTO.class, MinorStatusCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, MinorStatusCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, MinorStatusCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, MinorStatusCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, MinorStatusCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, MinorStatusCodeEmbeddedKey> id = (code, status) -> MinorStatusCodeEmbeddedKey.builder().codStatus(code).codRecordStatus(status.name()).build();

    private Specification<MinorStatusCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codStatus"));
    }
}
