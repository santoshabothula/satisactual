package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.ThirdPartyTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.ThirdPartyTypeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.ThirdPartyTypeDTO;
import com.datawise.satisactual.repository.master.ThirdPartyTypeRepository;
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
import java.util.function.BiFunction;

@Validated
@RestController
@RequestMapping("/mst/third-party-type")
public class ThirdPartyTypeController {

    @Autowired
    private CommonMasterService<ThirdPartyTypeEntity, ThirdPartyTypeEmbeddedKey, ThirdPartyTypeDTO> service;
    @Autowired
    private ThirdPartyTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ThirdPartyTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ThirdPartyTypeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThirdPartyTypeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, ThirdPartyTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ThirdPartyTypeDTO dto) {
        service.save(
                repository,
                dto,
                ThirdPartyTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodThirdPartyType()),
                dto.getCodThirdPartyType(),
                mapper,
                this.id.apply(dto.getCodThirdPartyType(), CodRecordStatus.N),
                this.id.apply(dto.getCodThirdPartyType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ThirdPartyTypeDTO dto) {
        service.update(
                repository,
                dto,
                ThirdPartyTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodThirdPartyType()),
                dto.getCodThirdPartyType(),
                mapper,
                this.id.apply(dto.getCodThirdPartyType(), CodRecordStatus.M),
                this.id.apply(dto.getCodThirdPartyType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                ThirdPartyTypeDTO.class,
                ThirdPartyTypeEntity.class,
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
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.reopen(
                repository,
                ThirdPartyTypeDTO.class,
                ThirdPartyTypeEntity.class,
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
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.authorize(
                repository,
                ThirdPartyTypeDTO.class,
                ThirdPartyTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<ThirdPartyTypeEntity, ThirdPartyTypeDTO> dtoMap = mapper.createTypeMap(ThirdPartyTypeEntity.class, ThirdPartyTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodThirdPartyType(), ThirdPartyTypeDTO::setCodThirdPartyType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ThirdPartyTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ThirdPartyTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ThirdPartyTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ThirdPartyTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ThirdPartyTypeDTO::setLastCheckerDateTime);

        TypeMap<ThirdPartyTypeDTO, ThirdPartyTypeEntity> entityMap = mapper.createTypeMap(ThirdPartyTypeDTO.class, ThirdPartyTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ThirdPartyTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ThirdPartyTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ThirdPartyTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ThirdPartyTypeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, ThirdPartyTypeEmbeddedKey> id = (code, status) -> ThirdPartyTypeEmbeddedKey.builder().codThirdPartyType(code).codRecordStatus(status.name()).build();

    private Specification<ThirdPartyTypeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codThirdPartyType"));
    }
}
