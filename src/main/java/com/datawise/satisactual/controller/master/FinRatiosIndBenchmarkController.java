package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.FinRatiosIndBenchmarkEmbeddedKey;
import com.datawise.satisactual.entities.master.FinRatiosIndBenchmarkEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.FinRatiosIndBenchmarkDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.FinRatiosIndBenchmarkRepository;
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
@RequestMapping("/mst/fin-ratios-ind-benchmark")
public class FinRatiosIndBenchmarkController {

    @Autowired
    private CommonMasterService<FinRatiosIndBenchmarkEntity, FinRatiosIndBenchmarkEmbeddedKey, FinRatiosIndBenchmarkDTO> service;
    @Autowired
    private FinRatiosIndBenchmarkRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<FinRatiosIndBenchmarkDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, FinRatiosIndBenchmarkDTO.class));
    }

    @GetMapping("/{ratio}/{industry-grp}")
    public ResponseEntity<FinRatiosIndBenchmarkDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("ratio") String ratio,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("industry-grp") String industryGrp
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(ratio, CodRecordStatus.A, industryGrp), mapper, FinRatiosIndBenchmarkDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody FinRatiosIndBenchmarkDTO dto) {
        service.save(
                repository,
                dto,
                FinRatiosIndBenchmarkEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodRatio(), dto.getCodIndustryGrp()),
                dto.getCodRatio() + ";" + dto.getCodIndustryGrp(),
                mapper,
                this.id(dto.getCodRatio(), CodRecordStatus.N, dto.getCodIndustryGrp()),
                this.id(dto.getCodRatio(), CodRecordStatus.A, dto.getCodIndustryGrp())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody FinRatiosIndBenchmarkDTO dto) {
        service.update(
                repository,
                dto,
                FinRatiosIndBenchmarkEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodRatio(), dto.getCodIndustryGrp()),
                dto.getCodRatio() + ";" + dto.getCodIndustryGrp(),
                mapper,
                this.id(dto.getCodRatio(), CodRecordStatus.M, dto.getCodIndustryGrp()),
                this.id(dto.getCodRatio(), CodRecordStatus.A, dto.getCodIndustryGrp())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{ratio}/{industry-grp}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("ratio") String ratio,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("industry-grp") String industryGrp
    ) {
        service.delete(
                repository,
                FinRatiosIndBenchmarkDTO.class,
                FinRatiosIndBenchmarkEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), ratio, industryGrp),
                ratio + ";" + industryGrp,
                mapper,
                this.id(ratio, CodRecordStatus.X, industryGrp),
                this.id(ratio, CodRecordStatus.C, industryGrp),
                this.id(ratio, CodRecordStatus.A, industryGrp)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{ratio}/{industry-grp}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("ratio") String ratio,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("industry-grp") String industryGrp
    ) {
        service.reopen(
                repository,
                FinRatiosIndBenchmarkDTO.class,
                FinRatiosIndBenchmarkEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), ratio, industryGrp),
                ratio + ";" + industryGrp,
                mapper,
                this.id(ratio, CodRecordStatus.R, industryGrp),
                this.id(ratio, CodRecordStatus.A, industryGrp),
                this.id(ratio, CodRecordStatus.C, industryGrp)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{ratio}/{industry-grp}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("ratio") String ratio,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("industry-grp") String industryGrp
    ) {
        service.authorize(
                repository,
                FinRatiosIndBenchmarkDTO.class,
                FinRatiosIndBenchmarkEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), ratio, industryGrp),
                ratio + ";" + industryGrp,
                mapper,
                this.id(ratio, CodRecordStatus.A, industryGrp),
                this.id(ratio, CodRecordStatus.C, industryGrp)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<FinRatiosIndBenchmarkEntity, FinRatiosIndBenchmarkDTO> dtoMap = mapper.createTypeMap(FinRatiosIndBenchmarkEntity.class, FinRatiosIndBenchmarkDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodRatio(), FinRatiosIndBenchmarkDTO::setCodRatio);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), FinRatiosIndBenchmarkDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodIndustryGrp(), FinRatiosIndBenchmarkDTO::setCodIndustryGrp);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, FinRatiosIndBenchmarkDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, FinRatiosIndBenchmarkDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, FinRatiosIndBenchmarkDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, FinRatiosIndBenchmarkDTO::setLastCheckerDateTime);

        TypeMap<FinRatiosIndBenchmarkDTO, FinRatiosIndBenchmarkEntity> entityMap = mapper.createTypeMap(FinRatiosIndBenchmarkDTO.class, FinRatiosIndBenchmarkEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, FinRatiosIndBenchmarkEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, FinRatiosIndBenchmarkEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, FinRatiosIndBenchmarkEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, FinRatiosIndBenchmarkEntity::setLastCheckerDateTime);
    }

    private FinRatiosIndBenchmarkEmbeddedKey id(String code, CodRecordStatus status, String industryGrp) {
        return FinRatiosIndBenchmarkEmbeddedKey.builder().codRatio(code).codRecordStatus(status.name()).codIndustryGrp(industryGrp).build();
    }

    private Specification<FinRatiosIndBenchmarkEntity> getSpec(List<CodRecordStatus> statuses, String code, String industryGrp) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codRatio"))
                .and(repository.findRecordWithCode(industryGrp, "codIndustryGrp"));
    }
}
