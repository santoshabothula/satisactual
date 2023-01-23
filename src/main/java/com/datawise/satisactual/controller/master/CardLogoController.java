package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CardLogoEmbeddedKey;
import com.datawise.satisactual.entities.master.CardLogoEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CardLogoDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CardLogoRepository;
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
@RequestMapping("/mst/card-logo")
public class CardLogoController {

    @Autowired
    private CommonMasterService<CardLogoEntity, CardLogoEmbeddedKey, CardLogoDTO> service;
    @Autowired
    private CardLogoRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CardLogoDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CardLogoDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardLogoDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, CardLogoDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CardLogoDTO dto) {
        service.save(
                repository,
                dto,
                CardLogoEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodLogo()),
                dto.getCodLogo(),
                mapper,
                this.id.apply(dto.getCodLogo(), CodRecordStatus.N),
                this.id.apply(dto.getCodLogo(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CardLogoDTO dto) {
        service.update(
                repository,
                dto,
                CardLogoEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodLogo()),
                dto.getCodLogo(),
                mapper,
                this.id.apply(dto.getCodLogo(), CodRecordStatus.M),
                this.id.apply(dto.getCodLogo(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                CardLogoDTO.class,
                CardLogoEntity.class,
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
                CardLogoDTO.class,
                CardLogoEntity.class,
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
                CardLogoDTO.class,
                CardLogoEntity.class,
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
        TypeMap<CardLogoEntity, CardLogoDTO> dtoMap = mapper.createTypeMap(CardLogoEntity.class, CardLogoDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodLogo(), CardLogoDTO::setCodLogo);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CardLogoDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CardLogoDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CardLogoDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CardLogoDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CardLogoDTO::setLastCheckerDateTime);

        TypeMap<CardLogoDTO, CardLogoEntity> entityMap = mapper.createTypeMap(CardLogoDTO.class, CardLogoEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CardLogoEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CardLogoEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CardLogoEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CardLogoEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, CardLogoEmbeddedKey> id = (code, status) -> CardLogoEmbeddedKey.builder().codLogo(code).codRecordStatus(status.name()).build();

    private Specification<CardLogoEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codLogo"));
    }
}
