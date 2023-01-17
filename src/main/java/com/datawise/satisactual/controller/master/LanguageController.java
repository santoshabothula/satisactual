package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.LanguageEmbeddedKey;
import com.datawise.satisactual.entities.master.LanguageEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.LanguageDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.LanguageRepository;
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
@RequestMapping("/mst/language")
public class LanguageController {

    @Autowired
    private CommonMasterService<LanguageEntity, LanguageEmbeddedKey, LanguageDTO> service;
    @Autowired
    private LanguageRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, LanguageDTO.class));
    }

    @GetMapping("/{language}/{iso3charCode}/{iso2charCode}")
    public ResponseEntity<LanguageDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("language") String language,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("iso3charCode") String iso3charCode,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("iso2charCode") String iso2charCode
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(CodRecordStatus.A, language, iso3charCode, iso2charCode), mapper, LanguageDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody LanguageDTO dto) {
        service.save(
                repository,
                dto,
                LanguageEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodLanguage(), dto.getIso3charCode(), dto.getIso2charCode()),
                dto.getCodLanguage() + ";" + dto.getIso3charCode() + ";" + dto.getIso2charCode(),
                mapper,
                this.id(CodRecordStatus.N, dto.getCodLanguage(), dto.getIso3charCode(), dto.getIso2charCode()),
                this.id(CodRecordStatus.A, dto.getCodLanguage(), dto.getIso3charCode(), dto.getIso2charCode())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody LanguageDTO dto) {
        service.update(
                repository,
                dto,
                LanguageEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodLanguage(), dto.getIso3charCode(), dto.getIso2charCode()),
                dto.getCodLanguage() + ";" + dto.getIso3charCode() + ";" + dto.getIso2charCode(),
                mapper,
                this.id(CodRecordStatus.M, dto.getCodLanguage(), dto.getIso3charCode(), dto.getIso2charCode()),
                this.id(CodRecordStatus.A, dto.getCodLanguage(), dto.getIso3charCode(), dto.getIso2charCode())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{language}/{iso3charCode}/{iso2charCode}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("language") String language,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("iso3charCode") String iso3charCode,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("iso2charCode") String iso2charCode
    ) {
        service.delete(
                repository,
                LanguageDTO.class,
                LanguageEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), language, iso3charCode, iso2charCode),
                language + ";" + iso3charCode + ";" + iso2charCode,
                mapper,
                this.id(CodRecordStatus.X, language, iso3charCode, iso2charCode),
                this.id(CodRecordStatus.C, language, iso3charCode, iso2charCode),
                this.id(CodRecordStatus.A, language, iso3charCode, iso2charCode)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{language}/{iso3charCode}/{iso2charCode}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("language") String language,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("iso3charCode") String iso3charCode,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("iso2charCode") String iso2charCode
    ) {
        service.reopen(
                repository,
                LanguageDTO.class,
                LanguageEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), language, iso3charCode, iso2charCode),
                language + ";" + iso3charCode + ";" + iso2charCode,
                mapper,
                this.id(CodRecordStatus.R, language, iso3charCode, iso2charCode),
                this.id(CodRecordStatus.A, language, iso3charCode, iso2charCode),
                this.id(CodRecordStatus.C, language, iso3charCode, iso2charCode)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{language}/{iso3charCode}/{iso2charCode}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("language") String language,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("iso3charCode") String iso3charCode,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("iso2charCode") String iso2charCode
    ) {
        service.authorize(
                repository,
                LanguageDTO.class,
                LanguageEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), language, iso3charCode, iso2charCode),
                language + ";" + iso3charCode + ";" + iso2charCode,
                mapper,
                this.id(CodRecordStatus.A, language, iso3charCode, iso2charCode),
                this.id(CodRecordStatus.C, language, iso3charCode, iso2charCode)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<LanguageEntity, LanguageDTO> dtoMap = mapper.createTypeMap(LanguageEntity.class, LanguageDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodLanguage(), LanguageDTO::setCodLanguage);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), LanguageDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getIso3charCode(), LanguageDTO::setIso3charCode);
        dtoMap.addMapping(c -> c.getId().getIso2charCode(), LanguageDTO::setIso2charCode);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, LanguageDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, LanguageDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, LanguageDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, LanguageDTO::setLastCheckerDateTime);

        TypeMap<LanguageDTO, LanguageEntity> entityMap = mapper.createTypeMap(LanguageDTO.class, LanguageEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, LanguageEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, LanguageEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, LanguageEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, LanguageEntity::setLastCheckerDateTime);
    }

    private LanguageEmbeddedKey id(CodRecordStatus status, String lang, String iso3charCode, String iso2charCode) {
        return LanguageEmbeddedKey.builder().codRecordStatus(status.name()).codLanguage(lang).iso3charCode(iso3charCode).iso2charCode(iso2charCode).build();
    }

    private Specification<LanguageEntity> getSpec(List<CodRecordStatus> statuses, String lang, String iso3charCode, String iso2charCode) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(lang, "codLanguage"))
                .and(repository.findRecordWithCode(iso3charCode, "iso3charCode"))
                .and(repository.findRecordWithCode(iso2charCode, "iso2charCode"));
    }
}
