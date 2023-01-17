package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.IndustryGrpEmbeddedKey;
import com.datawise.satisactual.entities.master.IndustryGrpEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.IndustryGrpDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.IndustryGrpRepository;
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
@RequestMapping("/mst/industry-grp")
public class IndustryGrpController {

    @Autowired
    private CommonMasterService<IndustryGrpEntity, IndustryGrpEmbeddedKey, IndustryGrpDTO> service;
    @Autowired
    private IndustryGrpRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<IndustryGrpDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, IndustryGrpDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryGrpDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, IndustryGrpDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody IndustryGrpDTO dto) {
        service.save(
                repository,
                dto,
                IndustryGrpEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodIndustryGrp()),
                dto.getCodIndustryGrp(),
                mapper,
                this.id.apply(dto.getCodIndustryGrp(), CodRecordStatus.N),
                this.id.apply(dto.getCodIndustryGrp(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody IndustryGrpDTO dto) {
        service.update(
                repository,
                dto,
                IndustryGrpEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodIndustryGrp()),
                dto.getCodIndustryGrp(),
                mapper,
                this.id.apply(dto.getCodIndustryGrp(), CodRecordStatus.M),
                this.id.apply(dto.getCodIndustryGrp(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                IndustryGrpDTO.class,
                IndustryGrpEntity.class,
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
                IndustryGrpDTO.class,
                IndustryGrpEntity.class,
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
                IndustryGrpDTO.class,
                IndustryGrpEntity.class,
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
        TypeMap<IndustryGrpEntity, IndustryGrpDTO> dtoMap = mapper.createTypeMap(IndustryGrpEntity.class, IndustryGrpDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodIndustryGrp(), IndustryGrpDTO::setCodIndustryGrp);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), IndustryGrpDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, IndustryGrpDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, IndustryGrpDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, IndustryGrpDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, IndustryGrpDTO::setLastCheckerDateTime);

        TypeMap<IndustryGrpDTO, IndustryGrpEntity> entityMap = mapper.createTypeMap(IndustryGrpDTO.class, IndustryGrpEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, IndustryGrpEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, IndustryGrpEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, IndustryGrpEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, IndustryGrpEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, IndustryGrpEmbeddedKey> id = (code, status) -> IndustryGrpEmbeddedKey.builder().codIndustryGrp(code).codRecordStatus(status.name()).build();

    private Specification<IndustryGrpEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codIndustryGrp"));
    }
}