package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CurrencyEmbeddedKey;
import com.datawise.satisactual.entities.master.CurrencyEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CurrencyDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CurrencyRepository;
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
@RequestMapping("/mst/currency")
@CrossOrigin
public class CurrencyController {

    @Autowired
    private CommonMasterService<CurrencyEntity, CurrencyEmbeddedKey, CurrencyDTO> service;
    @Autowired
    private CurrencyRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<CurrencyDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CurrencyDTO.class));
    }

    @GetMapping("/{id}/{date}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CurrencyDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A), mapper, CurrencyDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CurrencyDTO dto) {
        service.save(
                repository,
                dto,
                CurrencyEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCurrency()),
                dto.getCodCurrency(),
                mapper,
                this.id(dto.getCodCurrency(), CodRecordStatus.N),
                this.id(dto.getCodCurrency(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CurrencyDTO dto) {
        service.update(
                repository,
                dto,
                CurrencyEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCurrency()),
                dto.getCodCurrency(),
                mapper,
                this.id(dto.getCodCurrency(), CodRecordStatus.M),
                this.id(dto.getCodCurrency(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.delete(
                repository,
                CurrencyDTO.class,
                CurrencyEntity.class,
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
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.reopen(
                repository,
                CurrencyDTO.class,
                CurrencyEntity.class,
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
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.authorize(
                repository,
                CurrencyDTO.class,
                CurrencyEntity.class,
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
        TypeMap<CurrencyEntity, CurrencyDTO> dtoMap = mapper.createTypeMap(CurrencyEntity.class, CurrencyDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCurrency(), CurrencyDTO::setCodCurrency);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CurrencyDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CurrencyDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CurrencyDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CurrencyDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CurrencyDTO::setLastCheckerDateTime);

        TypeMap<CurrencyDTO, CurrencyEntity> entityMap = mapper.createTypeMap(CurrencyDTO.class, CurrencyEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CurrencyEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CurrencyEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CurrencyEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CurrencyEntity::setLastCheckerDateTime);
    }

    private CurrencyEmbeddedKey id(String code, CodRecordStatus status) {
        return CurrencyEmbeddedKey.builder().codCurrency(code).codRecordStatus(status.name()).build();
    }

    private Specification<CurrencyEntity> getSpec(List<CodRecordStatus> statuses, String code) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codCurrency"));
    }
}
