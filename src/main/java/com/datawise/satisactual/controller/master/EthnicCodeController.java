package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.EthnicCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.EthnicCodeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.EthnicCodeDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.EthnicCodeRepository;
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
@RequestMapping("/mst/ethnic-code")
public class EthnicCodeController {

    @Autowired
    private CommonMasterService<EthnicCodeEntity, EthnicCodeEmbeddedKey, EthnicCodeDTO> service;
    @Autowired
    private EthnicCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<EthnicCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, EthnicCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EthnicCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, EthnicCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody EthnicCodeDTO dto) {
        service.save(
                repository,
                dto,
                EthnicCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodEthnicity()),
                dto.getCodEthnicity(),
                mapper,
                this.id.apply(dto.getCodEthnicity(), CodRecordStatus.N),
                this.id.apply(dto.getCodEthnicity(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody EthnicCodeDTO dto) {
        service.update(
                repository,
                dto,
                EthnicCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodEthnicity()),
                dto.getCodEthnicity(),
                mapper,
                this.id.apply(dto.getCodEthnicity(), CodRecordStatus.M),
                this.id.apply(dto.getCodEthnicity(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                EthnicCodeDTO.class,
                EthnicCodeEntity.class,
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
                EthnicCodeDTO.class,
                EthnicCodeEntity.class,
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
                EthnicCodeDTO.class,
                EthnicCodeEntity.class,
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
        TypeMap<EthnicCodeEntity, EthnicCodeDTO> dtoMap = mapper.createTypeMap(EthnicCodeEntity.class, EthnicCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodEthnicity(), EthnicCodeDTO::setCodEthnicity);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), EthnicCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, EthnicCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, EthnicCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, EthnicCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, EthnicCodeDTO::setLastCheckerDateTime);

        TypeMap<EthnicCodeDTO, EthnicCodeEntity> entityMap = mapper.createTypeMap(EthnicCodeDTO.class, EthnicCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, EthnicCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, EthnicCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, EthnicCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, EthnicCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, EthnicCodeEmbeddedKey> id = (code, status) -> EthnicCodeEmbeddedKey.builder().codEthnicity(code).codRecordStatus(status.name()).build();

    private Specification<EthnicCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codEthnicity"));
    }
}
