package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.AuditChecklistEmbeddedKey;
import com.datawise.satisactual.entities.master.AuditChecklistEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.AuditChecklistDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.AuditChecklistRepository;
import com.datawise.satisactual.service.master.CommonMasterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@RequestMapping("/mst/audit-check-list")
@CrossOrigin
public class AuditChecklistController {

    @Autowired
    private CommonMasterService<AuditChecklistEntity, AuditChecklistEmbeddedKey, AuditChecklistDTO> service;
    @Autowired
    private AuditChecklistRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<AuditChecklistDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, AuditChecklistDTO.class));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<AuditChecklistDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, AuditChecklistDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody AuditChecklistDTO dto) {
        service.save(
                repository,
                dto,
                AuditChecklistEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAuditItem()),
                dto.getCodAuditItem(),
                mapper,
                this.id.apply(dto.getCodAuditItem(), CodRecordStatus.N),
                this.id.apply(dto.getCodAuditItem(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody AuditChecklistDTO dto) {
        service.update(
                repository,
                dto,
                AuditChecklistEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAuditItem()),
                dto.getCodAuditItem(),
                mapper,
                this.id.apply(dto.getCodAuditItem(), CodRecordStatus.M),
                this.id.apply(dto.getCodAuditItem(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                AuditChecklistDTO.class,
                AuditChecklistEntity.class,
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
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.reopen(
                repository,
                AuditChecklistDTO.class,
                AuditChecklistEntity.class,
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
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.authorize(
                repository,
                AuditChecklistDTO.class,
                AuditChecklistEntity.class,
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
        TypeMap<AuditChecklistEntity, AuditChecklistDTO> dtoMap = mapper.createTypeMap(AuditChecklistEntity.class, AuditChecklistDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodAuditItem(), AuditChecklistDTO::setCodAuditItem);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), AuditChecklistDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, AuditChecklistDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, AuditChecklistDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, AuditChecklistDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, AuditChecklistDTO::setLastCheckerDateTime);

        TypeMap<AuditChecklistDTO, AuditChecklistEntity> entityMap = mapper.createTypeMap(AuditChecklistDTO.class, AuditChecklistEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, AuditChecklistEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, AuditChecklistEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, AuditChecklistEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, AuditChecklistEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, AuditChecklistEmbeddedKey> id = (code, status) -> AuditChecklistEmbeddedKey.builder().codAuditItem(code).codRecordStatus(status.name()).build();

    private Specification<AuditChecklistEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codAuditItem"));
    }
}
