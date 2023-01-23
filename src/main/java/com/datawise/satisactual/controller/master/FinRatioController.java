package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.FinRatioEmbeddedKey;
import com.datawise.satisactual.entities.master.FinRatioEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.FinRatioDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.FinRatioRepository;
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
@RequestMapping("/mst/fin-ratio")
public class FinRatioController {

    @Autowired
    private CommonMasterService<FinRatioEntity, FinRatioEmbeddedKey, FinRatioDTO> service;
    @Autowired
    private FinRatioRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<FinRatioDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, FinRatioDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinRatioDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, FinRatioDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody FinRatioDTO dto) {
        service.save(
                repository,
                dto,
                FinRatioEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodRatio()),
                dto.getCodRatio(),
                mapper,
                this.id.apply(dto.getCodRatio(), CodRecordStatus.N),
                this.id.apply(dto.getCodRatio(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody FinRatioDTO dto) {
        service.update(
                repository,
                dto,
                FinRatioEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodRatio()),
                dto.getCodRatio(),
                mapper,
                this.id.apply(dto.getCodRatio(), CodRecordStatus.M),
                this.id.apply(dto.getCodRatio(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                FinRatioDTO.class,
                FinRatioEntity.class,
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
                FinRatioDTO.class,
                FinRatioEntity.class,
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
                FinRatioDTO.class,
                FinRatioEntity.class,
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
        TypeMap<FinRatioEntity, FinRatioDTO> dtoMap = mapper.createTypeMap(FinRatioEntity.class, FinRatioDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodRatio(), FinRatioDTO::setCodRatio);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), FinRatioDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, FinRatioDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, FinRatioDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, FinRatioDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, FinRatioDTO::setLastCheckerDateTime);

        TypeMap<FinRatioDTO, FinRatioEntity> entityMap = mapper.createTypeMap(FinRatioDTO.class, FinRatioEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, FinRatioEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, FinRatioEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, FinRatioEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, FinRatioEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, FinRatioEmbeddedKey> id = (code, status) -> FinRatioEmbeddedKey.builder().codRatio(code).codRecordStatus(status.name()).build();

    private Specification<FinRatioEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codRatio"));
    }
}
