package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.ContactChannelEmbeddedKey;
import com.datawise.satisactual.entities.master.ContactChannelEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.ContactChannelDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.ContactChannelRepository;
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
@RequestMapping("/mst/contact-channel")
@CrossOrigin
public class ContactChannelController {

    @Autowired
    private CommonMasterService<ContactChannelEntity, ContactChannelEmbeddedKey, ContactChannelDTO> service;
    @Autowired
    private ContactChannelRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<ContactChannelDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ContactChannelDTO.class));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<ContactChannelDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, ContactChannelDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ContactChannelDTO dto) {
        service.save(
                repository,
                dto,
                ContactChannelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodChannel()),
                dto.getCodChannel(),
                mapper,
                this.id.apply(dto.getCodChannel(), CodRecordStatus.N),
                this.id.apply(dto.getCodChannel(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ContactChannelDTO dto) {
        service.update(
                repository,
                dto,
                ContactChannelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodChannel()),
                dto.getCodChannel(),
                mapper,
                this.id.apply(dto.getCodChannel(), CodRecordStatus.M),
                this.id.apply(dto.getCodChannel(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                ContactChannelDTO.class,
                ContactChannelEntity.class,
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
                ContactChannelDTO.class,
                ContactChannelEntity.class,
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
                ContactChannelDTO.class,
                ContactChannelEntity.class,
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
        TypeMap<ContactChannelEntity, ContactChannelDTO> dtoMap = mapper.createTypeMap(ContactChannelEntity.class, ContactChannelDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodChannel(), ContactChannelDTO::setCodChannel);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ContactChannelDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ContactChannelDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ContactChannelDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ContactChannelDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ContactChannelDTO::setLastCheckerDateTime);

        TypeMap<ContactChannelDTO, ContactChannelEntity> entityMap = mapper.createTypeMap(ContactChannelDTO.class, ContactChannelEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ContactChannelEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ContactChannelEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ContactChannelEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ContactChannelEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, ContactChannelEmbeddedKey> id = (code, status) -> ContactChannelEmbeddedKey.builder().codChannel(code).codRecordStatus(status.name()).build();

    private Specification<ContactChannelEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codChannel"));
    }
}
