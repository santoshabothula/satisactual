package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.FundingLineEmbeddedKey;
import com.datawise.satisactual.entities.master.FundingLineEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.FundingLineDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.FundingLineRepository;
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
@RequestMapping("/mst/funding-line")
public class FundingLineController {

    @Autowired
    private CommonMasterService<FundingLineEntity, FundingLineEmbeddedKey, FundingLineDTO> service;
    @Autowired
    private FundingLineRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<FundingLineDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, FundingLineDTO.class));
    }

    @GetMapping("/{funding-line}/{funder}")
    public ResponseEntity<FundingLineDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line") String fundingLine,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funder") String funder
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(fundingLine, CodRecordStatus.A, funder), mapper, FundingLineDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody FundingLineDTO dto) {
        service.save(
                repository,
                dto,
                FundingLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFundingLine(), dto.getCodFunder()),
                dto.getCodFundingLine() + ";" + dto.getCodFunder(),
                mapper,
                this.id(dto.getCodFundingLine(), CodRecordStatus.N, dto.getCodFunder()),
                this.id(dto.getCodFundingLine(), CodRecordStatus.A, dto.getCodFunder())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody FundingLineDTO dto) {
        service.update(
                repository,
                dto,
                FundingLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFundingLine(), dto.getCodFunder()),
                dto.getCodFundingLine() + ";" + dto.getCodFunder(),
                mapper,
                this.id(dto.getCodFundingLine(), CodRecordStatus.M, dto.getCodFunder()),
                this.id(dto.getCodFundingLine(), CodRecordStatus.A, dto.getCodFunder())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{funding-line}/{funder}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line") String fundingLine,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funder") String funder
    ) {
        service.delete(
                repository,
                FundingLineDTO.class,
                FundingLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), fundingLine, funder),
                fundingLine + ";" + funder,
                mapper,
                this.id(fundingLine, CodRecordStatus.X, funder),
                this.id(fundingLine, CodRecordStatus.C, funder),
                this.id(fundingLine, CodRecordStatus.A, funder)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{funding-line}/{funder}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line") String fundingLine,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funder") String funder
    ) {
        service.reopen(
                repository,
                FundingLineDTO.class,
                FundingLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), fundingLine, funder),
                fundingLine + ";" + funder,
                mapper,
                this.id(fundingLine, CodRecordStatus.R, funder),
                this.id(fundingLine, CodRecordStatus.A, funder),
                this.id(fundingLine, CodRecordStatus.C, funder)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{funding-line}/{funder}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line") String fundingLine,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funder") String funder
    ) {
        service.authorize(
                repository,
                FundingLineDTO.class,
                FundingLineEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), fundingLine, funder),
                fundingLine + ";" + funder,
                mapper,
                this.id(fundingLine, CodRecordStatus.A, funder),
                this.id(fundingLine, CodRecordStatus.C, funder)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<FundingLineEntity, FundingLineDTO> dtoMap = mapper.createTypeMap(FundingLineEntity.class, FundingLineDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodFundingLine(), FundingLineDTO::setCodFundingLine);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), FundingLineDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodFunder(), FundingLineDTO::setCodFunder);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, FundingLineDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, FundingLineDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, FundingLineDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, FundingLineDTO::setLastCheckerDateTime);

        TypeMap<FundingLineDTO, FundingLineEntity> entityMap = mapper.createTypeMap(FundingLineDTO.class, FundingLineEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, FundingLineEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, FundingLineEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, FundingLineEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, FundingLineEntity::setLastCheckerDateTime);
    }

    private FundingLineEmbeddedKey id(String founderLine, CodRecordStatus status, String founder) {
        return FundingLineEmbeddedKey.builder().codFundingLine(founderLine).codRecordStatus(status.name()).codFunder(founder).build();
    }

    private Specification<FundingLineEntity> getSpec(List<CodRecordStatus> statuses, String founderLine, String founder) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(founderLine, "codFundingLine"))
                .and(repository.findRecordWithCode(founder, "codFunder"));
    }
}
