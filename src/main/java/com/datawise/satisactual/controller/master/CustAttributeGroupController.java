package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CustAttributeGroupEmbeddedKey;
import com.datawise.satisactual.entities.master.CustAttributeGroupEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustAttributeGroupDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CustAttributeGroupRepository;
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
@RequestMapping("/mst/cust-attribute-group")
public class CustAttributeGroupController {

    @Autowired
    private CommonMasterService<CustAttributeGroupEntity, CustAttributeGroupEmbeddedKey, CustAttributeGroupDTO> service;
    @Autowired
    private CustAttributeGroupRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CustAttributeGroupDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CustAttributeGroupDTO.class));
    }

    @GetMapping("/{id}/{date}")
    public ResponseEntity<CustAttributeGroupDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @PathVariable("desc") String desc
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A, desc), mapper, CustAttributeGroupDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CustAttributeGroupDTO dto) {
        service.save(
                repository,
                dto,
                CustAttributeGroupEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAttributeGroup(), dto.getAttributeGroupDesc()),
                dto.getCodAttributeGroup() + ";" + dto.getAttributeGroupDesc(),
                mapper,
                this.id(dto.getCodAttributeGroup(), CodRecordStatus.N, dto.getAttributeGroupDesc()),
                this.id(dto.getCodAttributeGroup(), CodRecordStatus.A, dto.getAttributeGroupDesc())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CustAttributeGroupDTO dto) {
        service.update(
                repository,
                dto,
                CustAttributeGroupEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodAttributeGroup(), dto.getAttributeGroupDesc()),
                dto.getCodAttributeGroup() + ";" + dto.getAttributeGroupDesc(),
                mapper,
                this.id(dto.getCodAttributeGroup(), CodRecordStatus.M, dto.getAttributeGroupDesc()),
                this.id(dto.getCodAttributeGroup(), CodRecordStatus.A, dto.getAttributeGroupDesc())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotBlank @PathVariable("desc") String desc
    ) {
        service.delete(
                repository,
                CustAttributeGroupDTO.class,
                CustAttributeGroupEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id, desc),
                id + ";" + desc,
                mapper,
                this.id(id, CodRecordStatus.X, desc),
                this.id(id, CodRecordStatus.C, desc),
                this.id(id, CodRecordStatus.A, desc)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}/{date}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @PathVariable("desc") String desc
    ) {
        service.reopen(
                repository,
                CustAttributeGroupDTO.class,
                CustAttributeGroupEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id, desc),
                id + ";" + desc,
                mapper,
                this.id(id, CodRecordStatus.R, desc),
                this.id(id, CodRecordStatus.A, desc),
                this.id(id, CodRecordStatus.C, desc)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @PathVariable("desc") String desc
    ) {
        service.authorize(
                repository,
                CustAttributeGroupDTO.class,
                CustAttributeGroupEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id, desc),
                id,
                mapper,
                this.id(id, CodRecordStatus.A, desc),
                this.id(id, CodRecordStatus.C, desc)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<CustAttributeGroupEntity, CustAttributeGroupDTO> dtoMap = mapper.createTypeMap(CustAttributeGroupEntity.class, CustAttributeGroupDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodAttributeGroup(), CustAttributeGroupDTO::setCodAttributeGroup);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CustAttributeGroupDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getAttributeGroupDesc(), CustAttributeGroupDTO::setAttributeGroupDesc);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CustAttributeGroupDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CustAttributeGroupDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CustAttributeGroupDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CustAttributeGroupDTO::setLastCheckerDateTime);

        TypeMap<CustAttributeGroupDTO, CustAttributeGroupEntity> entityMap = mapper.createTypeMap(CustAttributeGroupDTO.class, CustAttributeGroupEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CustAttributeGroupEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CustAttributeGroupEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CustAttributeGroupEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CustAttributeGroupEntity::setLastCheckerDateTime);
    }

    private CustAttributeGroupEmbeddedKey id(String code, CodRecordStatus status, String desc) {
        return CustAttributeGroupEmbeddedKey.builder().codAttributeGroup(code).codRecordStatus(status.name()).attributeGroupDesc(desc).build();
    }

    private Specification<CustAttributeGroupEntity> getSpec(List<CodRecordStatus> statuses, String code, String desc) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codAttributeGroup"))
                .and(repository.findRecordWithCode(desc, "attributeGroupDesc"));
    }
}
