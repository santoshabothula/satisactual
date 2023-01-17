package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.DocTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.DocTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.DocTypeDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.DocTypeRepository;
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

@Validated
@RestController
@RequestMapping("/mst/doc-type")
public class DocTypeController {

    @Autowired
    private CommonMasterService<DocTypeEntity, DocTypeEmbeddedKey, DocTypeDTO> service;
    @Autowired
    private DocTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<DocTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, DocTypeDTO.class));
    }

    @GetMapping("/{id}/{doc-type}/{doc-purpose}")
    public ResponseEntity<DocTypeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-type") String docType,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("doc-purpose") String docPurpose
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(docType, CodRecordStatus.A, docPurpose), mapper, DocTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody DocTypeDTO dto) {
        service.save(
                repository,
                dto,
                DocTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDocType(), dto.getEnuDocPurpose()),
                dto.getCodDocType() + ";" + dto.getEnuDocPurpose(),
                mapper,
                this.id(dto.getCodDocType(), CodRecordStatus.N, dto.getEnuDocPurpose()),
                this.id(dto.getCodDocType(), CodRecordStatus.A, dto.getEnuDocPurpose())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody DocTypeDTO dto) {
        service.update(
                repository,
                dto,
                DocTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDocType(), dto.getEnuDocPurpose()),
                dto.getCodDocType() + ";" + dto.getEnuDocPurpose(),
                mapper,
                this.id(dto.getCodDocType(), CodRecordStatus.M, dto.getEnuDocPurpose()),
                this.id(dto.getCodDocType(), CodRecordStatus.A, dto.getEnuDocPurpose())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{doc-type}/{doc-purpose}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-type") String docType,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("doc-purpose") String docPurpose
    ) {
        service.delete(
                repository,
                DocTypeDTO.class,
                DocTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()),docType, docPurpose),
                docType + ";" + docPurpose,
                mapper,
                this.id(docType, CodRecordStatus.X, docPurpose),
                this.id(docType, CodRecordStatus.C, docPurpose),
                this.id(docType, CodRecordStatus.A, docPurpose)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{doc-type}/{doc-purpose}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-type") String docType,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("doc-purpose") String docPurpose
    ) {
        service.reopen(
                repository,
                DocTypeDTO.class,
                DocTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), docType, docPurpose),
                docType + ";" + docPurpose,
                mapper,
                this.id(docType, CodRecordStatus.R, docPurpose),
                this.id(docType, CodRecordStatus.A, docPurpose),
                this.id(docType, CodRecordStatus.C, docPurpose)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{doc-type}/{doc-purpose}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-type") String docType,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("doc-purpose") String docPurpose
    ) {
        service.authorize(
                repository,
                DocTypeDTO.class,
                DocTypeEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), docType, docPurpose),
                docType + ";" + docPurpose,
                mapper,
                this.id(docType, CodRecordStatus.A, docPurpose),
                this.id(docType, CodRecordStatus.C, docPurpose)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<DocTypeEntity, DocTypeDTO> dtoMap = mapper.createTypeMap(DocTypeEntity.class, DocTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDocType(), DocTypeDTO::setCodDocType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), DocTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getEnuDocPurpose(), DocTypeDTO::setEnuDocPurpose);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, DocTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, DocTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, DocTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, DocTypeDTO::setLastCheckerDateTime);

        TypeMap<DocTypeDTO, DocTypeEntity> entityMap = mapper.createTypeMap(DocTypeDTO.class, DocTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, DocTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, DocTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, DocTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, DocTypeEntity::setLastCheckerDateTime);
    }

    private DocTypeEmbeddedKey id(String type, CodRecordStatus status, String purpose) {
        return DocTypeEmbeddedKey.builder().codDocType(type).codRecordStatus(status.name()).enuDocPurpose(purpose).build();
    }

    private Specification<DocTypeEntity> getSpec(List<CodRecordStatus> statuses, String docType, String docPurpose) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(docType, "codDocType"))
                .and(repository.findRecordWithCode(docPurpose, "enuDocPurpose"));
    }
}
