package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.VehicleTypEmbeddedKey;
import com.datawise.satisactual.entities.master.VehicleTypEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.VehicleTypDTO;
import com.datawise.satisactual.repository.master.VehicleTypRepository;
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
@RequestMapping("/mst/vehicle-typ")
public class VehicleTypController {

    @Autowired
    private CommonMasterService<VehicleTypEntity, VehicleTypEmbeddedKey, VehicleTypDTO> service;
    @Autowired
    private VehicleTypRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<VehicleTypDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, VehicleTypDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleTypDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, VehicleTypDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody VehicleTypDTO dto) {
        service.save(
                repository,
                dto,
                VehicleTypEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodVehicleTyp()),
                dto.getCodVehicleTyp(),
                mapper,
                this.id.apply(dto.getCodVehicleTyp(), CodRecordStatus.N),
                this.id.apply(dto.getCodVehicleTyp(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody VehicleTypDTO dto) {
        service.update(
                repository,
                dto,
                VehicleTypEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodVehicleTyp()),
                dto.getCodVehicleTyp(),
                mapper,
                this.id.apply(dto.getCodVehicleTyp(), CodRecordStatus.M),
                this.id.apply(dto.getCodVehicleTyp(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                VehicleTypDTO.class,
                VehicleTypEntity.class,
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
                VehicleTypDTO.class,
                VehicleTypEntity.class,
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
                VehicleTypDTO.class,
                VehicleTypEntity.class,
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
        TypeMap<VehicleTypEntity, VehicleTypDTO> dtoMap = mapper.createTypeMap(VehicleTypEntity.class, VehicleTypDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodVehicleTyp(), VehicleTypDTO::setCodVehicleTyp);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), VehicleTypDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, VehicleTypDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, VehicleTypDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, VehicleTypDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, VehicleTypDTO::setLastCheckerDateTime);

        TypeMap<VehicleTypDTO, VehicleTypEntity> entityMap = mapper.createTypeMap(VehicleTypDTO.class, VehicleTypEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, VehicleTypEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, VehicleTypEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, VehicleTypEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, VehicleTypEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, VehicleTypEmbeddedKey> id = (code, status) -> VehicleTypEmbeddedKey.builder().codVehicleTyp(code).codRecordStatus(status.name()).build();

    private Specification<VehicleTypEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codVehicleTyp"));
    }
}
