package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.DocStorageLocationEmbeddedKey;
import com.datawise.satisactual.entities.master.DocStorageLocationEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.DocStorageLocationDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.DocStorageLocationRepository;
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
@RequestMapping("/mst/doc-storage-location")
public class DocStorageLocationController {

    @Autowired
    private CommonMasterService<DocStorageLocationEntity, DocStorageLocationEmbeddedKey, DocStorageLocationDTO> service;
    @Autowired
    private DocStorageLocationRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<DocStorageLocationDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, DocStorageLocationDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocStorageLocationDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, DocStorageLocationDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody DocStorageLocationDTO dto) {
        service.save(
                repository,
                dto,
                DocStorageLocationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodStorageLocation()),
                dto.getCodStorageLocation(),
                mapper,
                this.id.apply(dto.getCodStorageLocation(), CodRecordStatus.N),
                this.id.apply(dto.getCodStorageLocation(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody DocStorageLocationDTO dto) {
        service.update(
                repository,
                dto,
                DocStorageLocationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodStorageLocation()),
                dto.getCodStorageLocation(),
                mapper,
                this.id.apply(dto.getCodStorageLocation(), CodRecordStatus.M),
                this.id.apply(dto.getCodStorageLocation(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                DocStorageLocationDTO.class,
                DocStorageLocationEntity.class,
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
                DocStorageLocationDTO.class,
                DocStorageLocationEntity.class,
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
                DocStorageLocationDTO.class,
                DocStorageLocationEntity.class,
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
        TypeMap<DocStorageLocationEntity, DocStorageLocationDTO> dtoMap = mapper.createTypeMap(DocStorageLocationEntity.class, DocStorageLocationDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodStorageLocation(), DocStorageLocationDTO::setCodStorageLocation);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), DocStorageLocationDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, DocStorageLocationDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, DocStorageLocationDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, DocStorageLocationDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, DocStorageLocationDTO::setLastCheckerDateTime);

        TypeMap<DocStorageLocationDTO, DocStorageLocationEntity> entityMap = mapper.createTypeMap(DocStorageLocationDTO.class, DocStorageLocationEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, DocStorageLocationEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, DocStorageLocationEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, DocStorageLocationEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, DocStorageLocationEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, DocStorageLocationEmbeddedKey> id = (code, status) -> DocStorageLocationEmbeddedKey.builder().codStorageLocation(code).codRecordStatus(status.name()).build();

    private Specification<DocStorageLocationEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codStorageLocation"));
    }
}