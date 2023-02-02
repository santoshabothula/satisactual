package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CampaignTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CampaignTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CampaignTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CampaignTypeRepository;
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
@RequestMapping("/mst/campaign-type")
@CrossOrigin
public class CampaignTypeController {

    @Autowired
    private CommonMasterService<CampaignTypeEntity, CampaignTypeEmbeddedKey, CampaignTypeDTO> service;
    @Autowired
    private CampaignTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<CampaignTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CampaignTypeDTO.class));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CampaignTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, CampaignTypeDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CampaignTypeDTO dto) {
        service.save(
                repository,
                dto,
                CampaignTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCampaignType()),
                dto.getCodCampaignType(),
                mapper,
                this.id.apply(dto.getCodCampaignType(), CodRecordStatus.N),
                this.id.apply(dto.getCodCampaignType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CampaignTypeDTO dto) {
        service.update(
                repository,
                dto,
                CampaignTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCampaignType()),
                dto.getCodCampaignType(),
                mapper,
                this.id.apply(dto.getCodCampaignType(), CodRecordStatus.M),
                this.id.apply(dto.getCodCampaignType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                CampaignTypeDTO.class,
                CampaignTypeEntity.class,
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
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.reopen(
                repository,
                CampaignTypeDTO.class,
                CampaignTypeEntity.class,
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
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.authorize(
                repository,
                CampaignTypeDTO.class,
                CampaignTypeEntity.class,
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
        TypeMap<CampaignTypeEntity, CampaignTypeDTO> dtoMap = mapper.createTypeMap(CampaignTypeEntity.class, CampaignTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCampaignType(), CampaignTypeDTO::setCodCampaignType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CampaignTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CampaignTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CampaignTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CampaignTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CampaignTypeDTO::setLastCheckerDateTime);

        TypeMap<CampaignTypeDTO, CampaignTypeEntity> entityMap = mapper.createTypeMap(CampaignTypeDTO.class, CampaignTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CampaignTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CampaignTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CampaignTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CampaignTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, CampaignTypeEmbeddedKey> id = (code, status) -> CampaignTypeEmbeddedKey.builder().codCampaignType(code).codRecordStatus(status.name()).build();

    private Specification<CampaignTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codCampaignType"));
    }
}
