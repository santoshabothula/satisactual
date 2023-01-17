package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.PromoCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.PromoCodeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.PromoCodeDTO;
import com.datawise.satisactual.repository.master.PromoCodeRepository;
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
@RequestMapping("/mst/promo-code")
public class PromoCodeController {

    @Autowired
    private CommonMasterService<PromoCodeEntity, PromoCodeEmbeddedKey, PromoCodeDTO> service;
    @Autowired
    private PromoCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<PromoCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, PromoCodeDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, PromoCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody PromoCodeDTO dto) {
        service.save(
                repository,
                dto,
                PromoCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPromo()),
                dto.getCodPromo(),
                mapper,
                this.id.apply(dto.getCodPromo(), CodRecordStatus.N),
                this.id.apply(dto.getCodPromo(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody PromoCodeDTO dto) {
        service.update(
                repository,
                dto,
                PromoCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodPromo()),
                dto.getCodPromo(),
                mapper,
                this.id.apply(dto.getCodPromo(), CodRecordStatus.M),
                this.id.apply(dto.getCodPromo(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                PromoCodeDTO.class,
                PromoCodeEntity.class,
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
                PromoCodeDTO.class,
                PromoCodeEntity.class,
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
                PromoCodeDTO.class,
                PromoCodeEntity.class,
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
        TypeMap<PromoCodeEntity, PromoCodeDTO> dtoMap = mapper.createTypeMap(PromoCodeEntity.class, PromoCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodPromo(), PromoCodeDTO::setCodPromo);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), PromoCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, PromoCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, PromoCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, PromoCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, PromoCodeDTO::setLastCheckerDateTime);

        TypeMap<PromoCodeDTO, PromoCodeEntity> entityMap = mapper.createTypeMap(PromoCodeDTO.class, PromoCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, PromoCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, PromoCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, PromoCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, PromoCodeEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, PromoCodeEmbeddedKey> id = (code, status) -> PromoCodeEmbeddedKey.builder().codPromo(code).codRecordStatus(status.name()).build();

    private Specification<PromoCodeEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codPromo"));
    }
}
