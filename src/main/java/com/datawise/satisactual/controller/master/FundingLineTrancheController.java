package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.FundingLineTrancheEmbeddedKey;
import com.datawise.satisactual.entities.master.FundingLineTrancheEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.FundingLineTrancheDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.FundingLineTrancheRepository;
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
@RequestMapping("/mst/funding-line-tranche")
public class FundingLineTrancheController {

    @Autowired
    private CommonMasterService<FundingLineTrancheEntity, FundingLineTrancheEmbeddedKey, FundingLineTrancheDTO> service;
    @Autowired
    private FundingLineTrancheRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<FundingLineTrancheDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, FundingLineTrancheDTO.class));
    }

    @GetMapping("/{funding-line-tranche}/{funding-line}/{funder}")
    public ResponseEntity<FundingLineTrancheDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line-tranche") String fundingLineTranche,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line") String fundingLine,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funder") String funder
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(CodRecordStatus.A, fundingLineTranche, fundingLine, funder), mapper, FundingLineTrancheDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody FundingLineTrancheDTO dto) {
        service.save(
                repository,
                dto,
                FundingLineTrancheEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFundingLineTranche(), dto.getCodFundingLine(), dto.getCodFunder()),
                dto.getCodFundingLineTranche() + ";" + dto.getCodFundingLine() + ";" + dto.getCodFunder(),
                mapper,
                this.id(CodRecordStatus.N, dto.getCodFundingLineTranche(), dto.getCodFundingLine(), dto.getCodFunder()),
                this.id(CodRecordStatus.A, dto.getCodFundingLineTranche(), dto.getCodFundingLine(), dto.getCodFunder())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody FundingLineTrancheDTO dto) {
        service.update(
                repository,
                dto,
                FundingLineTrancheEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFundingLineTranche(), dto.getCodFundingLine(), dto.getCodFunder()),
                dto.getCodFundingLineTranche() + ";" + dto.getCodFundingLine() + ";" + dto.getCodFunder(),
                mapper,
                this.id(CodRecordStatus.M, dto.getCodFundingLineTranche(), dto.getCodFundingLine(), dto.getCodFunder()),
                this.id(CodRecordStatus.A, dto.getCodFundingLineTranche(), dto.getCodFundingLine(), dto.getCodFunder())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{funding-line-tranche}/{funding-line}/{funder}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line-tranche") String fundingLineTranche,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line") String fundingLine,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funder") String funder
    ) {
        service.delete(
                repository,
                FundingLineTrancheDTO.class,
                FundingLineTrancheEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), fundingLineTranche, fundingLine, funder),
                fundingLineTranche + ";" + fundingLine + ";" + funder,
                mapper,
                this.id(CodRecordStatus.X, fundingLineTranche, fundingLine, funder),
                this.id(CodRecordStatus.C, fundingLineTranche, fundingLine, funder),
                this.id(CodRecordStatus.A, fundingLineTranche, fundingLine, funder)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{funding-line-tranche}/{funding-line}/{funder}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line-tranche") String fundingLineTranche,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line") String fundingLine,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funder") String funder
    ) {
        service.reopen(
                repository,
                FundingLineTrancheDTO.class,
                FundingLineTrancheEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), fundingLineTranche, fundingLine, funder),
                fundingLineTranche + ";" + fundingLine + ";" + funder,
                mapper,
                this.id(CodRecordStatus.R, fundingLineTranche, fundingLine, funder),
                this.id(CodRecordStatus.A, fundingLineTranche, fundingLine, funder),
                this.id(CodRecordStatus.C, fundingLineTranche, fundingLine, funder)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{funding-line-tranche}/{funding-line}/{funder}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line-tranche") String fundingLineTranche,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funding-line") String fundingLine,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("funder") String funder
    ) {
        service.authorize(
                repository,
                FundingLineTrancheDTO.class,
                FundingLineTrancheEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), fundingLineTranche, fundingLine, funder),
                fundingLineTranche + ";" + fundingLine + ";" + funder,
                mapper,
                this.id(CodRecordStatus.A, fundingLineTranche, fundingLine, funder),
                this.id(CodRecordStatus.C, fundingLineTranche, fundingLine, funder)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<FundingLineTrancheEntity, FundingLineTrancheDTO> dtoMap = mapper.createTypeMap(FundingLineTrancheEntity.class, FundingLineTrancheDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodFundingLineTranche(), FundingLineTrancheDTO::setCodFundingLineTranche);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), FundingLineTrancheDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodFundingLine(), FundingLineTrancheDTO::setCodFundingLine);
        dtoMap.addMapping(c -> c.getId().getCodFunder(), FundingLineTrancheDTO::setCodFunder);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, FundingLineTrancheDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, FundingLineTrancheDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, FundingLineTrancheDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, FundingLineTrancheDTO::setLastCheckerDateTime);

        TypeMap<FundingLineTrancheDTO, FundingLineTrancheEntity> entityMap = mapper.createTypeMap(FundingLineTrancheDTO.class, FundingLineTrancheEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, FundingLineTrancheEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, FundingLineTrancheEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, FundingLineTrancheEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, FundingLineTrancheEntity::setLastCheckerDateTime);
    }

    private FundingLineTrancheEmbeddedKey id(CodRecordStatus status, String fundingLineTranche, String fundingLine, String funder) {
        return FundingLineTrancheEmbeddedKey.builder().codFundingLineTranche(fundingLineTranche).codRecordStatus(status.name()).codFundingLine(fundingLine).codFunder(funder).build();
    }

    private Specification<FundingLineTrancheEntity> getSpec(List<CodRecordStatus> statuses, String fundingLineTranche, String fundingLine, String funder) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(fundingLineTranche, "codFundingLineTranche"))
                .and(repository.findRecordWithCode(fundingLine, "codFundingLine"))
                .and(repository.findRecordWithCode(funder, "codFunder"));
    }
}
