package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CollateralTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CollateralTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CollateralTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CollateralTypeRepository;
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
@RequestMapping("/mst/collateral-type")
public class CollateralTypeController {

    @Autowired
    private CommonMasterService<CollateralTypeEntity, CollateralTypeEmbeddedKey, CollateralTypeDTO> service;
    @Autowired
    private CollateralTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CollateralTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CollateralTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollateralTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, CollateralTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CollateralTypeDTO dto) {
        service.save(
                repository,
                dto,
                CollateralTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCollType()),
                dto.getCodCollType(),
                mapper,
                this.id.apply(dto.getCodCollType(), CodRecordStatus.N),
                this.id.apply(dto.getCodCollType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CollateralTypeDTO dto) {
        service.update(
                repository,
                dto,
                CollateralTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCollType()),
                dto.getCodCollType(),
                mapper,
                this.id.apply(dto.getCodCollType(), CodRecordStatus.M),
                this.id.apply(dto.getCodCollType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                CollateralTypeDTO.class,
                CollateralTypeEntity.class,
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
                CollateralTypeDTO.class,
                CollateralTypeEntity.class,
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
                CollateralTypeDTO.class,
                CollateralTypeEntity.class,
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
        TypeMap<CollateralTypeEntity, CollateralTypeDTO> dtoMap = mapper.createTypeMap(CollateralTypeEntity.class, CollateralTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCollType(), CollateralTypeDTO::setCodCollType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CollateralTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CollateralTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CollateralTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CollateralTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CollateralTypeDTO::setLastCheckerDateTime);

        TypeMap<CollateralTypeDTO, CollateralTypeEntity> entityMap = mapper.createTypeMap(CollateralTypeDTO.class, CollateralTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CollateralTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CollateralTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CollateralTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CollateralTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, CollateralTypeEmbeddedKey> id = (code, status) -> CollateralTypeEmbeddedKey.builder().codCollType(code).codRecordStatus(status.name()).build();

    private Specification<CollateralTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codCollType"));
    }
}
