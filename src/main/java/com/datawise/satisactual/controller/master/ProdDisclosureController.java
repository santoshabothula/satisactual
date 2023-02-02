package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.ProdDisclosureEmbeddedKey;
import com.datawise.satisactual.entities.master.ProdDisclosureEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.ProdDisclosureDTO;
import com.datawise.satisactual.repository.master.ProdDisclosureRepository;
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
@RequestMapping("/mst/prod-disclosure")
@CrossOrigin
public class ProdDisclosureController {

    @Autowired
    private CommonMasterService<ProdDisclosureEntity, ProdDisclosureEmbeddedKey, ProdDisclosureDTO> service;
    @Autowired
    private ProdDisclosureRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ProdDisclosureDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ProdDisclosureDTO.class));
    }

    @GetMapping("/{disclosure}/{product}")
    public ResponseEntity<ProdDisclosureDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("disclosure") String disclosure,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("product") String product
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(disclosure, CodRecordStatus.A, product), mapper, ProdDisclosureDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ProdDisclosureDTO dto) {
        service.save(
                repository,
                dto,
                ProdDisclosureEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDisclosure(), dto.getCodProduct()),
                dto.getCodDisclosure() + ";" + dto.getCodProduct(),
                mapper,
                this.id(dto.getCodDisclosure(), CodRecordStatus.N, dto.getCodProduct()),
                this.id(dto.getCodDisclosure(), CodRecordStatus.A, dto.getCodProduct())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ProdDisclosureDTO dto) {
        service.update(
                repository,
                dto,
                ProdDisclosureEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDisclosure(), dto.getCodProduct()),
                dto.getCodDisclosure() + ";" + dto.getCodProduct(),
                mapper,
                this.id(dto.getCodDisclosure(), CodRecordStatus.M, dto.getCodProduct()),
                this.id(dto.getCodDisclosure(), CodRecordStatus.A, dto.getCodProduct())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{disclosure}/{product}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("disclosure") String disclosure,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("product") String product
    ) {
        service.delete(
                repository,
                ProdDisclosureDTO.class,
                ProdDisclosureEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), disclosure, product),
                disclosure + ";" + product,
                mapper,
                this.id(disclosure, CodRecordStatus.X, product),
                this.id(disclosure, CodRecordStatus.C, product),
                this.id(disclosure, CodRecordStatus.A, product)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{disclosure}/{product}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("disclosure") String disclosure,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("product") String product
    ) {
        service.reopen(
                repository,
                ProdDisclosureDTO.class,
                ProdDisclosureEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), disclosure, product),
                disclosure + ";" + product,
                mapper,
                this.id(disclosure, CodRecordStatus.R, product),
                this.id(disclosure, CodRecordStatus.A, product),
                this.id(disclosure, CodRecordStatus.C, product)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{disclosure}/{product}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("disclosure") String disclosure,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("product") String product
    ) {
        service.authorize(
                repository,
                ProdDisclosureDTO.class,
                ProdDisclosureEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), disclosure, product),
                disclosure + ";" + product,
                mapper,
                this.id(disclosure, CodRecordStatus.A, product),
                this.id(disclosure, CodRecordStatus.C, product)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<ProdDisclosureEntity, ProdDisclosureDTO> dtoMap = mapper.createTypeMap(ProdDisclosureEntity.class, ProdDisclosureDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDisclosure(), ProdDisclosureDTO::setCodDisclosure);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ProdDisclosureDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodProduct(), ProdDisclosureDTO::setCodProduct);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ProdDisclosureDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ProdDisclosureDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ProdDisclosureDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ProdDisclosureDTO::setLastCheckerDateTime);

        TypeMap<ProdDisclosureDTO, ProdDisclosureEntity> entityMap = mapper.createTypeMap(ProdDisclosureDTO.class, ProdDisclosureEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ProdDisclosureEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ProdDisclosureEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ProdDisclosureEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ProdDisclosureEntity::setLastCheckerDateTime);
    }

    private ProdDisclosureEmbeddedKey id(String code, CodRecordStatus status, String product) {
        return ProdDisclosureEmbeddedKey.builder().codDisclosure(code).codRecordStatus(status.name()).codProduct(product).build();
    }

    private Specification<ProdDisclosureEntity> getSpec(List<CodRecordStatus> statuses, String code, String product) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codDisclosure"))
                .and(repository.findRecordWithCode(product, "codProduct"));
    }
}
