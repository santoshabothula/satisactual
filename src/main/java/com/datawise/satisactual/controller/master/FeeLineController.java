package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.FeeLineEmbeddedKey;
import com.datawise.satisactual.entities.master.FeeLineEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.FeeLineDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.FeeLineRepository;
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
@RequestMapping("/mst/fee-line")
@CrossOrigin
public class FeeLineController {

    @Autowired
    private CommonMasterService<FeeLineEntity, FeeLineEmbeddedKey, FeeLineDTO> service;
    @Autowired
    private FeeLineRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<FeeLineDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, FeeLineDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeLineDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, FeeLineDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody FeeLineDTO dto) {
        service.save(
                repository,
                dto,
                FeeLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFeeLine()),
                dto.getCodFeeLine(),
                mapper,
                this.id.apply(dto.getCodFeeLine(), CodRecordStatus.N),
                this.id.apply(dto.getCodFeeLine(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody FeeLineDTO dto) {
        service.update(
                repository,
                dto,
                FeeLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFeeLine()),
                dto.getCodFeeLine(),
                mapper,
                this.id.apply(dto.getCodFeeLine(), CodRecordStatus.M),
                this.id.apply(dto.getCodFeeLine(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                FeeLineDTO.class,
                FeeLineEntity.class,
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
                FeeLineDTO.class,
                FeeLineEntity.class,
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
                FeeLineDTO.class,
                FeeLineEntity.class,
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
        TypeMap<FeeLineEntity, FeeLineDTO> dtoMap = mapper.createTypeMap(FeeLineEntity.class, FeeLineDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodFeeLine(), FeeLineDTO::setCodFeeLine);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), FeeLineDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, FeeLineDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, FeeLineDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, FeeLineDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, FeeLineDTO::setLastCheckerDateTime);

        TypeMap<FeeLineDTO, FeeLineEntity> entityMap = mapper.createTypeMap(FeeLineDTO.class, FeeLineEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, FeeLineEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, FeeLineEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, FeeLineEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, FeeLineEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, FeeLineEmbeddedKey> id = (code, status) -> FeeLineEmbeddedKey.builder().codFeeLine(code).codRecordStatus(status.name()).build();

    private Specification<FeeLineEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codFeeLine"));
    }
}
