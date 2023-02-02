package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.StmtCycleEmbeddedKey;
import com.datawise.satisactual.entities.master.StmtCycleEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.StmtCycleDTO;
import com.datawise.satisactual.repository.master.StmtCycleRepository;
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
@RequestMapping("/mst/stmt-cycle")
@CrossOrigin
public class StmtCycleController {

    @Autowired
    private CommonMasterService<StmtCycleEntity, StmtCycleEmbeddedKey, StmtCycleDTO> service;
    @Autowired
    private StmtCycleRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<StmtCycleDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, StmtCycleDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StmtCycleDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, StmtCycleDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody StmtCycleDTO dto) {
        service.save(
                repository,
                dto,
                StmtCycleEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodStmtCycle()),
                dto.getCodStmtCycle(),
                mapper,
                this.id.apply(dto.getCodStmtCycle(), CodRecordStatus.N),
                this.id.apply(dto.getCodStmtCycle(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody StmtCycleDTO dto) {
        service.update(
                repository,
                dto,
                StmtCycleEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodStmtCycle()),
                dto.getCodStmtCycle(),
                mapper,
                this.id.apply(dto.getCodStmtCycle(), CodRecordStatus.M),
                this.id.apply(dto.getCodStmtCycle(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                StmtCycleDTO.class,
                StmtCycleEntity.class,
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
                StmtCycleDTO.class,
                StmtCycleEntity.class,
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
                StmtCycleDTO.class,
                StmtCycleEntity.class,
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
        TypeMap<StmtCycleEntity, StmtCycleDTO> dtoMap = mapper.createTypeMap(StmtCycleEntity.class, StmtCycleDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodStmtCycle(), StmtCycleDTO::setCodStmtCycle);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), StmtCycleDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, StmtCycleDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, StmtCycleDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, StmtCycleDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, StmtCycleDTO::setLastCheckerDateTime);

        TypeMap<StmtCycleDTO, StmtCycleEntity> entityMap = mapper.createTypeMap(StmtCycleDTO.class, StmtCycleEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, StmtCycleEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, StmtCycleEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, StmtCycleEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, StmtCycleEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, StmtCycleEmbeddedKey> id = (code, status) -> StmtCycleEmbeddedKey.builder().codStmtCycle(code).codRecordStatus(status.name()).build();

    private Specification<StmtCycleEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codStmtCycle"));
    }
}
