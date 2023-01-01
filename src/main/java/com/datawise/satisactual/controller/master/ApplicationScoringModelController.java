package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.model.master.dto.ApplicationScoringModelDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.service.master.ApplicationScoringModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/application-scoring-model")
public class ApplicationScoringModelController {

    @Autowired
    private ApplicationScoringModelService service;

    @GetMapping
    public ResponseEntity<List<ApplicationScoringModelDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{scoring-model}/{status}/{date}")
    public ResponseEntity<ApplicationScoringModelDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("scoring-model") String scoringModel,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("status") String status,
            @Valid @NotNull @PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(scoringModel, status, date));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ApplicationScoringModelDTO dto) {
        service.save(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully save operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/saveAll")
    public ResponseEntity<CustomResponse> saveAll(@Valid @RequestBody List<ApplicationScoringModelDTO> dto) {
        service.saveAll(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully save batch operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ApplicationScoringModelDTO dto) {
        service.update(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully update operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/updateAll")
    public ResponseEntity<CustomResponse> updateAll(@Valid @RequestBody List<ApplicationScoringModelDTO> dto) {
        service.updateAll(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully update batch operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/delete")
    public ResponseEntity<CustomResponse> delete(@Valid @RequestBody ApplicationScoringModelDTO dto) {
        service.delete(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully delete operation completed").status(HttpStatus.OK.value()).build());
    }

    @GetMapping("/delete/{scoring-model}/{status}/{date}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Max(4) @PathVariable("scoring-model") String scoringModel,
            @Valid @NotBlank @Max(4) @PathVariable("status") String status,
            @Valid @NotBlank @PathVariable("date") LocalDate date
    ) {
        service.delete(scoringModel, status, date);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully delete operation completed").status(HttpStatus.OK.value()).build());
    }
}
