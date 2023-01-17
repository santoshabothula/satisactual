package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CarDealershipEmbeddedKey;
import com.datawise.satisactual.entities.master.CarDealershipEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CarDealershipDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CarDealershipRepository;
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
@RequestMapping("/mst/car-dealership")
public class CarDealershipController {

    @Autowired
    private CommonMasterService<CarDealershipEntity, CarDealershipEmbeddedKey, CarDealershipDTO> service;
    @Autowired
    private CarDealershipRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CarDealershipDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CarDealershipDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDealershipDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, CarDealershipDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CarDealershipDTO dto) {
        service.save(
                repository,
                dto,
                CarDealershipEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getIdThirdPartyDealer()),
                String.valueOf(dto.getIdThirdPartyDealer()),
                mapper,
                this.id.apply(dto.getIdThirdPartyDealer(), CodRecordStatus.N),
                this.id.apply(dto.getIdThirdPartyDealer(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CarDealershipDTO dto) {
        service.update(
                repository,
                dto,
                CarDealershipEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getIdThirdPartyDealer()),
                String.valueOf(dto.getIdThirdPartyDealer()),
                mapper,
                this.id.apply(dto.getIdThirdPartyDealer(), CodRecordStatus.M),
                this.id.apply(dto.getIdThirdPartyDealer(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") Long id) {
        service.delete(
                repository,
                CarDealershipDTO.class,
                CarDealershipEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                String.valueOf(id),
                mapper,
                this.id.apply(id, CodRecordStatus.X),
                this.id.apply(id, CodRecordStatus.C),
                this.id.apply(id, CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}")
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") Long id) {
        service.reopen(
                repository,
                CarDealershipDTO.class,
                CarDealershipEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                String.valueOf(id),
                mapper,
                this.id.apply(id, CodRecordStatus.R),
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") Long id) {
        service.authorize(
                repository,
                CarDealershipDTO.class,
                CarDealershipEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                String.valueOf(id),
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<CarDealershipEntity, CarDealershipDTO> dtoMap = mapper.createTypeMap(CarDealershipEntity.class, CarDealershipDTO.class);
        dtoMap.addMapping(c -> c.getId().getIdThirdPartyDealer(), CarDealershipDTO::setIdThirdPartyDealer);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CarDealershipDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CarDealershipDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CarDealershipDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CarDealershipDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CarDealershipDTO::setLastCheckerDateTime);

        TypeMap<CarDealershipDTO, CarDealershipEntity> entityMap = mapper.createTypeMap(CarDealershipDTO.class, CarDealershipEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CarDealershipEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CarDealershipEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CarDealershipEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CarDealershipEntity::setLastCheckerDateTime);
    }

    private final BiFunction<Long, CodRecordStatus, CarDealershipEmbeddedKey> id = (code, status) -> CarDealershipEmbeddedKey.builder().idThirdPartyDealer(code).codRecordStatus(status.name()).build();

    private Specification<CarDealershipEntity> getSpec(List<CodRecordStatus> statuses, Long id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "idThirdPartyDealer"));
    }
}
