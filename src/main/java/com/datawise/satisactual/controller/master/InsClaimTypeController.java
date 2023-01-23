package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.InsClaimTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.InsClaimTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.InsClaimTypeDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.InsClaimTypeRepository;
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
@RequestMapping("/mst/ins-claim-type")
public class InsClaimTypeController {

    @Autowired
    private CommonMasterService<InsClaimTypeEntity, InsClaimTypeEmbeddedKey, InsClaimTypeDTO> service;
    @Autowired
    private InsClaimTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<InsClaimTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, InsClaimTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsClaimTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, InsClaimTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody InsClaimTypeDTO dto) {
        service.save(
                repository,
                dto,
                InsClaimTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodClaimTyp()),
                dto.getCodClaimTyp(),
                mapper,
                this.id.apply(dto.getCodClaimTyp(), CodRecordStatus.N),
                this.id.apply(dto.getCodClaimTyp(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody InsClaimTypeDTO dto) {
        service.update(
                repository,
                dto,
                InsClaimTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodClaimTyp()),
                dto.getCodClaimTyp(),
                mapper,
                this.id.apply(dto.getCodClaimTyp(), CodRecordStatus.M),
                this.id.apply(dto.getCodClaimTyp(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                InsClaimTypeDTO.class,
                InsClaimTypeEntity.class,
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
                InsClaimTypeDTO.class,
                InsClaimTypeEntity.class,
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
                InsClaimTypeDTO.class,
                InsClaimTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<InsClaimTypeEntity, InsClaimTypeDTO> dtoMap = mapper.createTypeMap(InsClaimTypeEntity.class, InsClaimTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodClaimTyp(), InsClaimTypeDTO::setCodClaimTyp);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), InsClaimTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, InsClaimTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, InsClaimTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, InsClaimTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, InsClaimTypeDTO::setLastCheckerDateTime);

        TypeMap<InsClaimTypeDTO, InsClaimTypeEntity> entityMap = mapper.createTypeMap(InsClaimTypeDTO.class, InsClaimTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, InsClaimTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, InsClaimTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, InsClaimTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, InsClaimTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, InsClaimTypeEmbeddedKey> id = (code, status) -> InsClaimTypeEmbeddedKey.builder().codClaimTyp(code).codRecordStatus(status.name()).build();

    private Specification<InsClaimTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codClaimTyp"));
    }
}
