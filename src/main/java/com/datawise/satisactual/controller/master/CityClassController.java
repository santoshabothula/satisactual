package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CityClassEmbeddedKey;
import com.datawise.satisactual.entities.master.CityClassEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CityClassDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CityClassRepository;
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
@RequestMapping("/mst/city-class")
public class CityClassController {

    @Autowired
    private CommonMasterService<CityClassEntity, CityClassEmbeddedKey, CityClassDTO> service;
    @Autowired
    private CityClassRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CityClassDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CityClassDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityClassDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, CityClassDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CityClassDTO dto) {
        service.save(
                repository,
                dto,
                CityClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCityClass()),
                dto.getCodCityClass(),
                mapper,
                this.id.apply(dto.getCodCityClass(), CodRecordStatus.N),
                this.id.apply(dto.getCodCityClass(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CityClassDTO dto) {
        service.update(
                repository,
                dto,
                CityClassEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCityClass()),
                dto.getCodCityClass(),
                mapper,
                this.id.apply(dto.getCodCityClass(), CodRecordStatus.M),
                this.id.apply(dto.getCodCityClass(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                CityClassDTO.class,
                CityClassEntity.class,
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
                CityClassDTO.class,
                CityClassEntity.class,
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
                CityClassDTO.class,
                CityClassEntity.class,
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
        TypeMap<CityClassEntity, CityClassDTO> dtoMap = mapper.createTypeMap(CityClassEntity.class, CityClassDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCityClass(), CityClassDTO::setCodCityClass);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CityClassDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CityClassDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CityClassDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CityClassDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CityClassDTO::setLastCheckerDateTime);

        TypeMap<CityClassDTO, CityClassEntity> entityMap = mapper.createTypeMap(CityClassDTO.class, CityClassEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CityClassEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CityClassEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CityClassEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CityClassEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, CityClassEmbeddedKey> id = (code, status) -> CityClassEmbeddedKey.builder().codCityClass(code).codRecordStatus(status.name()).build();

    private Specification<CityClassEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codCityClass"));
    }
}
