package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.EmployerCategoryEmbeddedKey;
import com.datawise.satisactual.entities.master.EmployerCategoryEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.EmployerCategoryDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.EmployerCategoryRepository;
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
@RequestMapping("/mst/employer-category")
public class EmployerCategoryController {

    @Autowired
    private CommonMasterService<EmployerCategoryEntity, EmployerCategoryEmbeddedKey, EmployerCategoryDTO> service;
    @Autowired
    private EmployerCategoryRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<EmployerCategoryDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, EmployerCategoryDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerCategoryDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, EmployerCategoryDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody EmployerCategoryDTO dto) {
        service.save(
                repository,
                dto,
                EmployerCategoryEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodEmployerCategory()),
                dto.getCodEmployerCategory(),
                mapper,
                this.id.apply(dto.getCodEmployerCategory(), CodRecordStatus.N),
                this.id.apply(dto.getCodEmployerCategory(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody EmployerCategoryDTO dto) {
        service.update(
                repository,
                dto,
                EmployerCategoryEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodEmployerCategory()),
                dto.getCodEmployerCategory(),
                mapper,
                this.id.apply(dto.getCodEmployerCategory(), CodRecordStatus.M),
                this.id.apply(dto.getCodEmployerCategory(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                EmployerCategoryDTO.class,
                EmployerCategoryEntity.class,
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
                EmployerCategoryDTO.class,
                EmployerCategoryEntity.class,
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
                EmployerCategoryDTO.class,
                EmployerCategoryEntity.class,
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
        TypeMap<EmployerCategoryEntity, EmployerCategoryDTO> dtoMap = mapper.createTypeMap(EmployerCategoryEntity.class, EmployerCategoryDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodEmployerCategory(), EmployerCategoryDTO::setCodEmployerCategory);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), EmployerCategoryDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, EmployerCategoryDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, EmployerCategoryDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, EmployerCategoryDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, EmployerCategoryDTO::setLastCheckerDateTime);

        TypeMap<EmployerCategoryDTO, EmployerCategoryEntity> entityMap = mapper.createTypeMap(EmployerCategoryDTO.class, EmployerCategoryEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, EmployerCategoryEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, EmployerCategoryEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, EmployerCategoryEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, EmployerCategoryEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, EmployerCategoryEmbeddedKey> id = (code, status) -> EmployerCategoryEmbeddedKey.builder().codEmployerCategory(code).codRecordStatus(status.name()).build();

    private Specification<EmployerCategoryEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codEmployerCategory"));
    }
}
