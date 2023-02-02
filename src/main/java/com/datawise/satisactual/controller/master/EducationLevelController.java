package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.EducationLevelEmbeddedKey;
import com.datawise.satisactual.entities.master.EducationLevelEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.EducationLevelDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.EducationLevelRepository;
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
@RequestMapping("/mst/education-level")
@CrossOrigin
public class EducationLevelController {

    @Autowired
    private CommonMasterService<EducationLevelEntity, EducationLevelEmbeddedKey, EducationLevelDTO> service;
    @Autowired
    private EducationLevelRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<EducationLevelDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, EducationLevelDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationLevelDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, EducationLevelDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody EducationLevelDTO dto) {
        service.save(
                repository,
                dto,
                EducationLevelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodEducationLevel()),
                dto.getCodEducationLevel(),
                mapper,
                this.id.apply(dto.getCodEducationLevel(), CodRecordStatus.N),
                this.id.apply(dto.getCodEducationLevel(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody EducationLevelDTO dto) {
        service.update(
                repository,
                dto,
                EducationLevelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodEducationLevel()),
                dto.getCodEducationLevel(),
                mapper,
                this.id.apply(dto.getCodEducationLevel(), CodRecordStatus.M),
                this.id.apply(dto.getCodEducationLevel(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                EducationLevelDTO.class,
                EducationLevelEntity.class,
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
                EducationLevelDTO.class,
                EducationLevelEntity.class,
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
                EducationLevelDTO.class,
                EducationLevelEntity.class,
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
        TypeMap<EducationLevelEntity, EducationLevelDTO> dtoMap = mapper.createTypeMap(EducationLevelEntity.class, EducationLevelDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodEducationLevel(), EducationLevelDTO::setCodEducationLevel);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), EducationLevelDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, EducationLevelDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, EducationLevelDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, EducationLevelDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, EducationLevelDTO::setLastCheckerDateTime);

        TypeMap<EducationLevelDTO, EducationLevelEntity> entityMap = mapper.createTypeMap(EducationLevelDTO.class, EducationLevelEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, EducationLevelEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, EducationLevelEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, EducationLevelEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, EducationLevelEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, EducationLevelEmbeddedKey> id = (code, status) -> EducationLevelEmbeddedKey.builder().codEducationLevel(code).codRecordStatus(status.name()).build();

    private Specification<EducationLevelEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codEducationLevel"));
    }
}
