package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.IntRateEmbeddedKey;
import com.datawise.satisactual.entities.master.IntRateEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.IntRateDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.IntRateRepository;
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
@RequestMapping("/mst/int-rate")
@CrossOrigin
public class IntRateController {

    @Autowired
    private CommonMasterService<IntRateEntity, IntRateEmbeddedKey, IntRateDTO> service;
    @Autowired
    private IntRateRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<IntRateDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, IntRateDTO.class));
    }

    @GetMapping("/{id}/{date}")
    public ResponseEntity<IntRateDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A, date), mapper, IntRateDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody IntRateDTO dto) {
        service.save(
                repository,
                dto,
                IntRateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodInterestRate(), dto.getDatEffective()),
                dto.getCodInterestRate() + ";" + dto.getDatEffective(),
                mapper,
                this.id(dto.getCodInterestRate(), CodRecordStatus.N, dto.getDatEffective()),
                this.id(dto.getCodInterestRate(), CodRecordStatus.A, dto.getDatEffective())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody IntRateDTO dto) {
        service.update(
                repository,
                dto,
                IntRateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodInterestRate(), dto.getDatEffective()),
                dto.getCodInterestRate() + ";" + dto.getDatEffective(),
                mapper,
                this.id(dto.getCodInterestRate(), CodRecordStatus.M, dto.getDatEffective()),
                this.id(dto.getCodInterestRate(), CodRecordStatus.A, dto.getDatEffective())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date
    ) {
        service.delete(
                repository,
                IntRateDTO.class,
                IntRateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id, date),
                id + ";" + date,
                mapper,
                this.id(id, CodRecordStatus.X, date),
                this.id(id, CodRecordStatus.C, date),
                this.id(id, CodRecordStatus.A, date)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}/{date}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date
    ) {
        service.reopen(
                repository,
                IntRateDTO.class,
                IntRateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id, date),
                id + ";" + date,
                mapper,
                this.id(id, CodRecordStatus.R, date),
                this.id(id, CodRecordStatus.A, date),
                this.id(id, CodRecordStatus.C, date)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}/{date}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date
    ) {
        service.authorize(
                repository,
                IntRateDTO.class,
                IntRateEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id, date),
                id + ";" + date,
                mapper,
                this.id(id, CodRecordStatus.A, date),
                this.id(id, CodRecordStatus.C, date)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<IntRateEntity, IntRateDTO> dtoMap = mapper.createTypeMap(IntRateEntity.class, IntRateDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodInterestRate(), IntRateDTO::setCodInterestRate);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), IntRateDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getDatEffective(), IntRateDTO::setDatEffective);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, IntRateDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, IntRateDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, IntRateDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, IntRateDTO::setLastCheckerDateTime);

        TypeMap<IntRateDTO, IntRateEntity> entityMap = mapper.createTypeMap(IntRateDTO.class, IntRateEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, IntRateEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, IntRateEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, IntRateEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, IntRateEntity::setLastCheckerDateTime);
    }

    private IntRateEmbeddedKey id(String code, CodRecordStatus status, LocalDate date) {
        return IntRateEmbeddedKey.builder().codInterestRate(code).codRecordStatus(status.name()).datEffective(date).build();
    }

    private Specification<IntRateEntity> getSpec(List<CodRecordStatus> statuses, String code, LocalDate date) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codInterestRate"))
                .and(repository.findRecordWithCode(date, "datEffective"));
    }
}
