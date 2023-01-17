package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CustomModelAttribEmbeddedKey;
import com.datawise.satisactual.entities.master.CustomModelAttribEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomModelAttribDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CustomModelAttribRepository;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/mst/custom-model-attrib")
public class CustomModelAttribController {

    @Autowired
    private CommonMasterService<CustomModelAttribEntity, CustomModelAttribEmbeddedKey, CustomModelAttribDTO> service;
    @Autowired
    private CustomModelAttribRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CustomModelAttribDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CustomModelAttribDTO.class));
    }

    @GetMapping("/{scoring-model}/{cod-attribute}/{attrib-value-min}/{attrib-value-max}")
    public ResponseEntity<CustomModelAttribDTO> get(
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("scoring-model") String scoringModel,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("cod-attribute") String codAttribute,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("attrib-value-min") String attribValueMin,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("attrib-value-max") String attribValueMax
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(CodRecordStatus.A, scoringModel, codAttribute, attribValueMin, attribValueMax), mapper, CustomModelAttribDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CustomModelAttribDTO dto) {
        service.save(
                repository,
                dto,
                CustomModelAttribEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodScoringModel(), dto.getCodAttribute(), dto.getAttribValueMin(), dto.getAttribValueMax()),
                dto.getCodScoringModel() + ";" + dto.getCodAttribute() + "," + dto.getAttribValueMin() + "," + dto.getAttribValueMax(),
                mapper,
                this.id(CodRecordStatus.N, dto.getCodScoringModel(), dto.getCodAttribute(), dto.getAttribValueMin(), dto.getAttribValueMax()),
                this.id(CodRecordStatus.A, dto.getCodScoringModel(), dto.getCodAttribute(), dto.getAttribValueMin(), dto.getAttribValueMax())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CustomModelAttribDTO dto) {
        service.update(
                repository,
                dto,
                CustomModelAttribEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodScoringModel(), dto.getCodAttribute(), dto.getAttribValueMin(), dto.getAttribValueMax()),
                dto.getCodScoringModel() + ";" + dto.getCodAttribute() + ";" + dto.getAttribValueMin() + ";" + dto.getAttribValueMax(),
                mapper,
                this.id(CodRecordStatus.M, dto.getCodScoringModel(), dto.getCodAttribute(), dto.getAttribValueMin(), dto.getAttribValueMax()),
                this.id(CodRecordStatus.A, dto.getCodScoringModel(), dto.getCodAttribute(), dto.getAttribValueMin(), dto.getAttribValueMax())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{scoring-model}/{cod-attribute}/{attrib-value-min}/{attrib-value-max}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("scoring-model") String scoringModel,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("cod-attribute") String codAttribute,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("attrib-value-min") String attribValueMin,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("attrib-value-max") String attribValueMax
    ) {
        service.delete(
                repository,
                CustomModelAttribDTO.class,
                CustomModelAttribEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), scoringModel, codAttribute, attribValueMin, attribValueMax),
                scoringModel + "," + codAttribute + "," + attribValueMin + "," + attribValueMax,
                mapper,
                this.id(CodRecordStatus.X, scoringModel, codAttribute, attribValueMin, attribValueMax),
                this.id(CodRecordStatus.C, scoringModel, codAttribute, attribValueMin, attribValueMax),
                this.id(CodRecordStatus.A, scoringModel, codAttribute, attribValueMin, attribValueMax)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{scoring-model}/{cod-attribute}/{attrib-value-min}/{attrib-value-max}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("scoring-model") String scoringModel,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("cod-attribute") String codAttribute,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("attrib-value-min") String attribValueMin,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("attrib-value-max") String attribValueMax
    ) {
        service.reopen(
                repository,
                CustomModelAttribDTO.class,
                CustomModelAttribEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), scoringModel, codAttribute, attribValueMin, attribValueMax),
                scoringModel + ";" + codAttribute + ";" + attribValueMin + ";" + attribValueMin,
                mapper,
                this.id(CodRecordStatus.R, scoringModel, codAttribute, attribValueMin, attribValueMax),
                this.id(CodRecordStatus.A, scoringModel, codAttribute, attribValueMin, attribValueMax),
                this.id(CodRecordStatus.C, scoringModel, codAttribute, attribValueMin, attribValueMax)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{scoring-model}/{cod-attribute}/{attrib-value-min}/{attrib-value-max}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("scoring-model") String scoringModel,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("cod-attribute") String codAttribute,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("attrib-value-min") String attribValueMin,
            @Valid @NotNull @Size(min = 1, max = 24) @PathVariable("attrib-value-max") String attribValueMax
    ) {
        service.authorize(
                repository,
                CustomModelAttribDTO.class,
                CustomModelAttribEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), scoringModel, codAttribute, attribValueMin, attribValueMax),
                scoringModel + "," + codAttribute + "," + attribValueMin + "," + attribValueMax,
                mapper,
                this.id(CodRecordStatus.A, scoringModel, codAttribute, attribValueMin, attribValueMax),
                this.id(CodRecordStatus.C, scoringModel, codAttribute, attribValueMin, attribValueMax)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<CustomModelAttribEntity, CustomModelAttribDTO> dtoMap = mapper.createTypeMap(CustomModelAttribEntity.class, CustomModelAttribDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodScoringModel(), CustomModelAttribDTO::setCodScoringModel);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CustomModelAttribDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodAttribute(), CustomModelAttribDTO::setCodAttribute);
        dtoMap.addMapping(c -> c.getId().getAttribValueMin(), CustomModelAttribDTO::setAttribValueMin);
        dtoMap.addMapping(c -> c.getId().getAttribValueMax(), CustomModelAttribDTO::setAttribValueMax);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CustomModelAttribDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CustomModelAttribDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CustomModelAttribDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CustomModelAttribDTO::setLastCheckerDateTime);

        TypeMap<CustomModelAttribDTO, CustomModelAttribEntity> entityMap = mapper.createTypeMap(CustomModelAttribDTO.class, CustomModelAttribEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CustomModelAttribEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CustomModelAttribEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CustomModelAttribEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CustomModelAttribEntity::setLastCheckerDateTime);
    }

    private CustomModelAttribEmbeddedKey id(CodRecordStatus status, String scoringModel, String codAttribute, String attribValueMin, String attribValueMax) {
        return CustomModelAttribEmbeddedKey.builder().codRecordStatus(status.name()).codScoringModel(scoringModel).codAttribute(codAttribute).attribValueMin(attribValueMin).attribValueMax(attribValueMax).build();
    }

    private Specification<CustomModelAttribEntity> getSpec(List<CodRecordStatus> statuses, String scoringModel, String codAttribute, String attribValueMin, String attribValueMax) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(scoringModel, "codScoringModel"))
                .and(repository.findRecordWithCode(codAttribute, "codAttribute"))
                .and(repository.findRecordWithCode(attribValueMin, "attribValueMin"))
                .and(repository.findRecordWithCode(attribValueMax, "attribValueMax"));
    }
}
