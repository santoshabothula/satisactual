package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.ProdCertificateEmbeddedKey;
import com.datawise.satisactual.entities.master.ProdCertificateEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.ProdCertificateDTO;
import com.datawise.satisactual.repository.master.ProdCertificateRepository;
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
@RequestMapping("/mst/prod-certificate")
public class ProdCertificateController {

    @Autowired
    private CommonMasterService<ProdCertificateEntity, ProdCertificateEmbeddedKey, ProdCertificateDTO> service;
    @Autowired
    private ProdCertificateRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ProdCertificateDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ProdCertificateDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdCertificateDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, ProdCertificateDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ProdCertificateDTO dto) {
        service.save(
                repository,
                dto,
                ProdCertificateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCertificate()),
                dto.getCodCertificate(),
                mapper,
                this.id.apply(dto.getCodCertificate(), CodRecordStatus.N),
                this.id.apply(dto.getCodCertificate(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ProdCertificateDTO dto) {
        service.update(
                repository,
                dto,
                ProdCertificateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCertificate()),
                dto.getCodCertificate(),
                mapper,
                this.id.apply(dto.getCodCertificate(), CodRecordStatus.M),
                this.id.apply(dto.getCodCertificate(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                ProdCertificateDTO.class,
                ProdCertificateEntity.class,
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
                ProdCertificateDTO.class,
                ProdCertificateEntity.class,
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
                ProdCertificateDTO.class,
                ProdCertificateEntity.class,
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
        TypeMap<ProdCertificateEntity, ProdCertificateDTO> dtoMap = mapper.createTypeMap(ProdCertificateEntity.class, ProdCertificateDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCertificate(), ProdCertificateDTO::setCodCertificate);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ProdCertificateDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ProdCertificateDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ProdCertificateDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ProdCertificateDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ProdCertificateDTO::setLastCheckerDateTime);

        TypeMap<ProdCertificateDTO, ProdCertificateEntity> entityMap = mapper.createTypeMap(ProdCertificateDTO.class, ProdCertificateEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ProdCertificateEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ProdCertificateEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ProdCertificateEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ProdCertificateEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, ProdCertificateEmbeddedKey> id = (code, status) -> ProdCertificateEmbeddedKey.builder().codCertificate(code).codRecordStatus(status.name()).build();

    private Specification<ProdCertificateEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codCertificate"));
    }
}
