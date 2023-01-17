package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.PinCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.PinCodeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.PinCodeDTO;
import com.datawise.satisactual.repository.master.PinCodeRepository;
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
@RequestMapping("/mst/pin-code")
public class PinCodeController {

    @Autowired
    private CommonMasterService<PinCodeEntity, PinCodeEmbeddedKey, PinCodeDTO> service;
    @Autowired
    private PinCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<PinCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, PinCodeDTO.class));
    }

    @GetMapping("/{pin-code}/{country}/{district}")
    public ResponseEntity<PinCodeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 8) @PathVariable("pin-code") String pinCode,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(CodRecordStatus.A, pinCode, country, district), mapper, PinCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody PinCodeDTO dto) {
        service.save(
                repository,
                dto,
                PinCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPinCode(), dto.getCodCountry(), dto.getCodDistrict()),
                dto.getCodPinCode() + ";" + dto.getCodCountry() + ";" + dto.getCodDistrict(),
                mapper,
                this.id(CodRecordStatus.N, dto.getCodPinCode(), dto.getCodCountry(), dto.getCodDistrict()),
                this.id(CodRecordStatus.A, dto.getCodPinCode(), dto.getCodCountry(), dto.getCodDistrict())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody PinCodeDTO dto) {
        service.update(
                repository,
                dto,
                PinCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPinCode(), dto.getCodCountry(), dto.getCodDistrict()),
                dto.getCodPinCode() + ";" + dto.getCodCountry() + ";" + dto.getCodDistrict(),
                mapper,
                this.id(CodRecordStatus.M, dto.getCodPinCode(), dto.getCodCountry(), dto.getCodDistrict()),
                this.id(CodRecordStatus.A, dto.getCodPinCode(), dto.getCodCountry(), dto.getCodDistrict())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{pin-code}/{country}/{district}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 8) @PathVariable("pin-code") String pinCode,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district
    ) {
        service.delete(
                repository,
                PinCodeDTO.class,
                PinCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), pinCode, country, district),
                pinCode + ";" + country + ";" + district,
                mapper,
                this.id(CodRecordStatus.X, pinCode, country, district),
                this.id(CodRecordStatus.C, pinCode, country, district),
                this.id(CodRecordStatus.A, pinCode, country, district)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{pin-code}/{country}/{district}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 8) @PathVariable("pin-code") String pinCode,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district
    ) {
        service.reopen(
                repository,
                PinCodeDTO.class,
                PinCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), pinCode, country, district),
                pinCode + ";" + country + ";" + district,
                mapper,
                this.id(CodRecordStatus.R, pinCode, country, district),
                this.id(CodRecordStatus.A, pinCode, country, district),
                this.id(CodRecordStatus.C, pinCode, country, district)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{pin-code}/{country}/{district}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 8) @PathVariable("pin-code") String pinCode,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country,
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("district") String district
    ) {
        service.authorize(
                repository,
                PinCodeDTO.class,
                PinCodeEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), pinCode, country, district),
                pinCode + ";" + country + ";" + district,
                mapper,
                this.id(CodRecordStatus.A,pinCode, country, district),
                this.id(CodRecordStatus.C, pinCode, country, district)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<PinCodeEntity, PinCodeDTO> dtoMap = mapper.createTypeMap(PinCodeEntity.class, PinCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodPinCode(), PinCodeDTO::setCodPinCode);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), PinCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodDistrict(), PinCodeDTO::setCodDistrict);
        dtoMap.addMapping(c -> c.getId().getCodCountry(), PinCodeDTO::setCodCountry);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, PinCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, PinCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, PinCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, PinCodeDTO::setLastCheckerDateTime);

        TypeMap<PinCodeDTO, PinCodeEntity> entityMap = mapper.createTypeMap(PinCodeDTO.class, PinCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, PinCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, PinCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, PinCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, PinCodeEntity::setLastCheckerDateTime);
    }

    private PinCodeEmbeddedKey id(CodRecordStatus status, String pinCode, String country, String district) {
        return PinCodeEmbeddedKey.builder().codRecordStatus(status.name()).codPinCode(pinCode).codCountry(country).codDistrict(district).build();
    }

    private Specification<PinCodeEntity> getSpec(List<CodRecordStatus> statuses, String pinCode, String country, String district) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(pinCode, "codPinCode"))
                .and(repository.findRecordWithCode(country, "codCountry"))
                .and(repository.findRecordWithCode(district, "codDistrict"));
    }
}
