package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.PrioritySectorCategoryEmbeddedKey;
import com.datawise.satisactual.entities.master.PrioritySectorCategoryEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.PrioritySectorCategoryDTO;
import com.datawise.satisactual.repository.master.PrioritySectorCategoryRepository;
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
@RequestMapping("/mst/priority-sector-category")
public class PrioritySectorCategoryController {

    @Autowired
    private CommonMasterService<PrioritySectorCategoryEntity, PrioritySectorCategoryEmbeddedKey, PrioritySectorCategoryDTO> service;
    @Autowired
    private PrioritySectorCategoryRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<PrioritySectorCategoryDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, PrioritySectorCategoryDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrioritySectorCategoryDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, PrioritySectorCategoryDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody PrioritySectorCategoryDTO dto) {
        service.save(
                repository,
                dto,
                PrioritySectorCategoryEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPrioritySectorCategory()),
                dto.getCodPrioritySectorCategory(),
                mapper,
                this.id.apply(dto.getCodPrioritySectorCategory(), CodRecordStatus.N),
                this.id.apply(dto.getCodPrioritySectorCategory(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody PrioritySectorCategoryDTO dto) {
        service.update(
                repository,
                dto,
                PrioritySectorCategoryEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPrioritySectorCategory()),
                dto.getCodPrioritySectorCategory(),
                mapper,
                this.id.apply(dto.getCodPrioritySectorCategory(), CodRecordStatus.M),
                this.id.apply(dto.getCodPrioritySectorCategory(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                PrioritySectorCategoryDTO.class,
                PrioritySectorCategoryEntity.class,
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
                PrioritySectorCategoryDTO.class,
                PrioritySectorCategoryEntity.class,
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
                PrioritySectorCategoryDTO.class,
                PrioritySectorCategoryEntity.class,
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
        TypeMap<PrioritySectorCategoryEntity, PrioritySectorCategoryDTO> dtoMap = mapper.createTypeMap(PrioritySectorCategoryEntity.class, PrioritySectorCategoryDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodPrioritySectorCategory(), PrioritySectorCategoryDTO::setCodPrioritySectorCategory);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), PrioritySectorCategoryDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, PrioritySectorCategoryDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, PrioritySectorCategoryDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, PrioritySectorCategoryDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, PrioritySectorCategoryDTO::setLastCheckerDateTime);

        TypeMap<PrioritySectorCategoryDTO, PrioritySectorCategoryEntity> entityMap = mapper.createTypeMap(PrioritySectorCategoryDTO.class, PrioritySectorCategoryEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, PrioritySectorCategoryEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, PrioritySectorCategoryEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, PrioritySectorCategoryEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, PrioritySectorCategoryEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, PrioritySectorCategoryEmbeddedKey> id = (code, status) -> PrioritySectorCategoryEmbeddedKey.builder().codPrioritySectorCategory(code).codRecordStatus(status.name()).build();

    private Specification<PrioritySectorCategoryEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codPrioritySectorCategory"));
    }
}
