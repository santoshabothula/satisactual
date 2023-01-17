package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.StateCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.StateCodeEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.StateCodeDTO;
import com.datawise.satisactual.repository.master.StateCodeRepository;
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
@RequestMapping("/mst/state-code")
public class StateCodeController {

    @Autowired
    private CommonMasterService<StateCodeEntity, StateCodeEmbeddedKey, StateCodeDTO> service;
    @Autowired
    private StateCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<StateCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, StateCodeDTO.class));
    }

    @GetMapping("/{state}/{country}")
    public ResponseEntity<StateCodeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(state, CodRecordStatus.A, country), mapper, StateCodeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody StateCodeDTO dto) {
        service.save(
                repository,
                dto,
                StateCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodState(), dto.getCodCountry()),
                dto.getCodState() + ";" + dto.getCodCountry(),
                mapper,
                this.id(dto.getCodState(), CodRecordStatus.N, dto.getCodCountry()),
                this.id(dto.getCodState(), CodRecordStatus.A, dto.getCodCountry())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody StateCodeDTO dto) {
        service.update(
                repository,
                dto,
                StateCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodState(), dto.getCodCountry()),
                dto.getCodState() + ";" + dto.getCodCountry(),
                mapper,
                this.id(dto.getCodState(), CodRecordStatus.M, dto.getCodCountry()),
                this.id(dto.getCodState(), CodRecordStatus.A, dto.getCodCountry())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{state}/{country}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.delete(
                repository,
                StateCodeDTO.class,
                StateCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), state, country),
                state + ";" + country,
                mapper,
                this.id(state, CodRecordStatus.X, country),
                this.id(state, CodRecordStatus.C, country),
                this.id(state, CodRecordStatus.A, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{state}/{country}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.reopen(
                repository,
                StateCodeDTO.class,
                StateCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), state, country),
                state + ";" + country,
                mapper,
                this.id(state, CodRecordStatus.R, country),
                this.id(state, CodRecordStatus.A, country),
                this.id(state, CodRecordStatus.C, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{state}/{country}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 12) @PathVariable("state") String state,
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("country") String country
    ) {
        service.authorize(
                repository,
                StateCodeDTO.class,
                StateCodeEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), state, country),
                state + ";" + country,
                mapper,
                this.id(state, CodRecordStatus.A, country),
                this.id(state, CodRecordStatus.C, country)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<StateCodeEntity, StateCodeDTO> dtoMap = mapper.createTypeMap(StateCodeEntity.class, StateCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodState(), StateCodeDTO::setCodState);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), StateCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getCodCountry(), StateCodeDTO::setCodCountry);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, StateCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, StateCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, StateCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, StateCodeDTO::setLastCheckerDateTime);

        TypeMap<StateCodeDTO, StateCodeEntity> entityMap = mapper.createTypeMap(StateCodeDTO.class, StateCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, StateCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, StateCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, StateCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, StateCodeEntity::setLastCheckerDateTime);
    }

    private StateCodeEmbeddedKey id(String state, CodRecordStatus status, String country) {
        return StateCodeEmbeddedKey.builder().codState(state).codRecordStatus(status.name()).codCountry(country).build();
    }

    private Specification<StateCodeEntity> getSpec(List<CodRecordStatus> statuses, String state, String country) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(state, "codState"))
                .and(repository.findRecordWithCode(country, "codCountry"));
    }
}
