package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.AuditFindingsTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.AuditFindingsTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.AuditFindingsTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.AuditFindingTypeRepository;
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
@RequestMapping("/mst/audit-finding-type")
public class AuditFindingTypeController {

    @Autowired
    private CommonMasterService<AuditFindingsTypeEntity, AuditFindingsTypeEmbeddedKey, AuditFindingsTypeDTO> service;
    @Autowired
    private AuditFindingTypeRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<AuditFindingsTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, AuditFindingsTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditFindingsTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, AuditFindingsTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody AuditFindingsTypeDTO dto) {
        service.save(
                repository,
                dto,
                AuditFindingsTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAuditFindingType()),
                dto.getCodAuditFindingType(),
                mapper,
                this.id.apply(dto.getCodAuditFindingType(), CodRecordStatus.N),
                this.id.apply(dto.getCodAuditFindingType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody AuditFindingsTypeDTO dto) {
        service.update(
                repository,
                dto,
                AuditFindingsTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAuditFindingType()),
                dto.getCodAuditFindingType(),
                mapper,
                this.id.apply(dto.getCodAuditFindingType(), CodRecordStatus.M),
                this.id.apply(dto.getCodAuditFindingType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                AuditFindingsTypeDTO.class,
                AuditFindingsTypeEntity.class,
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
                AuditFindingsTypeDTO.class,
                AuditFindingsTypeEntity.class,
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
                AuditFindingsTypeDTO.class,
                AuditFindingsTypeEntity.class,
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
        TypeMap<AuditFindingsTypeEntity, AuditFindingsTypeDTO> dtoMap = mapper.createTypeMap(AuditFindingsTypeEntity.class, AuditFindingsTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodAuditFindingType(), AuditFindingsTypeDTO::setCodAuditFindingType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), AuditFindingsTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, AuditFindingsTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, AuditFindingsTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, AuditFindingsTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, AuditFindingsTypeDTO::setLastCheckerDateTime);

        TypeMap<AuditFindingsTypeDTO, AuditFindingsTypeEntity> entityMap = mapper.createTypeMap(AuditFindingsTypeDTO.class, AuditFindingsTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, AuditFindingsTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, AuditFindingsTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, AuditFindingsTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, AuditFindingsTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, AuditFindingsTypeEmbeddedKey> id = (code, status) -> AuditFindingsTypeEmbeddedKey.builder().codAuditFindingType(code).codRecordStatus(status.name()).build();

    private Specification<AuditFindingsTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codAuditFindingType"));
    }
}
