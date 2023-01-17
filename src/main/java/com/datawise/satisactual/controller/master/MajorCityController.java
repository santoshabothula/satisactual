package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MajorCityEmbeddedKey;
import com.datawise.satisactual.entities.master.MajorCityEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MajorCityDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.MajorCityRepository;
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
@RequestMapping("/mst/major-city")
public class MajorCityController {

    @Autowired
    private CommonMasterService<MajorCityEntity, MajorCityEmbeddedKey, MajorCityDTO> service;
    @Autowired
    private MajorCityRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<MajorCityDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, MajorCityDTO.class));
    }

    @GetMapping("/{city}/{country}")
    public ResponseEntity<MajorCityDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 8) @PathVariable("city") String city,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(city, CodRecordStatus.A, country), mapper, MajorCityDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody MajorCityDTO dto) {
        service.save(
                repository,
                dto,
                MajorCityEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCity(), dto.getCodCountry()),
                dto.getCodCity() + ";" + dto.getCodCountry(),
                mapper,
                this.id(dto.getCodCity(), CodRecordStatus.N, dto.getCodCountry()),
                this.id(dto.getCodCity(), CodRecordStatus.A, dto.getCodCountry())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody MajorCityDTO dto) {
        service.update(
                repository,
                dto,
                MajorCityEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCity(), dto.getCodCountry()),
                dto.getCodCity() + ";" + dto.getCodCountry(),
                mapper,
                this.id(dto.getCodCity(), CodRecordStatus.M, dto.getCodCountry()),
                this.id(dto.getCodCity(), CodRecordStatus.A, dto.getCodCountry())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{city}/{country}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 8) @PathVariable("city") String city,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.delete(
                repository,
                MajorCityDTO.class,
                MajorCityEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), city, country),
                city + ";" + country,
                mapper,
                this.id(city, CodRecordStatus.X, country),
                this.id(city, CodRecordStatus.C, country),
                this.id(city, CodRecordStatus.A, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{city}/{country}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 8) @PathVariable("city") String city,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.reopen(
                repository,
                MajorCityDTO.class,
                MajorCityEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), city, country),
                city + ";" + country,
                mapper,
                this.id(city, CodRecordStatus.R, country),
                this.id(city, CodRecordStatus.A, country),
                this.id(city, CodRecordStatus.C, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{city}/{country}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 8) @PathVariable("city") String city,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.authorize(
                repository,
                MajorCityDTO.class,
                MajorCityEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), city, country),
                city + ";" + country,
                mapper,
                this.id(city, CodRecordStatus.A, country),
                this.id(city, CodRecordStatus.C, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<MajorCityEntity, MajorCityDTO> dtoMap = mapper.createTypeMap(MajorCityEntity.class, MajorCityDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCity(), MajorCityDTO::setCodCity);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), MajorCityDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodCountry(), MajorCityDTO::setCodCountry);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, MajorCityDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, MajorCityDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, MajorCityDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, MajorCityDTO::setLastCheckerDateTime);

        TypeMap<MajorCityDTO, MajorCityEntity> entityMap = mapper.createTypeMap(MajorCityDTO.class, MajorCityEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, MajorCityEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, MajorCityEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, MajorCityEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, MajorCityEntity::setLastCheckerDateTime);
    }

    private MajorCityEmbeddedKey id(String code, CodRecordStatus status, String country) {
        return MajorCityEmbeddedKey.builder().codCity(code).codRecordStatus(status.name()).codCountry(country).build();
    }

    private Specification<MajorCityEntity> getSpec(List<CodRecordStatus> statuses, String code, String country) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codCity"))
                .and(repository.findRecordWithCode(country, "codCountry"));
    }
}
