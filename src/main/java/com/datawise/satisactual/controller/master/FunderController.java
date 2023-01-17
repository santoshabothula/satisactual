package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.FunderEmbeddedKey;
import com.datawise.satisactual.entities.master.FunderEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.FunderDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.FunderRepository;
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
@RequestMapping("/mst/funder")
public class FunderController {

    @Autowired
    private CommonMasterService<FunderEntity, FunderEmbeddedKey, FunderDTO> service;
    @Autowired
    private FunderRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<FunderDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, FunderDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunderDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, FunderDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody FunderDTO dto) {
        service.save(
                repository,
                dto,
                FunderEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFunder()),
                dto.getCodFunder(),
                mapper,
                this.id.apply(dto.getCodFunder(), CodRecordStatus.N),
                this.id.apply(dto.getCodFunder(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody FunderDTO dto) {
        service.update(
                repository,
                dto,
                FunderEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFunder()),
                dto.getCodFunder(),
                mapper,
                this.id.apply(dto.getCodFunder(), CodRecordStatus.M),
                this.id.apply(dto.getCodFunder(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                FunderDTO.class,
                FunderEntity.class,
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
                FunderDTO.class,
                FunderEntity.class,
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
                FunderDTO.class,
                FunderEntity.class,
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
        TypeMap<FunderEntity, FunderDTO> dtoMap = mapper.createTypeMap(FunderEntity.class, FunderDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodFunder(), FunderDTO::setCodFunder);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), FunderDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, FunderDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, FunderDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, FunderDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, FunderDTO::setLastCheckerDateTime);

        TypeMap<FunderDTO, FunderEntity> entityMap = mapper.createTypeMap(FunderDTO.class, FunderEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, FunderEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, FunderEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, FunderEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, FunderEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, FunderEmbeddedKey> id = (code, status) -> FunderEmbeddedKey.builder().codFunder(code).codRecordStatus(status.name()).build();

    private Specification<FunderEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codFunder"));
    }
}