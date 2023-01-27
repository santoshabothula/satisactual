package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.VehicleMakeEmbeddedKey;
import com.datawise.satisactual.entities.master.VehicleMakeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.VehicleMakeDTO;
import com.datawise.satisactual.repository.master.VehicleMakeRepository;
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
@RequestMapping("/mst/vehicle-make")
public class VehicleMakeController {

    @Autowired
    private CommonMasterService<VehicleMakeEntity, VehicleMakeEmbeddedKey, VehicleMakeDTO> service;
    @Autowired
    private VehicleMakeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<VehicleMakeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, VehicleMakeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleMakeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, VehicleMakeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody VehicleMakeDTO dto) {
        service.save(
                repository,
                dto,
                VehicleMakeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodMake()),
                dto.getCodMake(),
                mapper,
                this.id.apply(dto.getCodMake(), CodRecordStatus.N),
                this.id.apply(dto.getCodMake(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody VehicleMakeDTO dto) {
        service.update(
                repository,
                dto,
                VehicleMakeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodMake()),
                dto.getCodMake(),
                mapper,
                this.id.apply(dto.getCodMake(), CodRecordStatus.M),
                this.id.apply(dto.getCodMake(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                VehicleMakeDTO.class,
                VehicleMakeEntity.class,
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
                VehicleMakeDTO.class,
                VehicleMakeEntity.class,
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
                VehicleMakeDTO.class,
                VehicleMakeEntity.class,
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
        TypeMap<VehicleMakeEntity, VehicleMakeDTO> dtoMap = mapper.createTypeMap(VehicleMakeEntity.class, VehicleMakeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodMake(), VehicleMakeDTO::setCodMake);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), VehicleMakeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, VehicleMakeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, VehicleMakeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, VehicleMakeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, VehicleMakeDTO::setLastCheckerDateTime);

        TypeMap<VehicleMakeDTO, VehicleMakeEntity> entityMap = mapper.createTypeMap(VehicleMakeDTO.class, VehicleMakeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, VehicleMakeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, VehicleMakeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, VehicleMakeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, VehicleMakeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, VehicleMakeEmbeddedKey> id = (code, status) -> VehicleMakeEmbeddedKey.builder().codMake(code).codRecordStatus(status.name()).build();

    private Specification<VehicleMakeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codMake"));
    }
}
