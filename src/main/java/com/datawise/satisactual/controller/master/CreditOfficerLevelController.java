package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CreditOfficerLevelEmbeddedKey;
import com.datawise.satisactual.entities.master.CreditOfficerLevelEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CreditOfficerLevelDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CreditOfficerLevelRepository;
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
@RequestMapping("/mst/credit-officer-level")
public class CreditOfficerLevelController {

    @Autowired
    private CommonMasterService<CreditOfficerLevelEntity, CreditOfficerLevelEmbeddedKey, CreditOfficerLevelDTO> service;
    @Autowired
    private CreditOfficerLevelRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CreditOfficerLevelDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CreditOfficerLevelDTO.class));
    }

    @GetMapping("/{id}/{date}")
    public ResponseEntity<CreditOfficerLevelDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A), mapper, CreditOfficerLevelDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CreditOfficerLevelDTO dto) {
        service.save(
                repository,
                dto,
                CreditOfficerLevelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCreditOfficerLevel()),
                dto.getCodCreditOfficerLevel(),
                mapper,
                this.id(dto.getCodCreditOfficerLevel(), CodRecordStatus.N),
                this.id(dto.getCodCreditOfficerLevel(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CreditOfficerLevelDTO dto) {
        service.update(
                repository,
                dto,
                CreditOfficerLevelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCreditOfficerLevel()),
                dto.getCodCreditOfficerLevel(),
                mapper,
                this.id(dto.getCodCreditOfficerLevel(), CodRecordStatus.M),
                this.id(dto.getCodCreditOfficerLevel(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.delete(
                repository,
                CreditOfficerLevelDTO.class,
                CreditOfficerLevelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.X),
                this.id(id, CodRecordStatus.C),
                this.id(id, CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}/{date}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.reopen(
                repository,
                CreditOfficerLevelDTO.class,
                CreditOfficerLevelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.R),
                this.id(id, CodRecordStatus.A),
                this.id(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.authorize(
                repository,
                CreditOfficerLevelDTO.class,
                CreditOfficerLevelEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.A),
                this.id(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<CreditOfficerLevelEntity, CreditOfficerLevelDTO> dtoMap = mapper.createTypeMap(CreditOfficerLevelEntity.class, CreditOfficerLevelDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCreditOfficerLevel(), CreditOfficerLevelDTO::setCodCreditOfficerLevel);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CreditOfficerLevelDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CreditOfficerLevelDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CreditOfficerLevelDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CreditOfficerLevelDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CreditOfficerLevelDTO::setLastCheckerDateTime);

        TypeMap<CreditOfficerLevelDTO, CreditOfficerLevelEntity> entityMap = mapper.createTypeMap(CreditOfficerLevelDTO.class, CreditOfficerLevelEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CreditOfficerLevelEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CreditOfficerLevelEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CreditOfficerLevelEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CreditOfficerLevelEntity::setLastCheckerDateTime);
    }

    private CreditOfficerLevelEmbeddedKey id(String code, CodRecordStatus status) {
        return CreditOfficerLevelEmbeddedKey.builder().codCreditOfficerLevel(code).codRecordStatus(status.name()).build();
    }

    private Specification<CreditOfficerLevelEntity> getSpec(List<CodRecordStatus> statuses, String code) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codCreditOfficerLevel"));
    }
}
