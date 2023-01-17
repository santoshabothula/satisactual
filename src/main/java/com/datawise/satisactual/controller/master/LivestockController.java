package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.LivestockEmbeddedKey;
import com.datawise.satisactual.entities.master.LivestockEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.LivestockDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.LivestockRepository;
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
@RequestMapping("/mst/livestock")
public class LivestockController {

    @Autowired
    private CommonMasterService<LivestockEntity, LivestockEmbeddedKey, LivestockDTO> service;
    @Autowired
    private LivestockRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<LivestockDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, LivestockDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivestockDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, LivestockDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody LivestockDTO dto) {
        service.save(
                repository,
                dto,
                LivestockEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodLivestock()),
                dto.getCodLivestock(),
                mapper,
                this.id.apply(dto.getCodLivestock(), CodRecordStatus.N),
                this.id.apply(dto.getCodLivestock(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody LivestockDTO dto) {
        service.update(
                repository,
                dto,
                LivestockEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodLivestock()),
                dto.getCodLivestock(),
                mapper,
                this.id.apply(dto.getCodLivestock(), CodRecordStatus.M),
                this.id.apply(dto.getCodLivestock(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                LivestockDTO.class,
                LivestockEntity.class,
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
                LivestockDTO.class,
                LivestockEntity.class,
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
                LivestockDTO.class,
                LivestockEntity.class,
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
        TypeMap<LivestockEntity, LivestockDTO> dtoMap = mapper.createTypeMap(LivestockEntity.class, LivestockDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodLivestock(), LivestockDTO::setCodLivestock);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), LivestockDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, LivestockDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, LivestockDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, LivestockDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, LivestockDTO::setLastCheckerDateTime);

        TypeMap<LivestockDTO, LivestockEntity> entityMap = mapper.createTypeMap(LivestockDTO.class, LivestockEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, LivestockEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, LivestockEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, LivestockEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, LivestockEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, LivestockEmbeddedKey> id = (code, status) -> LivestockEmbeddedKey.builder().codLivestock(code).codRecordStatus(status.name()).build();

    private Specification<LivestockEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codLivestock"));
    }
}
