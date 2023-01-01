package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.model.master.dto.AssignmentTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.service.master.AssignmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("assignment-type")
public class AssignmentTypeController {

    @Autowired
    private AssignmentTypeService service;

    @GetMapping
    public ResponseEntity<List<AssignmentTypeDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{type}/{status}")
    public ResponseEntity<AssignmentTypeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("type") String currency,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("status") String status
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(currency, status));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody AssignmentTypeDTO dto) {
        service.save(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully save operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/saveAll")
    public ResponseEntity<CustomResponse> saveAll(@Valid @RequestBody List<AssignmentTypeDTO> dto) {
        service.saveAll(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully save batch operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody AssignmentTypeDTO dto) {
        service.update(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully update operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/updateAll")
    public ResponseEntity<CustomResponse> updateAll(@Valid @RequestBody List<AssignmentTypeDTO> dto) {
        service.updateAll(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully update batch operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/delete")
    public ResponseEntity<CustomResponse> delete(@Valid @RequestBody AssignmentTypeDTO dto) {
        service.delete(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully delete operation completed").status(HttpStatus.OK.value()).build());
    }

    @GetMapping("/delete/{type}/{status}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("type") String currency,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("status") String status
    ) {
        service.delete(currency, status);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully delete operation completed").status(HttpStatus.OK.value()).build());
    }
}
