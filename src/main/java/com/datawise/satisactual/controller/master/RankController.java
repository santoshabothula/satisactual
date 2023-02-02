package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.RankEmbeddedKey;
import com.datawise.satisactual.entities.master.RankEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.RankDTO;
import com.datawise.satisactual.repository.master.RankRepository;
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

@Validated
@RestController
@RequestMapping("/mst/rank")
@CrossOrigin
public class RankController {

    @Autowired
    private CommonMasterService<RankEntity, RankEmbeddedKey, RankDTO> service;
    @Autowired
    private RankRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<RankDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, RankDTO.class));
    }

    @GetMapping("/{rank}/{wing}")
    public ResponseEntity<RankDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("rank") String rank,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("wing") String wing
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(rank, CodRecordStatus.A, wing), mapper, RankDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody RankDTO dto) {
        service.save(
                repository,
                dto,
                RankEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodRank(), dto.getWing()),
                dto.getCodRank() + ";" + dto.getWing(),
                mapper,
                this.id(dto.getCodRank(), CodRecordStatus.N, dto.getWing()),
                this.id(dto.getCodRank(), CodRecordStatus.A, dto.getWing())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody RankDTO dto) {
        service.update(
                repository,
                dto,
                RankEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodRank(), dto.getWing()),
                dto.getCodRank() + ";" + dto.getWing(),
                mapper,
                this.id(dto.getCodRank(), CodRecordStatus.M, dto.getWing()),
                this.id(dto.getCodRank(), CodRecordStatus.A, dto.getWing())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{rank}/{wing}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("rank") String Rank,
            @Valid @NotBlank @Size(min = 1, max = 6) @PathVariable("wing") String wing
    ) {
        service.delete(
                repository,
                RankDTO.class,
                RankEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), Rank, wing),
                Rank + ";" + wing,
                mapper,
                this.id(Rank, CodRecordStatus.X, wing),
                this.id(Rank, CodRecordStatus.C, wing),
                this.id(Rank, CodRecordStatus.A, wing)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{rank}/{wing}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("rank") String Rank,
            @Valid @NotBlank @Size(min = 1, max = 6) @PathVariable("wing") String wing
    ) {
        service.reopen(
                repository,
                RankDTO.class,
                RankEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), Rank, wing),
                Rank + ";" + wing,
                mapper,
                this.id(Rank, CodRecordStatus.R, wing),
                this.id(Rank, CodRecordStatus.A, wing),
                this.id(Rank, CodRecordStatus.C, wing)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{rank}/{wing}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("rank") String rank,
            @Valid @NotBlank @Size(min = 1, max = 6) @PathVariable("wing") String wing
    ) {
        service.authorize(
                repository,
                RankDTO.class,
                RankEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), rank, wing),
                rank + ";" + wing,
                mapper,
                this.id(rank, CodRecordStatus.A, wing),
                this.id(rank, CodRecordStatus.C, wing)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<RankEntity, RankDTO> dtoMap = mapper.createTypeMap(RankEntity.class, RankDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodRank(), RankDTO::setCodRank);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), RankDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getWing(), RankDTO::setWing);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, RankDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, RankDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, RankDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, RankDTO::setLastCheckerDateTime);

        TypeMap<RankDTO, RankEntity> entityMap = mapper.createTypeMap(RankDTO.class, RankEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, RankEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, RankEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, RankEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, RankEntity::setLastCheckerDateTime);
    }

    private RankEmbeddedKey id(String rank, CodRecordStatus status, String wing) {
        return RankEmbeddedKey.builder().codRank(rank).codRecordStatus(status.name()).wing(wing).build();
    }

    private Specification<RankEntity> getSpec(List<CodRecordStatus> statuses, String rank, String wing) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(rank, "codRank"))
                .and(repository.findRecordWithCode(wing, "wing"));
    }
}
