package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.IncomeSrcEmbeddedKey;
import com.datawise.satisactual.entities.master.IncomeSrcEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.IncomeSrcDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.IncomeSrcRepository;
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
@RequestMapping("/mst/income_src")
public class IncomeSrcController {

    @Autowired
    private CommonMasterService<IncomeSrcEntity, IncomeSrcEmbeddedKey, IncomeSrcDTO> service;
    @Autowired
    private IncomeSrcRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<IncomeSrcDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, IncomeSrcDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeSrcDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, IncomeSrcDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody IncomeSrcDTO dto) {
        service.save(
                repository,
                dto,
                IncomeSrcEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodIncomeSrc()),
                dto.getCodIncomeSrc(),
                mapper,
                this.id.apply(dto.getCodIncomeSrc(), CodRecordStatus.N),
                this.id.apply(dto.getCodIncomeSrc(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody IncomeSrcDTO dto) {
        service.update(
                repository,
                dto,
                IncomeSrcEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodIncomeSrc()),
                dto.getCodIncomeSrc(),
                mapper,
                this.id.apply(dto.getCodIncomeSrc(), CodRecordStatus.M),
                this.id.apply(dto.getCodIncomeSrc(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                IncomeSrcDTO.class,
                IncomeSrcEntity.class,
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
                IncomeSrcDTO.class,
                IncomeSrcEntity.class,
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
                IncomeSrcDTO.class,
                IncomeSrcEntity.class,
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
        TypeMap<IncomeSrcEntity, IncomeSrcDTO> dtoMap = mapper.createTypeMap(IncomeSrcEntity.class, IncomeSrcDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodIncomeSrc(), IncomeSrcDTO::setCodIncomeSrc);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), IncomeSrcDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, IncomeSrcDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, IncomeSrcDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, IncomeSrcDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, IncomeSrcDTO::setLastCheckerDateTime);

        TypeMap<IncomeSrcDTO, IncomeSrcEntity> entityMap = mapper.createTypeMap(IncomeSrcDTO.class, IncomeSrcEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, IncomeSrcEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, IncomeSrcEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, IncomeSrcEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, IncomeSrcEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, IncomeSrcEmbeddedKey> id = (code, status) -> IncomeSrcEmbeddedKey.builder().codIncomeSrc(code).codRecordStatus(status.name()).build();

    private Specification<IncomeSrcEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codIncomeSrc"));
    }
}
