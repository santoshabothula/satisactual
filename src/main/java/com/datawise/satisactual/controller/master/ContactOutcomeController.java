package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.ContactOutcomeEmbeddedKey;
import com.datawise.satisactual.entities.master.ContactOutcomeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.ContactOutcomeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.ContactOutcomeRepository;
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
@RequestMapping("/mst/contact-outcome")
public class ContactOutcomeController {

    @Autowired
    private CommonMasterService<ContactOutcomeEntity, ContactOutcomeEmbeddedKey, ContactOutcomeDTO> service;
    @Autowired
    private ContactOutcomeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ContactOutcomeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ContactOutcomeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactOutcomeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, ContactOutcomeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ContactOutcomeDTO dto) {
        service.save(
                repository,
                dto,
                ContactOutcomeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodContactOutcome()),
                dto.getCodContactOutcome(),
                mapper,
                this.id.apply(dto.getCodContactOutcome(), CodRecordStatus.N),
                this.id.apply(dto.getCodContactOutcome(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ContactOutcomeDTO dto) {
        service.update(
                repository,
                dto,
                ContactOutcomeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodContactOutcome()),
                dto.getCodContactOutcome(),
                mapper,
                this.id.apply(dto.getCodContactOutcome(), CodRecordStatus.M),
                this.id.apply(dto.getCodContactOutcome(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                ContactOutcomeDTO.class,
                ContactOutcomeEntity.class,
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
                ContactOutcomeDTO.class,
                ContactOutcomeEntity.class,
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
                ContactOutcomeDTO.class,
                ContactOutcomeEntity.class,
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
        TypeMap<ContactOutcomeEntity, ContactOutcomeDTO> dtoMap = mapper.createTypeMap(ContactOutcomeEntity.class, ContactOutcomeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodContactOutcome(), ContactOutcomeDTO::setCodContactOutcome);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ContactOutcomeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ContactOutcomeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ContactOutcomeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ContactOutcomeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ContactOutcomeDTO::setLastCheckerDateTime);

        TypeMap<ContactOutcomeDTO, ContactOutcomeEntity> entityMap = mapper.createTypeMap(ContactOutcomeDTO.class, ContactOutcomeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ContactOutcomeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ContactOutcomeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ContactOutcomeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ContactOutcomeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, ContactOutcomeEmbeddedKey> id = (code, status) -> ContactOutcomeEmbeddedKey.builder().codContactOutcome(code).codRecordStatus(status.name()).build();

    private Specification<ContactOutcomeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codContactOutcome"));
    }
}
