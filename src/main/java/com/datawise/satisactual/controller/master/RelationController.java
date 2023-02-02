package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.RelationEmbeddedKey;
import com.datawise.satisactual.entities.master.RelationEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.RelationDTO;
import com.datawise.satisactual.repository.master.RelationRepository;
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

@Validated
@RestController
@RequestMapping("/mst/relation")
@CrossOrigin
public class RelationController {

    @Autowired
    private CommonMasterService<RelationEntity, RelationEmbeddedKey, RelationDTO> service;
    @Autowired
    private RelationRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<RelationDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, RelationDTO.class));
    }

    @GetMapping("/{relation}/{relation_type}")
    public ResponseEntity<RelationDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("relation") String relation,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("relation_type") String relationType
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(relation, CodRecordStatus.A, relationType), mapper, RelationDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody RelationDTO dto) {
        service.save(
                repository,
                dto,
                RelationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodRelation(), dto.getRelationType()),
                dto.getCodRelation() + ";" + dto.getRelationType(),
                mapper,
                this.id(dto.getCodRelation(), CodRecordStatus.N, dto.getRelationType()),
                this.id(dto.getCodRelation(), CodRecordStatus.A, dto.getRelationType())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody RelationDTO dto) {
        service.update(
                repository,
                dto,
                RelationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodRelation(), dto.getRelationType()),
                dto.getCodRelation() + ";" + dto.getRelationType(),
                mapper,
                this.id(dto.getCodRelation(), CodRecordStatus.M, dto.getRelationType()),
                this.id(dto.getCodRelation(), CodRecordStatus.A, dto.getRelationType())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{relation}/{relation_type}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("relation") String relation,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("relation_type") String relationType
    ) {
        service.delete(
                repository,
                RelationDTO.class,
                RelationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), relation, relationType), relation + ";" + relationType,
                mapper,
                this.id(relation, CodRecordStatus.X, relationType),
                this.id(relation, CodRecordStatus.C, relationType),
                this.id(relation, CodRecordStatus.A, relationType)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{relation}/{relation_type}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("relation") String relation,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("relation_type") String relationType
    ) {
        service.reopen(
                repository,
                RelationDTO.class,
                RelationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), relation, relationType), relation + ";" + relationType,
                mapper,
                this.id(relation, CodRecordStatus.R, relationType),
                this.id(relation, CodRecordStatus.A, relationType),
                this.id(relation, CodRecordStatus.C, relationType)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{relation}/{relation_type}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("relation") String relation,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("relation_type") String relationType
    ) {
        service.authorize(
                repository,
                RelationDTO.class,
                RelationEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), relation, relationType), relation + ";" + relationType,
                mapper,
                this.id(relation, CodRecordStatus.A, relationType),
                this.id(relation, CodRecordStatus.C, relationType)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<RelationEntity, RelationDTO> dtoMap = mapper.createTypeMap(RelationEntity.class, RelationDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodRelation(), RelationDTO::setCodRelation);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), RelationDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getRelationType(), RelationDTO::setRelationType);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, RelationDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, RelationDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, RelationDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, RelationDTO::setLastCheckerDateTime);

        TypeMap<RelationDTO, RelationEntity> entityMap = mapper.createTypeMap(RelationDTO.class, RelationEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, RelationEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, RelationEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, RelationEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, RelationEntity::setLastCheckerDateTime);
    }

    private RelationEmbeddedKey id(String relation, CodRecordStatus status, String relationType) {
        return RelationEmbeddedKey.builder().codRelation(relation).codRecordStatus(status.name()).relationType(relationType).build();
    }

    private Specification<RelationEntity> getSpec(List<CodRecordStatus> statuses, String relation, String relationType) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(relation, "codRelation"))
                .and(repository.findRecordWithCode(relationType, "relationType"));
    }
}
