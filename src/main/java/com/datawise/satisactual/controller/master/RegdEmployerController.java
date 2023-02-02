package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.RegdEmployerEmbeddedKey;
import com.datawise.satisactual.entities.master.RegdEmployerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.RegdEmployerDTO;
import com.datawise.satisactual.repository.master.RegdEmployerRepository;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/mst/regd-employer")
@CrossOrigin
public class RegdEmployerController {

    @Autowired
    private CommonMasterService<RegdEmployerEntity, RegdEmployerEmbeddedKey, RegdEmployerDTO> service;
    @Autowired
    private RegdEmployerRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<RegdEmployerDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, RegdEmployerDTO.class));
    }

    @GetMapping("/{employer-shortname}/{id-third-party-employer}")
    public ResponseEntity<RegdEmployerDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("employer-shortname") String employerShortname,
            @Valid @NotNull @PathVariable("id-third-party-employer") Long idThirdPartyEmployer
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(employerShortname, CodRecordStatus.A, idThirdPartyEmployer), mapper, RegdEmployerDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody RegdEmployerDTO dto) {
        service.save(
                repository,
                dto,
                RegdEmployerEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getEmployerShortname(), dto.getIdThirdPartyEmployer()),
                dto.getEmployerShortname() + ";" + dto.getIdThirdPartyEmployer(),
                mapper,
                this.id(dto.getEmployerShortname(), CodRecordStatus.N, dto.getIdThirdPartyEmployer()),
                this.id(dto.getEmployerShortname(), CodRecordStatus.A, dto.getIdThirdPartyEmployer())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody RegdEmployerDTO dto) {
        service.update(
                repository,
                dto,
                RegdEmployerEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getEmployerShortname(), dto.getIdThirdPartyEmployer()),
                dto.getEmployerShortname() + ";" + dto.getIdThirdPartyEmployer(),
                mapper,
                this.id(dto.getEmployerShortname(), CodRecordStatus.M, dto.getIdThirdPartyEmployer()),
                this.id(dto.getEmployerShortname(), CodRecordStatus.A, dto.getIdThirdPartyEmployer())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{employer-shortname}/{id-third-party-employer}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("employer-shortname") String employerShortname,
            @Valid @NotNull @PathVariable("id-third-party-employer") Long idThirdPartyEmployer
    ) {
        service.delete(
                repository,
                RegdEmployerDTO.class,
                RegdEmployerEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), employerShortname, idThirdPartyEmployer),
                employerShortname + ";" + idThirdPartyEmployer,
                mapper,
                this.id(employerShortname, CodRecordStatus.X, idThirdPartyEmployer),
                this.id(employerShortname, CodRecordStatus.C, idThirdPartyEmployer),
                this.id(employerShortname, CodRecordStatus.A, idThirdPartyEmployer)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{employer-shortname}/{id-third-party-employer}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("employer-shortname") String employerShortname,
            @Valid @NotNull @PathVariable("id-third-party-employer") Long idThirdPartyEmployer
    ) {
        service.reopen(
                repository,
                RegdEmployerDTO.class,
                RegdEmployerEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), employerShortname, idThirdPartyEmployer),
                employerShortname + ";" + idThirdPartyEmployer,
                mapper,
                this.id(employerShortname, CodRecordStatus.R, idThirdPartyEmployer),
                this.id(employerShortname, CodRecordStatus.A, idThirdPartyEmployer),
                this.id(employerShortname, CodRecordStatus.C, idThirdPartyEmployer)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{employer-shortname}/{id-third-party-employer}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("employer-shortname") String employerShortname,
            @Valid @NotNull @PathVariable("id-third-party-employer") Long idThirdPartyEmployer
    ) {
        service.authorize(
                repository,
                RegdEmployerDTO.class,
                RegdEmployerEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), employerShortname, idThirdPartyEmployer),
                employerShortname + ";" + idThirdPartyEmployer,
                mapper,
                this.id(employerShortname, CodRecordStatus.A, idThirdPartyEmployer),
                this.id(employerShortname, CodRecordStatus.C, idThirdPartyEmployer)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<RegdEmployerEntity, RegdEmployerDTO> dtoMap = mapper.createTypeMap(RegdEmployerEntity.class, RegdEmployerDTO.class);
        dtoMap.addMapping(c -> c.getId().getEmployerShortname(), RegdEmployerDTO::setEmployerShortname);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), RegdEmployerDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getIdThirdPartyEmployer(), RegdEmployerDTO::setIdThirdPartyEmployer);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, RegdEmployerDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, RegdEmployerDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, RegdEmployerDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, RegdEmployerDTO::setLastCheckerDateTime);

        TypeMap<RegdEmployerDTO, RegdEmployerEntity> entityMap = mapper.createTypeMap(RegdEmployerDTO.class, RegdEmployerEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, RegdEmployerEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, RegdEmployerEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, RegdEmployerEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, RegdEmployerEntity::setLastCheckerDateTime);
    }

    private RegdEmployerEmbeddedKey id(String employerShortname, CodRecordStatus status, Long idThirdPartyEmployer) {
        return RegdEmployerEmbeddedKey.builder().employerShortname(employerShortname).codRecordStatus(status.name()).idThirdPartyEmployer(idThirdPartyEmployer).build();
    }

    private Specification<RegdEmployerEntity> getSpec(List<CodRecordStatus> statuses, String employerShortname, Long idThirdPartyEmployer) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(employerShortname, "employerShortname"))
                .and(repository.findRecordWithCode(idThirdPartyEmployer, "idThirdPartyEmployer"));
    }
}
