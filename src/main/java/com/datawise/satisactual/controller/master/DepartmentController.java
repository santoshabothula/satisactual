package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.DepartmentEmbeddedKey;
import com.datawise.satisactual.entities.master.DepartmentEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.DepartmentDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.DepartmentRepository;
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
@RequestMapping("/mst/department")
@CrossOrigin
public class DepartmentController {

    @Autowired
    private CommonMasterService<DepartmentEntity, DepartmentEmbeddedKey, DepartmentDTO> service;
    @Autowired
    private DepartmentRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, DepartmentDTO.class));
    }

    @GetMapping("/{department}/{thirdParty}")
    public ResponseEntity<DepartmentDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("department") String department,
            @Valid @NotNull @PathVariable("thirdParty") Long thirdParty
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(department, CodRecordStatus.A, thirdParty), mapper, DepartmentDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody DepartmentDTO dto) {
        service.save(
                repository,
                dto,
                DepartmentEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDepartment(), dto.getIdThirdParty()),
                dto.getCodDepartment() + ";" + dto.getIdThirdParty(),
                mapper,
                this.id(dto.getCodDepartment(), CodRecordStatus.N, dto.getIdThirdParty()),
                this.id(dto.getCodDepartment(), CodRecordStatus.A, dto.getIdThirdParty())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody DepartmentDTO dto) {
        service.update(
                repository,
                dto,
                DepartmentEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodDepartment(), dto.getIdThirdParty()),
                dto.getCodDepartment() + ";" + dto.getIdThirdParty(),
                mapper,
                this.id(dto.getCodDepartment(), CodRecordStatus.M, dto.getIdThirdParty()),
                this.id(dto.getCodDepartment(), CodRecordStatus.A, dto.getIdThirdParty())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{department}/{thirdParty}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("department") String department,
            @Valid @NotNull @PathVariable("thirdParty") Long thirdParty
    ) {
        service.delete(
                repository,
                DepartmentDTO.class,
                DepartmentEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), department, thirdParty),
                department + ";" + thirdParty,
                mapper,
                this.id(department, CodRecordStatus.X, thirdParty),
                this.id(department, CodRecordStatus.C, thirdParty),
                this.id(department, CodRecordStatus.A, thirdParty)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{department}/{thirdParty}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("department") String department,
            @Valid @NotNull @PathVariable("thirdParty") Long thirdParty
    ) {
        service.reopen(
                repository,
                DepartmentDTO.class,
                DepartmentEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), department, thirdParty),
                department + ";" + thirdParty,
                mapper,
                this.id(department, CodRecordStatus.R, thirdParty),
                this.id(department, CodRecordStatus.A, thirdParty),
                this.id(department, CodRecordStatus.C, thirdParty)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{department}/{thirdParty}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("department") String department,
            @Valid @NotNull @PathVariable("thirdParty") Long thirdParty
    ) {
        service.authorize(
                repository,
                DepartmentDTO.class,
                DepartmentEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), department, thirdParty),
                department + ";" + thirdParty,
                mapper,
                this.id(department, CodRecordStatus.A, thirdParty),
                this.id(department, CodRecordStatus.C, thirdParty)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<DepartmentEntity, DepartmentDTO> dtoMap = mapper.createTypeMap(DepartmentEntity.class, DepartmentDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodDepartment(), DepartmentDTO::setCodDepartment);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), DepartmentDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getIdThirdParty(), DepartmentDTO::setIdThirdParty);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, DepartmentDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, DepartmentDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, DepartmentDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, DepartmentDTO::setLastCheckerDateTime);

        TypeMap<DepartmentDTO, DepartmentEntity> entityMap = mapper.createTypeMap(DepartmentDTO.class, DepartmentEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, DepartmentEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, DepartmentEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, DepartmentEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, DepartmentEntity::setLastCheckerDateTime);
    }

    private DepartmentEmbeddedKey id(String code, CodRecordStatus status, Long thirdParty) {
        return DepartmentEmbeddedKey.builder().codDepartment(code).codRecordStatus(status.name()).idThirdParty(thirdParty).build();
    }

    private Specification<DepartmentEntity> getSpec(List<CodRecordStatus> statuses, String code, Long thirdParty) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codDepartment"))
                .and(repository.findRecordWithCode(thirdParty, "idThirdParty"));
    }
}
