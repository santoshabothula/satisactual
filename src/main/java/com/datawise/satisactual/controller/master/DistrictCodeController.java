package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.DistrictCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.DistrictCodeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.DistrictCodeDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.DistrictCodeRepository;
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

@Validated
@RestController
@RequestMapping("/mst/district-code")
public class DistrictCodeController {

    @Autowired
    private CommonMasterService<DistrictCodeEntity, DistrictCodeEmbeddedKey, DistrictCodeDTO> service;
    @Autowired
    private DistrictCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<DistrictCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, DistrictCodeDTO.class));
    }

    @GetMapping("/{id}/{date}")
    public ResponseEntity<DistrictCodeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(district, CodRecordStatus.A, state, country), mapper, DistrictCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody DistrictCodeDTO dto) {
        service.save(
                repository,
                dto,
                DistrictCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDistrict(), dto.getCodState(), dto.getCodCountry()),
                dto.getCodDistrict() + ";" + dto.getCodState(),
                mapper,
                this.id(dto.getCodDistrict(), CodRecordStatus.N, dto.getCodState(), dto.getCodCountry()),
                this.id(dto.getCodDistrict(), CodRecordStatus.A, dto.getCodState(), dto.getCodCountry())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody DistrictCodeDTO dto) {
        service.update(
                repository,
                dto,
                DistrictCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDistrict(), dto.getCodState(), dto.getCodCountry()),
                dto.getCodDistrict() + ";" + dto.getCodState(),
                mapper,
                this.id(dto.getCodDistrict(), CodRecordStatus.M, dto.getCodState(), dto.getCodCountry()),
                this.id(dto.getCodDistrict(), CodRecordStatus.A, dto.getCodState(), dto.getCodCountry())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.delete(
                repository,
                DistrictCodeDTO.class,
                DistrictCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), district, state, country),
                district + ";" + state + ";" + country,
                mapper,
                this.id(district, CodRecordStatus.X, state, country),
                this.id(district, CodRecordStatus.C, state, country),
                this.id(district, CodRecordStatus.A, state, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}/{date}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.reopen(
                repository,
                DistrictCodeDTO.class,
                DistrictCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), district, state, country),
                district + ";" + state + ";" + country,
                mapper,
                this.id(district, CodRecordStatus.R, state, country),
                this.id(district, CodRecordStatus.A, state, country),
                this.id(district, CodRecordStatus.C, state, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}/{date}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.authorize(
                repository,
                DistrictCodeDTO.class,
                DistrictCodeEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), district, state, country),
                district + ";" + state + ";" + country,
                mapper,
                this.id(district, CodRecordStatus.A, state, country),
                this.id(district, CodRecordStatus.C, state, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<DistrictCodeEntity, DistrictCodeDTO> dtoMap = mapper.createTypeMap(DistrictCodeEntity.class, DistrictCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDistrict(), DistrictCodeDTO::setCodDistrict);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), DistrictCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodCountry(), DistrictCodeDTO::setCodCountry);
        dtoMap.addMapping(c -> c.getId().getCodState(), DistrictCodeDTO::setCodState);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, DistrictCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, DistrictCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, DistrictCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, DistrictCodeDTO::setLastCheckerDateTime);

        TypeMap<DistrictCodeDTO, DistrictCodeEntity> entityMap = mapper.createTypeMap(DistrictCodeDTO.class, DistrictCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, DistrictCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, DistrictCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, DistrictCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, DistrictCodeEntity::setLastCheckerDateTime);
    }

    private DistrictCodeEmbeddedKey id(String code, CodRecordStatus status, String state, String country) {
        return DistrictCodeEmbeddedKey.builder().codDistrict(code).codRecordStatus(status.name()).codState(state).codCountry(country).build();
    }

    private Specification<DistrictCodeEntity> getSpec(List<CodRecordStatus> statuses, String code, String state, String country) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codDistrict"))
                .and(repository.findRecordWithCode(state, "codState"))
                .and(repository.findRecordWithCode(country, "codCountry"));
    }
}
