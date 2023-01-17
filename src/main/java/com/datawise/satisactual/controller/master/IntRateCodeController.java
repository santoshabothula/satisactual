package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.IntRateCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.IntRateCodeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.IntRateCodeDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.IntRateCodeRepository;
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
@RequestMapping("/mst/int-rate-code")
public class IntRateCodeController {

    @Autowired
    private CommonMasterService<IntRateCodeEntity, IntRateCodeEmbeddedKey, IntRateCodeDTO> service;
    @Autowired
    private IntRateCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<IntRateCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, IntRateCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntRateCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, IntRateCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody IntRateCodeDTO dto) {
        service.save(
                repository,
                dto,
                IntRateCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodInterestRate()),
                dto.getCodInterestRate(),
                mapper,
                this.id.apply(dto.getCodInterestRate(), CodRecordStatus.N),
                this.id.apply(dto.getCodInterestRate(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody IntRateCodeDTO dto) {
        service.update(
                repository,
                dto,
                IntRateCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodInterestRate()),
                dto.getCodInterestRate(),
                mapper,
                this.id.apply(dto.getCodInterestRate(), CodRecordStatus.M),
                this.id.apply(dto.getCodInterestRate(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                IntRateCodeDTO.class,
                IntRateCodeEntity.class,
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
                IntRateCodeDTO.class,
                IntRateCodeEntity.class,
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
                IntRateCodeDTO.class,
                IntRateCodeEntity.class,
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
        TypeMap<IntRateCodeEntity, IntRateCodeDTO> dtoMap = mapper.createTypeMap(IntRateCodeEntity.class, IntRateCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodInterestRate(), IntRateCodeDTO::setCodInterestRate);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), IntRateCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, IntRateCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, IntRateCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, IntRateCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, IntRateCodeDTO::setLastCheckerDateTime);

        TypeMap<IntRateCodeDTO, IntRateCodeEntity> entityMap = mapper.createTypeMap(IntRateCodeDTO.class, IntRateCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, IntRateCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, IntRateCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, IntRateCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, IntRateCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, IntRateCodeEmbeddedKey> id = (code, status) -> IntRateCodeEmbeddedKey.builder().codInterestRate(code).codRecordStatus(status.name()).build();

    private Specification<IntRateCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codInterestRate"));
    }
}
