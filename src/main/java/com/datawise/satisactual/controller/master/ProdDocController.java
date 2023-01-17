package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.ProdDocEmbeddedKey;
import com.datawise.satisactual.entities.master.ProdDocEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.ProdDocDTO;
import com.datawise.satisactual.repository.master.ProdDocRepository;
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
@RequestMapping("/mst/prod-doc")
public class ProdDocController {

    @Autowired
    private CommonMasterService<ProdDocEntity, ProdDocEmbeddedKey, ProdDocDTO> service;
    @Autowired
    private ProdDocRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ProdDocDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ProdDocDTO.class));
    }

    @GetMapping("/{product}/{doc-type}/{doc-purpose}")
    public ResponseEntity<ProdDocDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("product") String product,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-type") String type,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-purpose") String purpose
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(CodRecordStatus.A, product, type, purpose), mapper, ProdDocDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ProdDocDTO dto) {
        service.save(
                repository,
                dto,
                ProdDocEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodProduct(), dto.getCodDocType(), dto.getDocPurpose()),
                dto.getCodProduct() + ";" + dto.getCodDocType() + ";" + dto.getDocPurpose(),
                mapper,
                this.id(CodRecordStatus.N, dto.getCodProduct(), dto.getCodDocType(), dto.getDocPurpose()),
                this.id(CodRecordStatus.A, dto.getCodProduct(), dto.getCodDocType(), dto.getDocPurpose())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ProdDocDTO dto) {
        service.update(
                repository,
                dto,
                ProdDocEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodProduct(), dto.getCodDocType(), dto.getDocPurpose()),
                dto.getCodProduct() + ";" + dto.getCodDocType() + ";" + dto.getDocPurpose(),
                mapper,
                this.id(CodRecordStatus.M, dto.getCodProduct(), dto.getCodDocType(), dto.getDocPurpose()),
                this.id(CodRecordStatus.A, dto.getCodProduct(), dto.getCodDocType(), dto.getDocPurpose())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{product}/{doc-type}/{doc-purpose}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("product") String product,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-type") String type,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-purpose") String purpose
    ) {
        service.delete(
                repository,
                ProdDocDTO.class,
                ProdDocEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), product, type, purpose),
                product + ";" + type + ";" + purpose,
                mapper,
                this.id(CodRecordStatus.X, product, type, purpose),
                this.id(CodRecordStatus.C, product, type, purpose),
                this.id(CodRecordStatus.A, product, type, purpose)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{product}/{doc-type}/{doc-purpose}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("product") String product,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-type") String type,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-purpose") String purpose
    ) {
        service.reopen(
                repository,
                ProdDocDTO.class,
                ProdDocEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), product, type, purpose),
                product + ";" + type + ";" + purpose,
                mapper,
                this.id(CodRecordStatus.R, product, type, purpose),
                this.id(CodRecordStatus.A, product, type, purpose),
                this.id(CodRecordStatus.C, product, type, purpose)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{product}/{doc-type}/{doc-purpose}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("product") String product,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-type") String type,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("doc-purpose") String purpose
    ) {
        service.authorize(
                repository,
                ProdDocDTO.class,
                ProdDocEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), product, type, purpose),
                product + ";" + type + ";" + purpose,
                mapper,
                this.id(CodRecordStatus.A, product, type, purpose),
                this.id(CodRecordStatus.C, product, type, purpose)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<ProdDocEntity, ProdDocDTO> dtoMap = mapper.createTypeMap(ProdDocEntity.class, ProdDocDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDocType(), ProdDocDTO::setCodDocType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ProdDocDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodProduct(), ProdDocDTO::setCodProduct);
        dtoMap.addMapping(c -> c.getId().getDocPurpose(), ProdDocDTO::setDocPurpose);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ProdDocDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ProdDocDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ProdDocDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ProdDocDTO::setLastCheckerDateTime);

        TypeMap<ProdDocDTO, ProdDocEntity> entityMap = mapper.createTypeMap(ProdDocDTO.class, ProdDocEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ProdDocEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ProdDocEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ProdDocEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ProdDocEntity::setLastCheckerDateTime);
    }

    private ProdDocEmbeddedKey id(CodRecordStatus status, String product, String type, String purpose) {
        return ProdDocEmbeddedKey.builder().codRecordStatus(status.name()).codProduct(product).codDocType(type).docPurpose(purpose).build();
    }

    private Specification<ProdDocEntity> getSpec(List<CodRecordStatus> statuses, String product, String type, String purpose) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(product, "codProduct"))
                .and(repository.findRecordWithCode(purpose, "docPurpose"))
                .and(repository.findRecordWithCode(type, "codDocType"));
    }
}
