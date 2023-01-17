package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.OrgTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.OrgTypeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.OrgTypeDTO;
import com.datawise.satisactual.repository.master.OrgTypeRepository;
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
@RequestMapping("/mst/org-type")
public class OrgTypeController {

    @Autowired
    private CommonMasterService<OrgTypeEntity, OrgTypeEmbeddedKey, OrgTypeDTO> service;
    @Autowired
    private OrgTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<OrgTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, OrgTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, OrgTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody OrgTypeDTO dto) {
        service.save(
                repository,
                dto,
                OrgTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodOrganizationType()),
                dto.getCodOrganizationType(),
                mapper,
                this.id.apply(dto.getCodOrganizationType(), CodRecordStatus.N),
                this.id.apply(dto.getCodOrganizationType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody OrgTypeDTO dto) {
        service.update(
                repository,
                dto,
                OrgTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodOrganizationType()),
                dto.getCodOrganizationType(),
                mapper,
                this.id.apply(dto.getCodOrganizationType(), CodRecordStatus.M),
                this.id.apply(dto.getCodOrganizationType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                OrgTypeDTO.class,
                OrgTypeEntity.class,
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
                OrgTypeDTO.class,
                OrgTypeEntity.class,
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
                OrgTypeDTO.class,
                OrgTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<OrgTypeEntity, OrgTypeDTO> dtoMap = mapper.createTypeMap(OrgTypeEntity.class, OrgTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodOrganizationType(), OrgTypeDTO::setCodOrganizationType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), OrgTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, OrgTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, OrgTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, OrgTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, OrgTypeDTO::setLastCheckerDateTime);

        TypeMap<OrgTypeDTO, OrgTypeEntity> entityMap = mapper.createTypeMap(OrgTypeDTO.class, OrgTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, OrgTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, OrgTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, OrgTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, OrgTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, OrgTypeEmbeddedKey> id = (code, status) -> OrgTypeEmbeddedKey.builder().codOrganizationType(code).codRecordStatus(status.name()).build();

    private Specification<OrgTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codOrganizationType"));
    }
}
