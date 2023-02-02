package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.DisclosureLangEmbeddedKey;
import com.datawise.satisactual.entities.master.DisclosureLangEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.DisclosureLangDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.DisclosureLangRepository;
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
@RequestMapping("/mst/disclosure-lang")
@CrossOrigin
public class DisclosureLangController {

    @Autowired
    private CommonMasterService<DisclosureLangEntity, DisclosureLangEmbeddedKey, DisclosureLangDTO> service;
    @Autowired
    private DisclosureLangRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<DisclosureLangDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, DisclosureLangDTO.class));
    }

    @GetMapping("/{disclosure}/{language}")
    public ResponseEntity<DisclosureLangDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("disclosure") String disclosure,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("language") String language
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(disclosure, CodRecordStatus.A, language), mapper, DisclosureLangDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody DisclosureLangDTO dto) {
        service.save(
                repository,
                dto,
                DisclosureLangEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDisclosure(), dto.getCodLanguage()),
                dto.getCodDisclosure() + ";" + dto.getCodLanguage(),
                mapper,
                this.id(dto.getCodDisclosure(), CodRecordStatus.N, dto.getCodLanguage()),
                this.id(dto.getCodDisclosure(), CodRecordStatus.A, dto.getCodLanguage())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody DisclosureLangDTO dto) {
        service.update(
                repository,
                dto,
                DisclosureLangEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDisclosure(), dto.getCodLanguage()),
                dto.getCodDisclosure() + ";" + dto.getCodLanguage(),
                mapper,
                this.id(dto.getCodDisclosure(), CodRecordStatus.M, dto.getCodLanguage()),
                this.id(dto.getCodDisclosure(), CodRecordStatus.A, dto.getCodLanguage())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{disclosure}/{language}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("disclosure") String disclosure,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("language") String language
    ) {
        service.delete(
                repository,
                DisclosureLangDTO.class,
                DisclosureLangEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), disclosure, language),
                disclosure + ";" + language,
                mapper,
                this.id(disclosure, CodRecordStatus.X, language),
                this.id(disclosure, CodRecordStatus.C, language),
                this.id(disclosure, CodRecordStatus.A, language)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{disclosure}/{language}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("disclosure") String disclosure,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("language") String language
    ) {
        service.reopen(
                repository,
                DisclosureLangDTO.class,
                DisclosureLangEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), disclosure, language),
                disclosure + ";" + language,
                mapper,
                this.id(disclosure, CodRecordStatus.R, language),
                this.id(disclosure, CodRecordStatus.A, language),
                this.id(disclosure, CodRecordStatus.C, language)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{disclosure}/{language}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("disclosure") String disclosure,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("language") String language
    ) {
        service.authorize(
                repository,
                DisclosureLangDTO.class,
                DisclosureLangEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), disclosure, language),
                disclosure + ";" + language,
                mapper,
                this.id(disclosure, CodRecordStatus.A, language),
                this.id(disclosure, CodRecordStatus.C, language)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<DisclosureLangEntity, DisclosureLangDTO> dtoMap = mapper.createTypeMap(DisclosureLangEntity.class, DisclosureLangDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDisclosure(), DisclosureLangDTO::setCodDisclosure);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), DisclosureLangDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodLanguage(), DisclosureLangDTO::setCodLanguage);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, DisclosureLangDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, DisclosureLangDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, DisclosureLangDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, DisclosureLangDTO::setLastCheckerDateTime);

        TypeMap<DisclosureLangDTO, DisclosureLangEntity> entityMap = mapper.createTypeMap(DisclosureLangDTO.class, DisclosureLangEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, DisclosureLangEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, DisclosureLangEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, DisclosureLangEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, DisclosureLangEntity::setLastCheckerDateTime);
    }

    private DisclosureLangEmbeddedKey id(String code, CodRecordStatus status, String codLanguage) {
        return DisclosureLangEmbeddedKey.builder().codDisclosure(code).codRecordStatus(status.name()).codLanguage(codLanguage).build();
    }

    private Specification<DisclosureLangEntity> getSpec(List<CodRecordStatus> statuses, String code, String codLanguage) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codDisclosure"))
                .and(repository.findRecordWithCode(codLanguage, "codLanguage"));
    }
}
