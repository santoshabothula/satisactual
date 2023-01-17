package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.AssignmentTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.AssignmentTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.AssignmentTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.AssignmentTypeRepository;
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
@RequestMapping("/mst/assignment-type")
public class AssignmentTypeController {

    @Autowired
    private CommonMasterService<AssignmentTypeEntity, AssignmentTypeEmbeddedKey, AssignmentTypeDTO> service;
    @Autowired
    private AssignmentTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<AssignmentTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, AssignmentTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, AssignmentTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody AssignmentTypeDTO dto) {
        service.save(
                repository,
                dto,
                AssignmentTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAssignmentType()),
                dto.getCodAssignmentType(),
                mapper,
                this.id.apply(dto.getCodAssignmentType(), CodRecordStatus.N),
                this.id.apply(dto.getCodAssignmentType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody AssignmentTypeDTO dto) {
        service.update(
                repository,
                dto,
                AssignmentTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAssignmentType()),
                dto.getCodAssignmentType(),
                mapper,
                this.id.apply(dto.getCodAssignmentType(), CodRecordStatus.M),
                this.id.apply(dto.getCodAssignmentType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                AssignmentTypeDTO.class,
                AssignmentTypeEntity.class,
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
                AssignmentTypeDTO.class,
                AssignmentTypeEntity.class,
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
                AssignmentTypeDTO.class,
                AssignmentTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.R, CodRecordStatus.X, CodRecordStatus.C, CodRecordStatus.M, CodRecordStatus.N), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<AssignmentTypeEntity, AssignmentTypeDTO> dtoMap = mapper.createTypeMap(AssignmentTypeEntity.class, AssignmentTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodAssignmentType(), AssignmentTypeDTO::setCodAssignmentType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), AssignmentTypeDTO::setCodAssignmentType);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, AssignmentTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, AssignmentTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, AssignmentTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, AssignmentTypeDTO::setLastCheckerDateTime);

        TypeMap<AssignmentTypeDTO, AssignmentTypeEntity> entityMap = mapper.createTypeMap(AssignmentTypeDTO.class, AssignmentTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, AssignmentTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, AssignmentTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, AssignmentTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, AssignmentTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, AssignmentTypeEmbeddedKey> id = (code, status) -> AssignmentTypeEmbeddedKey.builder().codAssignmentType(code).codRecordStatus(status.name()).build();

    private Specification<AssignmentTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codAssignmentType"));
    }
}
