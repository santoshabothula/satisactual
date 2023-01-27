package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.UserDesignationEmbeddedKey;
import com.datawise.satisactual.entities.master.UserDesignationEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.UserDesignationDTO;
import com.datawise.satisactual.repository.master.UserDesignationRepository;
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
@RequestMapping("/mst/user-designation")
public class UserDesignationController {

    @Autowired
    private CommonMasterService<UserDesignationEntity, UserDesignationEmbeddedKey, UserDesignationDTO> service;
    @Autowired
    private UserDesignationRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<UserDesignationDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, UserDesignationDTO.class));
    }

    @GetMapping("/{designation}/{id-third-party}")
    public ResponseEntity<UserDesignationDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("designation") String designation,
            @Valid @NotNull @PathVariable("id-third-party") Long idThirdParty
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(designation, CodRecordStatus.A, idThirdParty), mapper, UserDesignationDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody UserDesignationDTO dto) {
        service.save(
                repository,
                dto,
                UserDesignationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDesignation(), dto.getIdThirdParty()),
                dto.getCodDesignation() + ";" + dto.getIdThirdParty(),
                mapper,
                this.id(dto.getCodDesignation(), CodRecordStatus.N, dto.getIdThirdParty()),
                this.id(dto.getCodDesignation(), CodRecordStatus.A, dto.getIdThirdParty())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody UserDesignationDTO dto) {
        service.update(
                repository,
                dto,
                UserDesignationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDesignation(), dto.getIdThirdParty()),
                dto.getCodDesignation() + ";" + dto.getIdThirdParty(),
                mapper,
                this.id(dto.getCodDesignation(), CodRecordStatus.M, dto.getIdThirdParty()),
                this.id(dto.getCodDesignation(), CodRecordStatus.A, dto.getIdThirdParty())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{designation}/{id-third-party}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("designation") String designation,
            @Valid @NotNull @PathVariable("id-third-party") Long idThirdParty
    ) {
        service.delete(
                repository,
                UserDesignationDTO.class,
                UserDesignationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), designation, idThirdParty),
                designation + ";" + idThirdParty,
                mapper,
                this.id(designation, CodRecordStatus.X, idThirdParty),
                this.id(designation, CodRecordStatus.C, idThirdParty),
                this.id(designation, CodRecordStatus.A, idThirdParty)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{designation}/{id-third-party}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("designation") String designation,
            @Valid @NotNull @PathVariable("id-third-party") Long idThirdParty
    ) {
        service.reopen(
                repository,
                UserDesignationDTO.class,
                UserDesignationEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), designation, idThirdParty),
                designation + ";" + idThirdParty,
                mapper,
                this.id(designation, CodRecordStatus.R, idThirdParty),
                this.id(designation, CodRecordStatus.A, idThirdParty),
                this.id(designation, CodRecordStatus.C, idThirdParty)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{designation}/{id-third-party}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("designation") String designation,
            @Valid @NotNull @PathVariable("id-third-party") Long idThirdParty
    ) {
        service.authorize(
                repository,
                UserDesignationDTO.class,
                UserDesignationEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), designation, idThirdParty),
                designation + ";" + idThirdParty,
                mapper,
                this.id(designation, CodRecordStatus.A, idThirdParty),
                this.id(designation, CodRecordStatus.C, idThirdParty)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<UserDesignationEntity, UserDesignationDTO> dtoMap = mapper.createTypeMap(UserDesignationEntity.class, UserDesignationDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDesignation(), UserDesignationDTO::setCodDesignation);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), UserDesignationDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getIdThirdParty(), UserDesignationDTO::setIdThirdParty);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, UserDesignationDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, UserDesignationDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, UserDesignationDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, UserDesignationDTO::setLastCheckerDateTime);

        TypeMap<UserDesignationDTO, UserDesignationEntity> entityMap = mapper.createTypeMap(UserDesignationDTO.class, UserDesignationEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, UserDesignationEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, UserDesignationEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, UserDesignationEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, UserDesignationEntity::setLastCheckerDateTime);
    }

    private UserDesignationEmbeddedKey id(String designation, CodRecordStatus status, Long idThirdParty) {
        return UserDesignationEmbeddedKey.builder().codDesignation(designation).codRecordStatus(status.name()).idThirdParty(idThirdParty).build();
    }

    private Specification<UserDesignationEntity> getSpec(List<CodRecordStatus> statuses, String designation, Long idThirdParty) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(designation, "codDesignation"))
                .and(repository.findRecordWithCode(idThirdParty, "idThirdParty"));
    }
}
