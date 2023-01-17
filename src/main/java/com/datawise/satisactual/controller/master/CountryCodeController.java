package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CountryCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.CountryCodeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CountryCodeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CountryCodeRepository;
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
@RequestMapping("/mst/country-code")
public class CountryCodeController {

    @Autowired
    private CommonMasterService<CountryCodeEntity, CountryCodeEmbeddedKey, CountryCodeDTO> service;
    @Autowired
    private CountryCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CountryCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CountryCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, CountryCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CountryCodeDTO dto) {
        service.save(
                repository,
                dto,
                CountryCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCountry()),
                dto.getCodCountry(),
                mapper,
                this.id.apply(dto.getCodCountry(), CodRecordStatus.N),
                this.id.apply(dto.getCodCountry(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CountryCodeDTO dto) {
        service.update(
                repository,
                dto,
                CountryCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCountry()),
                dto.getCodCountry(),
                mapper,
                this.id.apply(dto.getCodCountry(), CodRecordStatus.M),
                this.id.apply(dto.getCodCountry(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                CountryCodeDTO.class,
                CountryCodeEntity.class,
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
                CountryCodeDTO.class,
                CountryCodeEntity.class,
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
                CountryCodeDTO.class,
                CountryCodeEntity.class,
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
        TypeMap<CountryCodeEntity, CountryCodeDTO> dtoMap = mapper.createTypeMap(CountryCodeEntity.class, CountryCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCountry(), CountryCodeDTO::setCodCountry);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CountryCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CountryCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CountryCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CountryCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CountryCodeDTO::setLastCheckerDateTime);

        TypeMap<CountryCodeDTO, CountryCodeEntity> entityMap = mapper.createTypeMap(CountryCodeDTO.class, CountryCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CountryCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CountryCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CountryCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CountryCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, CountryCodeEmbeddedKey> id = (code, status) -> CountryCodeEmbeddedKey.builder().codCountry(code).codRecordStatus(status.name()).build();

    private Specification<CountryCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codCountry"));
    }
}
