package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.TaxStatusEmbeddedKey;
import com.datawise.satisactual.entities.master.TaxStatusEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.TaxStatusDTO;
import com.datawise.satisactual.repository.master.TaxStatusRepository;
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
@RequestMapping("/mst/tax-status")
@CrossOrigin
public class TaxStatusController {

    @Autowired
    private CommonMasterService<TaxStatusEntity, TaxStatusEmbeddedKey, TaxStatusDTO> service;
    @Autowired
    private TaxStatusRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<TaxStatusDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, TaxStatusDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxStatusDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, TaxStatusDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody TaxStatusDTO dto) {
        service.save(
                repository,
                dto,
                TaxStatusEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTaxStatus()),
                dto.getCodTaxStatus(),
                mapper,
                this.id.apply(dto.getCodTaxStatus(), CodRecordStatus.N),
                this.id.apply(dto.getCodTaxStatus(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody TaxStatusDTO dto) {
        service.update(
                repository,
                dto,
                TaxStatusEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTaxStatus()),
                dto.getCodTaxStatus(),
                mapper,
                this.id.apply(dto.getCodTaxStatus(), CodRecordStatus.M),
                this.id.apply(dto.getCodTaxStatus(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                TaxStatusDTO.class,
                TaxStatusEntity.class,
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
                TaxStatusDTO.class,
                TaxStatusEntity.class,
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
                TaxStatusDTO.class,
                TaxStatusEntity.class,
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
        TypeMap<TaxStatusEntity, TaxStatusDTO> dtoMap = mapper.createTypeMap(TaxStatusEntity.class, TaxStatusDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodTaxStatus(), TaxStatusDTO::setCodTaxStatus);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), TaxStatusDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, TaxStatusDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, TaxStatusDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, TaxStatusDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, TaxStatusDTO::setLastCheckerDateTime);

        TypeMap<TaxStatusDTO, TaxStatusEntity> entityMap = mapper.createTypeMap(TaxStatusDTO.class, TaxStatusEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, TaxStatusEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, TaxStatusEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, TaxStatusEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, TaxStatusEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, TaxStatusEmbeddedKey> id = (code, status) -> TaxStatusEmbeddedKey.builder().codTaxStatus(code).codRecordStatus(status.name()).build();

    private Specification<TaxStatusEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codTaxStatus"));
    }
}
