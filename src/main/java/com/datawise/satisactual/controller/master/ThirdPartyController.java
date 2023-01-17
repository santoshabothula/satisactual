package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.ThirdPartyEmbeddedKey;
import com.datawise.satisactual.entities.master.ThirdPartyEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.ThirdPartyDTO;
import com.datawise.satisactual.repository.master.ThirdPartyRepository;
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
@RequestMapping("/mst/third-party")
public class ThirdPartyController {

    @Autowired
    private CommonMasterService<ThirdPartyEntity, ThirdPartyEmbeddedKey, ThirdPartyDTO> service;
    @Autowired
    private ThirdPartyRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ThirdPartyDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ThirdPartyDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThirdPartyDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, ThirdPartyDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ThirdPartyDTO dto) {
        service.save(
                repository,
                dto,
                ThirdPartyEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getIdThirdParty()),
                String.valueOf(dto.getIdThirdParty()),
                mapper,
                this.id.apply(dto.getIdThirdParty(), CodRecordStatus.N),
                this.id.apply(dto.getIdThirdParty(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ThirdPartyDTO dto) {
        service.update(
                repository,
                dto,
                ThirdPartyEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getIdThirdParty()),
                String.valueOf(dto.getIdThirdParty()),
                mapper,
                this.id.apply(dto.getIdThirdParty(), CodRecordStatus.M),
                this.id.apply(dto.getIdThirdParty(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") Long id) {
        service.delete(
                repository,
                ThirdPartyDTO.class,
                ThirdPartyEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                String.valueOf(id),
                mapper,
                this.id.apply(id, CodRecordStatus.X),
                this.id.apply(id, CodRecordStatus.C),
                this.id.apply(id, CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}")
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") Long id) {
        service.reopen(
                repository,
                ThirdPartyDTO.class,
                ThirdPartyEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                String.valueOf(id),
                mapper,
                this.id.apply(id, CodRecordStatus.R),
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") Long id) {
        service.authorize(
                repository,
                ThirdPartyDTO.class,
                ThirdPartyEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                String.valueOf(id),
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<ThirdPartyEntity, ThirdPartyDTO> dtoMap = mapper.createTypeMap(ThirdPartyEntity.class, ThirdPartyDTO.class);
        dtoMap.addMapping(c -> c.getId().getIdThirdParty(), ThirdPartyDTO::setIdThirdParty);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ThirdPartyDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ThirdPartyDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ThirdPartyDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ThirdPartyDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ThirdPartyDTO::setLastCheckerDateTime);

        TypeMap<ThirdPartyDTO, ThirdPartyEntity> entityMap = mapper.createTypeMap(ThirdPartyDTO.class, ThirdPartyEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ThirdPartyEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ThirdPartyEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ThirdPartyEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ThirdPartyEntity::setLastCheckerDateTime);
    }

    private final BiFunction<Long, CodRecordStatus, ThirdPartyEmbeddedKey> id = (code, status) -> ThirdPartyEmbeddedKey.builder().idThirdParty(code).codRecordStatus(status.name()).build();

    private Specification<ThirdPartyEntity> getSpec(List<CodRecordStatus> statuses, Long id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "idThirdParty"));
    }
}
