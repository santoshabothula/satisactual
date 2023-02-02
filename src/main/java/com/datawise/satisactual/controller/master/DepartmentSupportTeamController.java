package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.DepartmentSupportTeamEmbeddedKey;
import com.datawise.satisactual.entities.master.DepartmentSupportTeamEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.DepartmentSupportTeamDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.DepartmentSupportTeamRepository;
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
@RequestMapping("/mst/department-support-team")
@CrossOrigin
public class DepartmentSupportTeamController {

    @Autowired
    private CommonMasterService<DepartmentSupportTeamEntity, DepartmentSupportTeamEmbeddedKey, DepartmentSupportTeamDTO> service;
    @Autowired
    private DepartmentSupportTeamRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<DepartmentSupportTeamDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, DepartmentSupportTeamDTO.class));
    }

    @GetMapping("/{department}/{third-party}/{login-id}")
    public ResponseEntity<DepartmentSupportTeamDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("department") String department,
            @Valid @NotNull @PathVariable("third-party") Long thirdParty,
            @Valid @NotBlank @Size(min = 1, max = 48) @PathVariable("login-id") String loginId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(department, CodRecordStatus.A, thirdParty, loginId), mapper, DepartmentSupportTeamDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody DepartmentSupportTeamDTO dto) {
        service.save(
                repository,
                dto,
                DepartmentSupportTeamEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDepartment(), dto.getIdThirdParty(), dto.getLoginId()),
                dto.getCodDepartment() + ";" + dto.getIdThirdParty() + ";" + dto.getLoginId(),
                mapper,
                this.id(dto.getCodDepartment(), CodRecordStatus.N, dto.getIdThirdParty(), dto.getLoginId()),
                this.id(dto.getCodDepartment(), CodRecordStatus.A, dto.getIdThirdParty(), dto.getLoginId())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody DepartmentSupportTeamDTO dto) {
        service.update(
                repository,
                dto,
                DepartmentSupportTeamEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDepartment(), dto.getIdThirdParty(), dto.getLoginId()),
                dto.getCodDepartment() + ";" + dto.getIdThirdParty() + ";" + dto.getLoginId(),
                mapper,
                this.id(dto.getCodDepartment(), CodRecordStatus.M, dto.getIdThirdParty(), dto.getLoginId()),
                this.id(dto.getCodDepartment(), CodRecordStatus.A, dto.getIdThirdParty(), dto.getLoginId())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{department}/{third-party}/{login-id}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("department") String department,
            @Valid @NotNull @PathVariable("third-party") Long thirdParty,
            @Valid @NotBlank @Size(min = 1, max = 48) @PathVariable("login-id") String loginId
    ) {
        service.delete(
                repository,
                DepartmentSupportTeamDTO.class,
                DepartmentSupportTeamEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), department, thirdParty, loginId),
                department + ";" + thirdParty + ";" + loginId,
                mapper,
                this.id(department, CodRecordStatus.X, thirdParty, loginId),
                this.id(department, CodRecordStatus.C, thirdParty, loginId),
                this.id(department, CodRecordStatus.A, thirdParty, loginId)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{department}/{third-party}/{login-id}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("department") String department,
            @Valid @NotNull @PathVariable("third-party") Long thirdParty,
            @Valid @NotBlank @Size(min = 1, max = 48) @PathVariable("login-id") String loginId
    ) {
        service.reopen(
                repository,
                DepartmentSupportTeamDTO.class,
                DepartmentSupportTeamEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), department, thirdParty, loginId),
                department + ";" + thirdParty + ";" + loginId,
                mapper,
                this.id(department, CodRecordStatus.R, thirdParty, loginId),
                this.id(department, CodRecordStatus.A, thirdParty, loginId),
                this.id(department, CodRecordStatus.C, thirdParty, loginId)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{department}/{third-party}/{login-id}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("department") String department,
            @Valid @NotNull @PathVariable("third-party") Long thirdParty,
            @Valid @NotBlank @Size(min = 1, max = 48) @PathVariable("login-id") String loginId
    ) {
        service.authorize(
                repository,
                DepartmentSupportTeamDTO.class,
                DepartmentSupportTeamEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), department, thirdParty, loginId),
                department + ";" + thirdParty + ";" + loginId,
                mapper,
                this.id(department, CodRecordStatus.A, thirdParty, loginId),
                this.id(department, CodRecordStatus.C, thirdParty, loginId)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<DepartmentSupportTeamEntity, DepartmentSupportTeamDTO> dtoMap = mapper.createTypeMap(DepartmentSupportTeamEntity.class, DepartmentSupportTeamDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDepartment(), DepartmentSupportTeamDTO::setCodDepartment);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), DepartmentSupportTeamDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getLoginId(), DepartmentSupportTeamDTO::setLoginId);
        dtoMap.addMapping(c -> c.getId().getLoginId(), DepartmentSupportTeamDTO::setLoginId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, DepartmentSupportTeamDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, DepartmentSupportTeamDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, DepartmentSupportTeamDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, DepartmentSupportTeamDTO::setLastCheckerDateTime);

        TypeMap<DepartmentSupportTeamDTO, DepartmentSupportTeamEntity> entityMap = mapper.createTypeMap(DepartmentSupportTeamDTO.class, DepartmentSupportTeamEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, DepartmentSupportTeamEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, DepartmentSupportTeamEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, DepartmentSupportTeamEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, DepartmentSupportTeamEntity::setLastCheckerDateTime);
    }

    private DepartmentSupportTeamEmbeddedKey id(String code, CodRecordStatus status, Long thirdParty, String loginId) {
        return DepartmentSupportTeamEmbeddedKey.builder().codDepartment(code).codRecordStatus(status.name()).idThirdParty(thirdParty).loginId(loginId).build();
    }

    private Specification<DepartmentSupportTeamEntity> getSpec(List<CodRecordStatus> statuses, String code, Long thirdParty, String loginId) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codDepartment"))
                .and(repository.findRecordWithCode(thirdParty, "idThirdParty"))
                .and(repository.findRecordWithCode(loginId, "loginId"));
    }
}
