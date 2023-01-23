package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CenterTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CenterTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CenterTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CenterTypeRepository;
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
@RequestMapping("/mst/center-type")
public class CenterTypeController {

    @Autowired
    private CommonMasterService<CenterTypeEntity, CenterTypeEmbeddedKey, CenterTypeDTO> service;
    @Autowired
    private CenterTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CenterTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CenterTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CenterTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, CenterTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CenterTypeDTO dto) {
        service.save(
                repository,
                dto,
                CenterTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCenterType()),
                dto.getCodCenterType(),
                mapper,
                this.id.apply(dto.getCodCenterType(), CodRecordStatus.N),
                this.id.apply(dto.getCodCenterType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CenterTypeDTO dto) {
        service.update(
                repository,
                dto,
                CenterTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCenterType()),
                dto.getCodCenterType(),
                mapper,
                this.id.apply(dto.getCodCenterType(), CodRecordStatus.M),
                this.id.apply(dto.getCodCenterType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                CenterTypeDTO.class,
                CenterTypeEntity.class,
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
                CenterTypeDTO.class,
                CenterTypeEntity.class,
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
                CenterTypeDTO.class,
                CenterTypeEntity.class,
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
        TypeMap<CenterTypeEntity, CenterTypeDTO> dtoMap = mapper.createTypeMap(CenterTypeEntity.class, CenterTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCenterType(), CenterTypeDTO::setCodCenterType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CenterTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CenterTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CenterTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CenterTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CenterTypeDTO::setLastCheckerDateTime);

        TypeMap<CenterTypeDTO, CenterTypeEntity> entityMap = mapper.createTypeMap(CenterTypeDTO.class, CenterTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CenterTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CenterTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CenterTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CenterTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, CenterTypeEmbeddedKey> id = (code, status) -> CenterTypeEmbeddedKey.builder().codCenterType(code).codRecordStatus(status.name()).build();

    private Specification<CenterTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codCenterType"));
    }
}
