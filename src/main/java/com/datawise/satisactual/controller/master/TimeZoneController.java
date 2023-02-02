package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.TimeZoneEmbeddedKey;
import com.datawise.satisactual.entities.master.TimeZoneEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.TimeZoneDTO;
import com.datawise.satisactual.repository.master.TimeZoneRepository;
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
@RequestMapping("/mst/time-zone")
@CrossOrigin
public class TimeZoneController {

    @Autowired
    private CommonMasterService<TimeZoneEntity, TimeZoneEmbeddedKey, TimeZoneDTO> service;
    @Autowired
    private TimeZoneRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<TimeZoneDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, TimeZoneDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeZoneDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, TimeZoneDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody TimeZoneDTO dto) {
        service.save(
                repository,
                dto,
                TimeZoneEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTimeZone()),
                dto.getCodTimeZone(),
                mapper,
                this.id.apply(dto.getCodTimeZone(), CodRecordStatus.N),
                this.id.apply(dto.getCodTimeZone(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody TimeZoneDTO dto) {
        service.update(
                repository,
                dto,
                TimeZoneEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTimeZone()),
                dto.getCodTimeZone(),
                mapper,
                this.id.apply(dto.getCodTimeZone(), CodRecordStatus.M),
                this.id.apply(dto.getCodTimeZone(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                TimeZoneDTO.class,
                TimeZoneEntity.class,
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
                TimeZoneDTO.class,
                TimeZoneEntity.class,
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
                TimeZoneDTO.class,
                TimeZoneEntity.class,
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
        TypeMap<TimeZoneEntity, TimeZoneDTO> dtoMap = mapper.createTypeMap(TimeZoneEntity.class, TimeZoneDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodTimeZone(), TimeZoneDTO::setCodTimeZone);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), TimeZoneDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, TimeZoneDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, TimeZoneDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, TimeZoneDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, TimeZoneDTO::setLastCheckerDateTime);

        TypeMap<TimeZoneDTO, TimeZoneEntity> entityMap = mapper.createTypeMap(TimeZoneDTO.class, TimeZoneEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, TimeZoneEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, TimeZoneEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, TimeZoneEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, TimeZoneEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, TimeZoneEmbeddedKey> id = (code, status) -> TimeZoneEmbeddedKey.builder().codTimeZone(code).codRecordStatus(status.name()).build();

    private Specification<TimeZoneEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codTimeZone"));
    }
}
