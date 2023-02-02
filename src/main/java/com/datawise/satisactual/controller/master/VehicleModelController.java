package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.VehicleModelEmbeddedKey;
import com.datawise.satisactual.entities.master.VehicleModelEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.VehicleModelDTO;
import com.datawise.satisactual.repository.master.VehicleModelRepository;
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

@Validated
@RestController
@RequestMapping("/mst/vehicle-model")
@CrossOrigin
public class VehicleModelController {

    @Autowired
    private CommonMasterService<VehicleModelEntity, VehicleModelEmbeddedKey, VehicleModelDTO> service;
    @Autowired
    private VehicleModelRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<VehicleModelDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, VehicleModelDTO.class));
    }

    @GetMapping("/{model}/{make}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<VehicleModelDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("model") String model,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("make") String make
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(model, CodRecordStatus.A, make), mapper, VehicleModelDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody VehicleModelDTO dto) {
        service.save(
                repository,
                dto,
                VehicleModelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodModel(), dto.getCodMake()),
                dto.getCodModel() + ";" + dto.getCodMake(),
                mapper,
                this.id(dto.getCodModel(), CodRecordStatus.N, dto.getCodMake()),
                this.id(dto.getCodModel(), CodRecordStatus.A, dto.getCodMake())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody VehicleModelDTO dto) {
        service.update(
                repository,
                dto,
                VehicleModelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodModel(), dto.getCodMake()),
                dto.getCodModel() + ";" + dto.getCodMake(),
                mapper,
                this.id(dto.getCodModel(), CodRecordStatus.M, dto.getCodMake()),
                this.id(dto.getCodModel(), CodRecordStatus.A, dto.getCodMake())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{model}/{make}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("model") String model,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("make") String make
    ) {
        service.delete(
                repository,
                VehicleModelDTO.class,
                VehicleModelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), model, make),
                model + ";" + make,
                mapper,
                this.id(model, CodRecordStatus.X, make),
                this.id(model, CodRecordStatus.C, make),
                this.id(model, CodRecordStatus.A, make)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{model}/{make}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("model") String model,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("make") String make
    ) {
        service.reopen(
                repository,
                VehicleModelDTO.class,
                VehicleModelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), model, make),
                model + ";" + make,
                mapper,
                this.id(model, CodRecordStatus.R, make),
                this.id(model, CodRecordStatus.A, make),
                this.id(model, CodRecordStatus.C, make)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{model}/{make}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("model") String model,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("make") String make
    ) {
        service.authorize(
                repository,
                VehicleModelDTO.class,
                VehicleModelEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), model, make),
                model + ";" + make,
                mapper,
                this.id(model, CodRecordStatus.A, make),
                this.id(model, CodRecordStatus.C, make)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<VehicleModelEntity, VehicleModelDTO> dtoMap = mapper.createTypeMap(VehicleModelEntity.class, VehicleModelDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodModel(), VehicleModelDTO::setCodModel);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), VehicleModelDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodMake(), VehicleModelDTO::setCodMake);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, VehicleModelDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, VehicleModelDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, VehicleModelDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, VehicleModelDTO::setLastCheckerDateTime);

        TypeMap<VehicleModelDTO, VehicleModelEntity> entityMap = mapper.createTypeMap(VehicleModelDTO.class, VehicleModelEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, VehicleModelEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, VehicleModelEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, VehicleModelEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, VehicleModelEntity::setLastCheckerDateTime);
    }

    private VehicleModelEmbeddedKey id(String model, CodRecordStatus status, String make) {
        return VehicleModelEmbeddedKey.builder().codModel(model).codRecordStatus(status.name()).codMake(make).build();
    }

    private Specification<VehicleModelEntity> getSpec(List<CodRecordStatus> statuses, String model, String make) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(model, "codModel"))
                .and(repository.findRecordWithCode(make, "codMake"));
    }
}
