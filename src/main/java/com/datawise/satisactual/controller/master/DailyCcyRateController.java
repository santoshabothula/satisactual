package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.DailyCcyRateEmbeddedKey;
import com.datawise.satisactual.entities.master.DailyCcyRateEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.DailyCcyRateDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.DailyCcyRateRepository;
import com.datawise.satisactual.service.master.CommonMasterService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/mst/daily-ccy-rate")
public class DailyCcyRateController {

    @Autowired
    private CommonMasterService<DailyCcyRateEntity, DailyCcyRateEmbeddedKey, DailyCcyRateDTO> service;
    @Autowired
    private DailyCcyRateRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<DailyCcyRateDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, DailyCcyRateDTO.class));
    }

    @GetMapping("/{id}/{date}")
    public ResponseEntity<DailyCcyRateDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("base-currency") String baseCurrency
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A, date, baseCurrency), mapper, DailyCcyRateDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody DailyCcyRateDTO dto) {
        service.save(
                repository,
                dto,
                DailyCcyRateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCurrency(), dto.getRateSet(), dto.getCodBaseCurrency()),
                dto.getCodCurrency() + ";" + dto.getRateSet() + ";" + dto.getCodBaseCurrency(),
                mapper,
                this.id(dto.getCodCurrency(), CodRecordStatus.N, dto.getRateSet(), dto.getCodBaseCurrency()),
                this.id(dto.getCodCurrency(), CodRecordStatus.A, dto.getRateSet(), dto.getCodBaseCurrency())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody DailyCcyRateDTO dto) {
        service.update(
                repository,
                dto,
                DailyCcyRateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCurrency(), dto.getRateSet(), dto.getCodBaseCurrency()),
                dto.getCodCurrency() + ";" + dto.getRateSet() + ";" + dto.getCodBaseCurrency(),
                mapper,
                this.id(dto.getCodCurrency(), CodRecordStatus.M, dto.getRateSet(), dto.getCodBaseCurrency()),
                this.id(dto.getCodCurrency(), CodRecordStatus.A, dto.getRateSet(), dto.getCodBaseCurrency())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("base-currency") String baseCurrency
    ) {
        service.delete(
                repository,
                DailyCcyRateDTO.class,
                DailyCcyRateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id, date, baseCurrency),
                id + ";" + date + ";" + baseCurrency,
                mapper,
                this.id(id, CodRecordStatus.X, date, baseCurrency),
                this.id(id, CodRecordStatus.C, date, baseCurrency),
                this.id(id, CodRecordStatus.A, date, baseCurrency)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}/{date}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("base-currency") String baseCurrency
    ) {
        service.reopen(
                repository,
                DailyCcyRateDTO.class,
                DailyCcyRateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id, date, baseCurrency),
                id + ";" + date + ";" + baseCurrency,
                mapper,
                this.id(id, CodRecordStatus.R, date, baseCurrency),
                this.id(id, CodRecordStatus.A, date, baseCurrency),
                this.id(id, CodRecordStatus.C, date, baseCurrency)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}/{date}/{base-currency}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("base-currency") String baseCurrency
    ) {
        service.authorize(
                repository,
                DailyCcyRateDTO.class,
                DailyCcyRateEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id, date, baseCurrency),
                id,
                mapper,
                this.id(id, CodRecordStatus.A, date, baseCurrency),
                this.id(id, CodRecordStatus.C, date, baseCurrency)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<DailyCcyRateEntity, DailyCcyRateDTO> dtoMap = mapper.createTypeMap(DailyCcyRateEntity.class, DailyCcyRateDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCurrency(), DailyCcyRateDTO::setCodCurrency);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), DailyCcyRateDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getRateSet(), DailyCcyRateDTO::setRateSet);
        dtoMap.addMapping(c -> c.getId().getCodBaseCurrency(), DailyCcyRateDTO::setCodBaseCurrency);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, DailyCcyRateDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, DailyCcyRateDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, DailyCcyRateDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, DailyCcyRateDTO::setLastCheckerDateTime);

        TypeMap<DailyCcyRateDTO, DailyCcyRateEntity> entityMap = mapper.createTypeMap(DailyCcyRateDTO.class, DailyCcyRateEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, DailyCcyRateEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, DailyCcyRateEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, DailyCcyRateEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, DailyCcyRateEntity::setLastCheckerDateTime);
    }

    private DailyCcyRateEmbeddedKey id(String code, CodRecordStatus status, LocalDate date, String codBaseCurrency) {
        return DailyCcyRateEmbeddedKey.builder().codCurrency(code).codRecordStatus(status.name()).rateSet(date).codBaseCurrency(codBaseCurrency).build();
    }

    private Specification<DailyCcyRateEntity> getSpec(List<CodRecordStatus> statuses, String code, LocalDate date, String codBaseCurrency) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codCurrency"))
                .and(repository.findRecordWithCode(date, "rateSet"))
                .and(repository.findRecordWithCode(codBaseCurrency, "codBaseCurrency"));
    }
}
