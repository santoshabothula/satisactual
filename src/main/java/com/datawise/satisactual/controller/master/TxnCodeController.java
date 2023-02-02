package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.TxnCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.TxnCodeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.TxnCodeDTO;
import com.datawise.satisactual.repository.master.TxnCodeRepository;
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
@RequestMapping("/mst/txn-code")
@CrossOrigin
public class TxnCodeController {

    @Autowired
    private CommonMasterService<TxnCodeEntity, TxnCodeEmbeddedKey, TxnCodeDTO> service;
    @Autowired
    private TxnCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<TxnCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, TxnCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TxnCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, TxnCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody TxnCodeDTO dto) {
        service.save(
                repository,
                dto,
                TxnCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTxn()),
                dto.getCodTxn(),
                mapper,
                this.id.apply(dto.getCodTxn(), CodRecordStatus.N),
                this.id.apply(dto.getCodTxn(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody TxnCodeDTO dto) {
        service.update(
                repository,
                dto,
                TxnCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTxn()),
                dto.getCodTxn(),
                mapper,
                this.id.apply(dto.getCodTxn(), CodRecordStatus.M),
                this.id.apply(dto.getCodTxn(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                TxnCodeDTO.class,
                TxnCodeEntity.class,
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
                TxnCodeDTO.class,
                TxnCodeEntity.class,
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
                TxnCodeDTO.class,
                TxnCodeEntity.class,
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
        TypeMap<TxnCodeEntity, TxnCodeDTO> dtoMap = mapper.createTypeMap(TxnCodeEntity.class, TxnCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodTxn(), TxnCodeDTO::setCodTxn);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), TxnCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, TxnCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, TxnCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, TxnCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, TxnCodeDTO::setLastCheckerDateTime);

        TypeMap<TxnCodeDTO, TxnCodeEntity> entityMap = mapper.createTypeMap(TxnCodeDTO.class, TxnCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, TxnCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, TxnCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, TxnCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, TxnCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, TxnCodeEmbeddedKey> id = (code, status) -> TxnCodeEmbeddedKey.builder().codTxn(code).codRecordStatus(status.name()).build();

    private Specification<TxnCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codTxn"));
    }
}
