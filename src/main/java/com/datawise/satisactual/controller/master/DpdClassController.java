package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.DpdClassEmbeddedKey;
import com.datawise.satisactual.entities.master.DpdClassEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.enums.FlagYesNo;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.DpdClassDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.DpdClassRepository;
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
@RequestMapping("/mst/dpd-class")
public class DpdClassController {

    @Autowired
    private CommonMasterService<DpdClassEntity, DpdClassEmbeddedKey, DpdClassDTO> service;
    @Autowired
    private DpdClassRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<DpdClassDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, DpdClassDTO.class));
    }

    @GetMapping("/{dpd-class}/{flg-rewrite}")
    public ResponseEntity<DpdClassDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("dpd-class") String dpdClass,
            @PathVariable("flg-rewrite") FlagYesNo isRewrite
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(dpdClass, CodRecordStatus.A, isRewrite.name()), mapper, DpdClassDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody DpdClassDTO dto) {
        service.save(
                repository,
                dto,
                DpdClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDpdClass(), dto.getIsRewrite()),
                dto.getCodDpdClass() + ";" + dto.getIsRewrite(),
                mapper,
                this.id(dto.getCodDpdClass(), CodRecordStatus.N, dto.getIsRewrite()),
                this.id(dto.getCodDpdClass(), CodRecordStatus.A, dto.getIsRewrite())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody DpdClassDTO dto) {
        service.update(
                repository,
                dto,
                DpdClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDpdClass(), dto.getIsRewrite()),
                dto.getCodDpdClass() + ";" + dto.getIsRewrite(),
                mapper,
                this.id(dto.getCodDpdClass(), CodRecordStatus.M, dto.getIsRewrite()),
                this.id(dto.getCodDpdClass(), CodRecordStatus.A, dto.getIsRewrite())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{dpd-class}/{flg-rewrite}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("dpd-class") String dpdClass,
            @PathVariable("flg-rewrite") FlagYesNo docPurpose
    ) {
        service.delete(
                repository,
                DpdClassDTO.class,
                DpdClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()),dpdClass, docPurpose.name()), dpdClass + ";" + docPurpose.name(),
                mapper,
                this.id(dpdClass, CodRecordStatus.X, docPurpose.name()),
                this.id(dpdClass, CodRecordStatus.C, docPurpose.name()),
                this.id(dpdClass, CodRecordStatus.A, docPurpose.name())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{dpd-class}/{flg-rewrite}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("dpd-class") String dpdClass,
            @PathVariable("flg-rewrite") FlagYesNo docPurpose
    ) {
        service.reopen(
                repository,
                DpdClassDTO.class,
                DpdClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dpdClass, docPurpose.name()), dpdClass + ";" + docPurpose,
                mapper,
                this.id(dpdClass, CodRecordStatus.R, docPurpose.name()),
                this.id(dpdClass, CodRecordStatus.A, docPurpose.name()),
                this.id(dpdClass, CodRecordStatus.C, docPurpose.name())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{dpd-class}/{flg-rewrite}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("dpd-class") String dpdClass,
            @PathVariable("flg-rewrite") FlagYesNo docPurpose
    ) {
        service.authorize(
                repository,
                DpdClassDTO.class,
                DpdClassEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), dpdClass, docPurpose.name()), dpdClass + ";" + docPurpose,
                mapper,
                this.id(dpdClass, CodRecordStatus.A, docPurpose.name()),
                this.id(dpdClass, CodRecordStatus.C, docPurpose.name())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<DpdClassEntity, DpdClassDTO> dtoMap = mapper.createTypeMap(DpdClassEntity.class, DpdClassDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDpdClass(), DpdClassDTO::setCodDpdClass);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), DpdClassDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getIsRewrite(), DpdClassDTO::setIsRewrite);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, DpdClassDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, DpdClassDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, DpdClassDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, DpdClassDTO::setLastCheckerDateTime);

        TypeMap<DpdClassDTO, DpdClassEntity> entityMap = mapper.createTypeMap(DpdClassDTO.class, DpdClassEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, DpdClassEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, DpdClassEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, DpdClassEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, DpdClassEntity::setLastCheckerDateTime);
    }

    private DpdClassEmbeddedKey id(String type, CodRecordStatus status, String purpose) {
        return DpdClassEmbeddedKey.builder().codDpdClass(type).codRecordStatus(status.name()).isRewrite(purpose).build();
    }

    private Specification<DpdClassEntity> getSpec(List<CodRecordStatus> statuses, String dpdClass, String isRewrite) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(dpdClass, "codDpdClass"))
                .and(repository.findRecordWithCode(isRewrite, "isRewrite"));
    }
}
