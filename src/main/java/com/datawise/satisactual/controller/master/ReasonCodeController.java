package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.ReasonCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.ReasonCodeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.ReasonCodeDTO;
import com.datawise.satisactual.repository.master.ReasonCodeRepository;
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
@RequestMapping("/mst/reason-code")
public class ReasonCodeController {

    @Autowired
    private CommonMasterService<ReasonCodeEntity, ReasonCodeEmbeddedKey, ReasonCodeDTO> service;
    @Autowired
    private ReasonCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ReasonCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ReasonCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReasonCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, ReasonCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ReasonCodeDTO dto) {
        service.save(
                repository,
                dto,
                ReasonCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodReason()),
                dto.getCodReason(),
                mapper,
                this.id.apply(dto.getCodReason(), CodRecordStatus.N),
                this.id.apply(dto.getCodReason(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ReasonCodeDTO dto) {
        service.update(
                repository,
                dto,
                ReasonCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodReason()),
                dto.getCodReason(),
                mapper,
                this.id.apply(dto.getCodReason(), CodRecordStatus.M),
                this.id.apply(dto.getCodReason(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                ReasonCodeDTO.class,
                ReasonCodeEntity.class,
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
                ReasonCodeDTO.class,
                ReasonCodeEntity.class,
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
                ReasonCodeDTO.class,
                ReasonCodeEntity.class,
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
        TypeMap<ReasonCodeEntity, ReasonCodeDTO> dtoMap = mapper.createTypeMap(ReasonCodeEntity.class, ReasonCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodReason(), ReasonCodeDTO::setCodReason);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ReasonCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ReasonCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ReasonCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ReasonCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ReasonCodeDTO::setLastCheckerDateTime);

        TypeMap<ReasonCodeDTO, ReasonCodeEntity> entityMap = mapper.createTypeMap(ReasonCodeDTO.class, ReasonCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ReasonCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ReasonCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ReasonCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ReasonCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, ReasonCodeEmbeddedKey> id = (code, status) -> ReasonCodeEmbeddedKey.builder().codReason(code).codRecordStatus(status.name()).build();

    private Specification<ReasonCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codReason"));
    }
}