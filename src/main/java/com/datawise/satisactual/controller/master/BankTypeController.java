package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.BankTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.BankTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.BankTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.BankTypeRepository;
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
@RequestMapping("/mst/bank-type")
public class BankTypeController {

    @Autowired
    private CommonMasterService<BankTypeEntity, BankTypeEmbeddedKey, BankTypeDTO> service;
    @Autowired
    private BankTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<BankTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, BankTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, BankTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody BankTypeDTO dto) {
        service.save(
                repository,
                dto,
                BankTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodBankType()),
                dto.getCodBankType(),
                mapper,
                this.id.apply(dto.getCodBankType(), CodRecordStatus.N),
                this.id.apply(dto.getCodBankType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody BankTypeDTO dto) {
        service.update(
                repository,
                dto,
                BankTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodBankType()),
                dto.getCodBankType(),
                mapper,
                this.id.apply(dto.getCodBankType(), CodRecordStatus.M),
                this.id.apply(dto.getCodBankType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                BankTypeDTO.class,
                BankTypeEntity.class,
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
                BankTypeDTO.class,
                BankTypeEntity.class,
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
                BankTypeDTO.class,
                BankTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.R, CodRecordStatus.X, CodRecordStatus.M, CodRecordStatus.N), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<BankTypeEntity, BankTypeDTO> dtoMap = mapper.createTypeMap(BankTypeEntity.class, BankTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodBankType(), BankTypeDTO::setCodBankType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), BankTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, BankTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, BankTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, BankTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, BankTypeDTO::setLastCheckerDateTime);

        TypeMap<BankTypeDTO, BankTypeEntity> entityMap = mapper.createTypeMap(BankTypeDTO.class, BankTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, BankTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, BankTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, BankTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, BankTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, BankTypeEmbeddedKey> id = (code, status) -> BankTypeEmbeddedKey.builder().codBankType(code).codRecordStatus(status.name()).build();

    private Specification<BankTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codBankType"));
    }
}
