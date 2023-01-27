package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.OccupationEmbeddedKey;
import com.datawise.satisactual.entities.master.OccupationEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.OccupationDTO;
import com.datawise.satisactual.repository.master.OccupationRepository;
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
@RequestMapping("/mst/occupation")
public class OccupationController {

    @Autowired
    private CommonMasterService<OccupationEntity, OccupationEmbeddedKey, OccupationDTO> service;
    @Autowired
    private OccupationRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<OccupationDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, OccupationDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccupationDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, OccupationDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody OccupationDTO dto) {
        service.save(
                repository,
                dto,
                OccupationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodOccupation()),
                dto.getCodOccupation(),
                mapper,
                this.id.apply(dto.getCodOccupation(), CodRecordStatus.N),
                this.id.apply(dto.getCodOccupation(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody OccupationDTO dto) {
        service.update(
                repository,
                dto,
                OccupationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodOccupation()),
                dto.getCodOccupation(),
                mapper,
                this.id.apply(dto.getCodOccupation(), CodRecordStatus.M),
                this.id.apply(dto.getCodOccupation(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                OccupationDTO.class,
                OccupationEntity.class,
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
                OccupationDTO.class,
                OccupationEntity.class,
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
                OccupationDTO.class,
                OccupationEntity.class,
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
        TypeMap<OccupationEntity, OccupationDTO> dtoMap = mapper.createTypeMap(OccupationEntity.class, OccupationDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodOccupation(), OccupationDTO::setCodOccupation);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), OccupationDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, OccupationDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, OccupationDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, OccupationDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, OccupationDTO::setLastCheckerDateTime);

        TypeMap<OccupationDTO, OccupationEntity> entityMap = mapper.createTypeMap(OccupationDTO.class, OccupationEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, OccupationEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, OccupationEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, OccupationEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, OccupationEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, OccupationEmbeddedKey> id = (code, status) -> OccupationEmbeddedKey.builder().codOccupation(code).codRecordStatus(status.name()).build();

    private Specification<OccupationEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codOccupation"));
    }
}
