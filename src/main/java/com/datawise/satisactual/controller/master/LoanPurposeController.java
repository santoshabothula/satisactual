package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.LoanPurposeEmbeddedKey;
import com.datawise.satisactual.entities.master.LoanPurposeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.LoanPurposeDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.LoanPurposeRepository;
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
@RequestMapping("/mst/loan-purpose")
public class LoanPurposeController {

    @Autowired
    private CommonMasterService<LoanPurposeEntity, LoanPurposeEmbeddedKey, LoanPurposeDTO> service;
    @Autowired
    private LoanPurposeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<LoanPurposeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, LoanPurposeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanPurposeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, LoanPurposeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody LoanPurposeDTO dto) {
        service.save(
                repository,
                dto,
                LoanPurposeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodLoanPurpose()),
                dto.getCodLoanPurpose(),
                mapper,
                this.id.apply(dto.getCodLoanPurpose(), CodRecordStatus.N),
                this.id.apply(dto.getCodLoanPurpose(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody LoanPurposeDTO dto) {
        service.update(
                repository,
                dto,
                LoanPurposeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodLoanPurpose()),
                dto.getCodLoanPurpose(),
                mapper,
                this.id.apply(dto.getCodLoanPurpose(), CodRecordStatus.M),
                this.id.apply(dto.getCodLoanPurpose(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                LoanPurposeDTO.class,
                LoanPurposeEntity.class,
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
                LoanPurposeDTO.class,
                LoanPurposeEntity.class,
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
                LoanPurposeDTO.class,
                LoanPurposeEntity.class,
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
        TypeMap<LoanPurposeEntity, LoanPurposeDTO> dtoMap = mapper.createTypeMap(LoanPurposeEntity.class, LoanPurposeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodLoanPurpose(), LoanPurposeDTO::setCodLoanPurpose);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), LoanPurposeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, LoanPurposeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, LoanPurposeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, LoanPurposeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, LoanPurposeDTO::setLastCheckerDateTime);

        TypeMap<LoanPurposeDTO, LoanPurposeEntity> entityMap = mapper.createTypeMap(LoanPurposeDTO.class, LoanPurposeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, LoanPurposeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, LoanPurposeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, LoanPurposeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, LoanPurposeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, LoanPurposeEmbeddedKey> id = (code, status) -> LoanPurposeEmbeddedKey.builder().codLoanPurpose(code).codRecordStatus(status.name()).build();

    private Specification<LoanPurposeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codLoanPurpose"));
    }
}
