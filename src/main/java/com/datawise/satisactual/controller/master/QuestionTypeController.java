package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.QuestionTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.QuestionTypeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.QuestionTypeDTO;
import com.datawise.satisactual.repository.master.QuestionTypeRepository;
import com.datawise.satisactual.service.master.CommonMasterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@RequestMapping("/mst/question-type")
@CrossOrigin
public class QuestionTypeController {

    @Autowired
    private CommonMasterService<QuestionTypeEntity, QuestionTypeEmbeddedKey, QuestionTypeDTO> service;
    @Autowired
    private QuestionTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<QuestionTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, QuestionTypeDTO.class));
    }

    @GetMapping("/{question-type}/{base-question-type}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<QuestionTypeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("question-type") String questionType,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("base-question-type") String baseQuestionType
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(questionType, CodRecordStatus.A, baseQuestionType), mapper, QuestionTypeDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody QuestionTypeDTO dto) {
        service.save(
                repository,
                dto,
                QuestionTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodQuestionType(), dto.getBaseQuestionType()),
                dto.getCodQuestionType() + ";" + dto.getBaseQuestionType(),
                mapper,
                this.id(dto.getCodQuestionType(), CodRecordStatus.N, dto.getBaseQuestionType()),
                this.id(dto.getCodQuestionType(), CodRecordStatus.A, dto.getBaseQuestionType())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody QuestionTypeDTO dto) {
        service.update(
                repository,
                dto,
                QuestionTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodQuestionType(), dto.getBaseQuestionType()),
                dto.getCodQuestionType() + ";" + dto.getBaseQuestionType(),
                mapper,
                this.id(dto.getCodQuestionType(), CodRecordStatus.M, dto.getBaseQuestionType()),
                this.id(dto.getCodQuestionType(), CodRecordStatus.A, dto.getBaseQuestionType())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{question-type}/{base-question-type}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("question-type") String questionType,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("base-question-type") String baseQuestionType
    ) {
        service.delete(
                repository,
                QuestionTypeDTO.class,
                QuestionTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), questionType, baseQuestionType),
                questionType + ";" + baseQuestionType,
                mapper,
                this.id(questionType, CodRecordStatus.X, baseQuestionType),
                this.id(questionType, CodRecordStatus.C, baseQuestionType),
                this.id(questionType, CodRecordStatus.A, baseQuestionType)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{question-type}/{base-question-type}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("question-type") String questionType,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("base-question-type") String baseQuestionType
    ) {
        service.reopen(
                repository,
                QuestionTypeDTO.class,
                QuestionTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), questionType, baseQuestionType),
                questionType + ";" + baseQuestionType,
                mapper,
                this.id(questionType, CodRecordStatus.R, baseQuestionType),
                this.id(questionType, CodRecordStatus.A, baseQuestionType),
                this.id(questionType, CodRecordStatus.C, baseQuestionType)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{question-type}/{base-question-type}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("question-type") String questionType,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("base-question-type") String baseQuestionType
    ) {
        service.authorize(
                repository,
                QuestionTypeDTO.class,
                QuestionTypeEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), questionType, baseQuestionType),
                questionType + ";" + baseQuestionType,
                mapper,
                this.id(questionType, CodRecordStatus.A, baseQuestionType),
                this.id(questionType, CodRecordStatus.C, baseQuestionType)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<QuestionTypeEntity, QuestionTypeDTO> dtoMap = mapper.createTypeMap(QuestionTypeEntity.class, QuestionTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodQuestionType(), QuestionTypeDTO::setCodQuestionType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), QuestionTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getBaseQuestionType(), QuestionTypeDTO::setBaseQuestionType);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, QuestionTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, QuestionTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, QuestionTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, QuestionTypeDTO::setLastCheckerDateTime);

        TypeMap<QuestionTypeDTO, QuestionTypeEntity> entityMap = mapper.createTypeMap(QuestionTypeDTO.class, QuestionTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, QuestionTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, QuestionTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, QuestionTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, QuestionTypeEntity::setLastCheckerDateTime);
    }

    private QuestionTypeEmbeddedKey id(String type, CodRecordStatus status, String baseQuestionType) {
        return QuestionTypeEmbeddedKey.builder().codQuestionType(type).codRecordStatus(status.name()).baseQuestionType(baseQuestionType).build();
    }

    private Specification<QuestionTypeEntity> getSpec(List<CodRecordStatus> statuses, String code, String baseQuestionType) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codQuestionType"))
                .and(repository.findRecordWithCode(baseQuestionType, "baseQuestionType"));
    }
}
