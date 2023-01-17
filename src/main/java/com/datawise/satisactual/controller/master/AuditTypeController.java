package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.AuditTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.AuditTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.AuditTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.AuditTypeRepository;
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
@RequestMapping("/mst/audit-type")
public class AuditTypeController {

    @Autowired
    private CommonMasterService<AuditTypeEntity, AuditTypeEmbeddedKey, AuditTypeDTO> service;
    @Autowired
    private AuditTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<AuditTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, AuditTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, AuditTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody AuditTypeDTO dto) {
        service.save(
                repository,
                dto,
                AuditTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAuditType()),
                dto.getCodAuditType(),
                mapper,
                this.id.apply(dto.getCodAuditType(), CodRecordStatus.N),
                this.id.apply(dto.getCodAuditType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody AuditTypeDTO dto) {
        service.update(
                repository,
                dto,
                AuditTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAuditType()),
                dto.getCodAuditType(),
                mapper,
                this.id.apply(dto.getCodAuditType(), CodRecordStatus.M),
                this.id.apply(dto.getCodAuditType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                AuditTypeDTO.class,
                AuditTypeEntity.class,
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
                AuditTypeDTO.class,
                AuditTypeEntity.class,
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
                AuditTypeDTO.class,
                AuditTypeEntity.class,
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
        TypeMap<AuditTypeEntity, AuditTypeDTO> dtoMap = mapper.createTypeMap(AuditTypeEntity.class, AuditTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodAuditType(), AuditTypeDTO::setCodAuditType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), AuditTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, AuditTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, AuditTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, AuditTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, AuditTypeDTO::setLastCheckerDateTime);

        TypeMap<AuditTypeDTO, AuditTypeEntity> entityMap = mapper.createTypeMap(AuditTypeDTO.class, AuditTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, AuditTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, AuditTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, AuditTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, AuditTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, AuditTypeEmbeddedKey> id = (code, status) -> AuditTypeEmbeddedKey.builder().codAuditType(code).codRecordStatus(status.name()).build();

    private Specification<AuditTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codAuditType"));
    }
}
