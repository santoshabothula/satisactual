package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.PrefixEmbeddedKey;
import com.datawise.satisactual.entities.master.PrefixEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.PrefixDTO;
import com.datawise.satisactual.repository.master.PrefixRepository;
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
@RequestMapping("/mst/prefix")
@CrossOrigin
public class PrefixController {

    @Autowired
    private CommonMasterService<PrefixEntity, PrefixEmbeddedKey, PrefixDTO> service;
    @Autowired
    private PrefixRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<PrefixDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, PrefixDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrefixDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, PrefixDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody PrefixDTO dto) {
        service.save(
                repository,
                dto,
                PrefixEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPrefix()),
                dto.getCodPrefix(),
                mapper,
                this.id.apply(dto.getCodPrefix(), CodRecordStatus.N),
                this.id.apply(dto.getCodPrefix(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody PrefixDTO dto) {
        service.update(
                repository,
                dto,
                PrefixEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPrefix()),
                dto.getCodPrefix(),
                mapper,
                this.id.apply(dto.getCodPrefix(), CodRecordStatus.M),
                this.id.apply(dto.getCodPrefix(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                PrefixDTO.class,
                PrefixEntity.class,
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
                PrefixDTO.class,
                PrefixEntity.class,
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
                PrefixDTO.class,
                PrefixEntity.class,
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
        TypeMap<PrefixEntity, PrefixDTO> dtoMap = mapper.createTypeMap(PrefixEntity.class, PrefixDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodPrefix(), PrefixDTO::setCodPrefix);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), PrefixDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, PrefixDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, PrefixDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, PrefixDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, PrefixDTO::setLastCheckerDateTime);

        TypeMap<PrefixDTO, PrefixEntity> entityMap = mapper.createTypeMap(PrefixDTO.class, PrefixEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, PrefixEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, PrefixEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, PrefixEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, PrefixEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, PrefixEmbeddedKey> id = (code, status) -> PrefixEmbeddedKey.builder().codPrefix(code).codRecordStatus(status.name()).build();

    private Specification<PrefixEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codPrefix"));
    }
}
