package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CusFinStmtLineEmbeddedKey;
import com.datawise.satisactual.entities.master.CusFinStmtLineEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CusFinStmtLineDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CusFinStmtLineRepository;
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
@RequestMapping("/mst/cus-fin-stmt-line")
public class CusFinStmtLineController {

    @Autowired
    private CommonMasterService<CusFinStmtLineEntity, CusFinStmtLineEmbeddedKey, CusFinStmtLineDTO> service;
    @Autowired
    private CusFinStmtLineRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CusFinStmtLineDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CusFinStmtLineDTO.class));
    }

    @GetMapping("/{id}/{date}")
    public ResponseEntity<CusFinStmtLineDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A), mapper, CusFinStmtLineDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CusFinStmtLineDTO dto) {
        service.save(
                repository,
                dto,
                CusFinStmtLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFinStmtLine()),
                dto.getCodFinStmtLine(),
                mapper,
                this.id(dto.getCodFinStmtLine(), CodRecordStatus.N),
                this.id(dto.getCodFinStmtLine(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CusFinStmtLineDTO dto) {
        service.update(
                repository,
                dto,
                CusFinStmtLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodFinStmtLine()),
                dto.getCodFinStmtLine(),
                mapper,
                this.id(dto.getCodFinStmtLine(), CodRecordStatus.M),
                this.id(dto.getCodFinStmtLine(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.delete(
                repository,
                CusFinStmtLineDTO.class,
                CusFinStmtLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.X),
                this.id(id, CodRecordStatus.C),
                this.id(id, CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}/{date}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.reopen(
                repository,
                CusFinStmtLineDTO.class,
                CusFinStmtLineEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.R),
                this.id(id, CodRecordStatus.A),
                this.id(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.authorize(
                repository,
                CusFinStmtLineDTO.class,
                CusFinStmtLineEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.A),
                this.id(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<CusFinStmtLineEntity, CusFinStmtLineDTO> dtoMap = mapper.createTypeMap(CusFinStmtLineEntity.class, CusFinStmtLineDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodFinStmtLine(), CusFinStmtLineDTO::setCodFinStmtLine);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CusFinStmtLineDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CusFinStmtLineDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CusFinStmtLineDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CusFinStmtLineDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CusFinStmtLineDTO::setLastCheckerDateTime);

        TypeMap<CusFinStmtLineDTO, CusFinStmtLineEntity> entityMap = mapper.createTypeMap(CusFinStmtLineDTO.class, CusFinStmtLineEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CusFinStmtLineEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CusFinStmtLineEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CusFinStmtLineEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CusFinStmtLineEntity::setLastCheckerDateTime);
    }

    private CusFinStmtLineEmbeddedKey id(String code, CodRecordStatus status) {
        return CusFinStmtLineEmbeddedKey.builder().codFinStmtLine(code).codRecordStatus(status.name()).build();
    }

    private Specification<CusFinStmtLineEntity> getSpec(List<CodRecordStatus> statuses, String code) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codFinStmtLine"));
    }
}
