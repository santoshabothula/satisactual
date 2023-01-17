package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.AddressTypeEntity;
import com.datawise.satisactual.entities.master.AddressTypesEmbeddedKey;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.AddressTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.AddressTypesRepository;
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
@RequestMapping("/mst/address-type")
public class AddressTypeController {

    @Autowired
    private CommonMasterService<AddressTypeEntity, AddressTypesEmbeddedKey, AddressTypeDTO> service;
    @Autowired
    private AddressTypesRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<AddressTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, AddressTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, AddressTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody AddressTypeDTO dto) {
        service.save(
                repository,
                dto,
                AddressTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAddressType()),
                dto.getCodAddressType(),
                mapper,
                this.id.apply(dto.getCodAddressType(), CodRecordStatus.N),
                this.id.apply(dto.getCodAddressType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody AddressTypeDTO dto) {
        service.update(
                repository,
                dto,
                AddressTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAddressType()),
                dto.getCodAddressType(),
                mapper,
                this.id.apply(dto.getCodAddressType(), CodRecordStatus.M),
                this.id.apply(dto.getCodAddressType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                AddressTypeDTO.class,
                AddressTypeEntity.class,
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
                AddressTypeDTO.class,
                AddressTypeEntity.class,
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
                AddressTypeDTO.class,
                AddressTypeEntity.class,
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
        TypeMap<AddressTypeEntity, AddressTypeDTO> dtoMap = mapper.createTypeMap(AddressTypeEntity.class, AddressTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodAddressType(), AddressTypeDTO::setCodAddressType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), AddressTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, AddressTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, AddressTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, AddressTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, AddressTypeDTO::setLastCheckerDateTime);

        TypeMap<AddressTypeDTO, AddressTypeEntity> entityMap = mapper.createTypeMap(AddressTypeDTO.class, AddressTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, AddressTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, AddressTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, AddressTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, AddressTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, AddressTypesEmbeddedKey> id = (code, status) -> AddressTypesEmbeddedKey.builder().codAddressType(code).codRecordStatus(status.name()).build();

    private Specification<AddressTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codAddressType"));
    }
}
