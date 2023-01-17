package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.PanchayatCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.PanchayatCodeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.PanchayatCodeDTO;
import com.datawise.satisactual.repository.master.PanchayatCodeRepository;
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
@RequestMapping("/mst/panchayat-code")
public class PanchayatCodeController {

    @Autowired
    private CommonMasterService<PanchayatCodeEntity, PanchayatCodeEmbeddedKey, PanchayatCodeDTO> service;
    @Autowired
    private PanchayatCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<PanchayatCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, PanchayatCodeDTO.class));
    }

    @GetMapping("/{panchayat}/{block}/{district}/{state}/{country}/{country}")
    public ResponseEntity<PanchayatCodeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("panchayat") String panchayat,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("block") String block,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("country") String country
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(CodRecordStatus.A, panchayat, block, district, state, country), mapper, PanchayatCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody PanchayatCodeDTO dto) {
        service.save(
                repository,
                dto,
                PanchayatCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPanchayat(), dto.getCodBlock(), dto.getCodDistrict(), dto.getCodState(), dto.getCodCountry()),
                dto.getCodPanchayat() + ";" + dto.getCodBlock() + ";" + dto.getCodDistrict() + ";" + dto.getCodState() + ";" + dto.getCodCountry(),
                mapper,
                this.id(CodRecordStatus.N, dto.getCodPanchayat(), dto.getCodBlock(), dto.getCodDistrict(), dto.getCodState(), dto.getCodCountry()),
                this.id(CodRecordStatus.A, dto.getCodPanchayat(), dto.getCodBlock(), dto.getCodDistrict(), dto.getCodState(), dto.getCodCountry())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody PanchayatCodeDTO dto) {
        service.update(
                repository,
                dto,
                PanchayatCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPanchayat(), dto.getCodBlock(), dto.getCodDistrict(), dto.getCodState(), dto.getCodCountry()),
                dto.getCodPanchayat() + ";" + dto.getCodBlock() + ";" + dto.getCodDistrict() + ";" + dto.getCodState() + ";" + dto.getCodCountry(),
                mapper,
                this.id(CodRecordStatus.M, dto.getCodPanchayat(), dto.getCodBlock(), dto.getCodDistrict(), dto.getCodState(), dto.getCodCountry()),
                this.id(CodRecordStatus.A, dto.getCodPanchayat(), dto.getCodBlock(), dto.getCodDistrict(), dto.getCodState(), dto.getCodCountry())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{panchayat}/{block}/{district}/{state}/{country}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("panchayat") String panchayat,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("block") String block,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("country") String country
    ) {
        service.delete(
                repository,
                PanchayatCodeDTO.class,
                PanchayatCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), panchayat, block, district, state, country),
                panchayat + ";" + block + ";" + district + ";" + state + ";" + country,
                mapper,
                this.id(CodRecordStatus.X, panchayat, block, district, state, country),
                this.id(CodRecordStatus.C, panchayat, block, district, state, country),
                this.id(CodRecordStatus.A, panchayat, block, district, state, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{panchayat}/{block}/{district}/{state}/{country}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("panchayat") String panchayat,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("block") String block,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("country") String country
    ) {
        service.reopen(
                repository,
                PanchayatCodeDTO.class,
                PanchayatCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), panchayat, block, district, state, country),
                panchayat + ";" + block + ";" + district + ";" + state + ";" + country,
                mapper,
                this.id(CodRecordStatus.R, panchayat, block, district, state, country),
                this.id(CodRecordStatus.A, panchayat, block, district, state, country),
                this.id(CodRecordStatus.C, panchayat, block, district, state, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{panchayat}/{block}/{district}/{state}/{country}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("panchayat") String panchayat,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("block") String block,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("country") String country
    ) {
        service.authorize(
                repository,
                PanchayatCodeDTO.class,
                PanchayatCodeEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), panchayat, block, district, state, country),
                panchayat + ";" + block + ";" + district + ";" + state + ";" + country,
                mapper,
                this.id(CodRecordStatus.A, panchayat, block, district, state, country),
                this.id(CodRecordStatus.C, panchayat, block, district, state, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<PanchayatCodeEntity, PanchayatCodeDTO> dtoMap = mapper.createTypeMap(PanchayatCodeEntity.class, PanchayatCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodPanchayat(), PanchayatCodeDTO::setCodPanchayat);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), PanchayatCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodBlock(), PanchayatCodeDTO::setCodBlock);
        dtoMap.addMapping(c -> c.getId().getCodCountry(), PanchayatCodeDTO::setCodCountry);
        dtoMap.addMapping(c -> c.getId().getCodDistrict(), PanchayatCodeDTO::setCodDistrict);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, PanchayatCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, PanchayatCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, PanchayatCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, PanchayatCodeDTO::setLastCheckerDateTime);

        TypeMap<PanchayatCodeDTO, PanchayatCodeEntity> entityMap = mapper.createTypeMap(PanchayatCodeDTO.class, PanchayatCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, PanchayatCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, PanchayatCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, PanchayatCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, PanchayatCodeEntity::setLastCheckerDateTime);
    }

    private PanchayatCodeEmbeddedKey id(CodRecordStatus status, String panchayat, String block, String district, String state, String country) {
        return PanchayatCodeEmbeddedKey.builder().codPanchayat(panchayat).codRecordStatus(status.name()).codBlock(block).codCountry(country).codDistrict(district).codState(state).build();
    }

    private Specification<PanchayatCodeEntity> getSpec(List<CodRecordStatus> statuses, String panchayat, String block, String district, String state, String country) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(panchayat, "codPanchayat"))
                .and(repository.findRecordWithCode(block, "codBlock"))
                .and(repository.findRecordWithCode(country, "codCountry"))
                .and(repository.findRecordWithCode(district, "codDistrict"))
                .and(repository.findRecordWithCode(state, "state"));
    }
}
