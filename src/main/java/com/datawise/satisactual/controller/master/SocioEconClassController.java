package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.SocioEconClassEmbeddedKey;
import com.datawise.satisactual.entities.master.SocioEconClassEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.SocioEconClassDTO;
import com.datawise.satisactual.repository.master.SocioEconClassRepository;
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
@RequestMapping("/mst/socio-econ-class")
@CrossOrigin
public class SocioEconClassController {

    @Autowired
    private CommonMasterService<SocioEconClassEntity, SocioEconClassEmbeddedKey, SocioEconClassDTO> service;
    @Autowired
    private SocioEconClassRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<SocioEconClassDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, SocioEconClassDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioEconClassDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, SocioEconClassDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody SocioEconClassDTO dto) {
        service.save(
                repository,
                dto,
                SocioEconClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodSocioEconClass()),
                dto.getCodSocioEconClass(),
                mapper,
                this.id.apply(dto.getCodSocioEconClass(), CodRecordStatus.N),
                this.id.apply(dto.getCodSocioEconClass(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody SocioEconClassDTO dto) {
        service.update(
                repository,
                dto,
                SocioEconClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodSocioEconClass()),
                dto.getCodSocioEconClass(),
                mapper,
                this.id.apply(dto.getCodSocioEconClass(), CodRecordStatus.M),
                this.id.apply(dto.getCodSocioEconClass(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                SocioEconClassDTO.class,
                SocioEconClassEntity.class,
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
                SocioEconClassDTO.class,
                SocioEconClassEntity.class,
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
                SocioEconClassDTO.class,
                SocioEconClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<SocioEconClassEntity, SocioEconClassDTO> dtoMap = mapper.createTypeMap(SocioEconClassEntity.class, SocioEconClassDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodSocioEconClass(), SocioEconClassDTO::setCodSocioEconClass);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), SocioEconClassDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, SocioEconClassDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, SocioEconClassDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, SocioEconClassDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, SocioEconClassDTO::setLastCheckerDateTime);

        TypeMap<SocioEconClassDTO, SocioEconClassEntity> entityMap = mapper.createTypeMap(SocioEconClassDTO.class, SocioEconClassEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, SocioEconClassEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, SocioEconClassEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, SocioEconClassEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, SocioEconClassEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, SocioEconClassEmbeddedKey> id = (code, status) -> SocioEconClassEmbeddedKey.builder().codSocioEconClass(code).codRecordStatus(status.name()).build();

    private Specification<SocioEconClassEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codSocioEconClass"));
    }
}
