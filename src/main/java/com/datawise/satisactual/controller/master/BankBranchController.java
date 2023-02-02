package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.BankBranchEmbeddedKey;
import com.datawise.satisactual.entities.master.BankBranchEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.BankBranchDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.BankBranchRepository;
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
import java.util.function.BiFunction;

@Validated
@RestController
@RequestMapping("/mst/bank-branch")
@CrossOrigin
public class BankBranchController {

    @Autowired
    private CommonMasterService<BankBranchEntity, BankBranchEmbeddedKey, BankBranchDTO> service;
    @Autowired
    private BankBranchRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<BankBranchDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, BankBranchDTO.class));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<BankBranchDTO> get(@Valid @NotBlank @Size(min = 1, max = 24) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, BankBranchDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody BankBranchDTO dto) {
        service.save(
                repository,
                dto,
                BankBranchEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodIfscBankBranch()),
                dto.getCodIfscBankBranch(),
                mapper,
                this.id.apply(dto.getCodIfscBankBranch(), CodRecordStatus.N),
                this.id.apply(dto.getCodIfscBankBranch(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody BankBranchDTO dto) {
        service.update(
                repository,
                dto,
                BankBranchEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodIfscBankBranch()),
                dto.getCodIfscBankBranch(),
                mapper,
                this.id.apply(dto.getCodIfscBankBranch(), CodRecordStatus.M),
                this.id.apply(dto.getCodIfscBankBranch(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 24) @PathVariable("id") String id) {
        service.delete(
                repository,
                BankBranchDTO.class,
                BankBranchEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.X),
                this.id.apply(id, CodRecordStatus.C),
                this.id.apply(id, CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 24) @PathVariable("id") String id) {
        service.reopen(
                repository,
                BankBranchDTO.class,
                BankBranchEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.R),
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 24) @PathVariable("id") String id) {
        service.authorize(
                repository,
                BankBranchDTO.class,
                BankBranchEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.R, CodRecordStatus.X, CodRecordStatus.M, CodRecordStatus.N), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<BankBranchEntity, BankBranchDTO> dtoMap = mapper.createTypeMap(BankBranchEntity.class, BankBranchDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodIfscBankBranch(), BankBranchDTO::setCodIfscBankBranch);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), BankBranchDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, BankBranchDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, BankBranchDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, BankBranchDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, BankBranchDTO::setLastCheckerDateTime);

        TypeMap<BankBranchDTO, BankBranchEntity> entityMap = mapper.createTypeMap(BankBranchDTO.class, BankBranchEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, BankBranchEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, BankBranchEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, BankBranchEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, BankBranchEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, BankBranchEmbeddedKey> id = (code, status) -> BankBranchEmbeddedKey.builder().codIfscBankBranch(code).codRecordStatus(status.name()).build();

    private Specification<BankBranchEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codIfscBankBranch"));
    }
}
