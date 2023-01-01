package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.model.master.dto.AddressTypesMasterDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.service.master.AddressTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
@RequestMapping("/address-type")
public class AddressTypesController {

    @Autowired
    private AddressTypesService service;

    @GetMapping
    public ResponseEntity<List<AddressTypesMasterDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{type}/{status}")
    public ResponseEntity<AddressTypesMasterDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("type") String type,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("status") String status
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(type, status));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody AddressTypesMasterDTO dto) {
        service.save(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully save operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/saveAll")
    public ResponseEntity<CustomResponse> saveAll(@Valid @RequestBody List<AddressTypesMasterDTO> dto) {
        service.saveAll(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully save batch operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody AddressTypesMasterDTO dto) {
        service.update(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully update operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/updateAll")
    public ResponseEntity<CustomResponse> updateAll(@Valid @RequestBody List<AddressTypesMasterDTO> dto) {
        service.updateAll(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully update batch operation completed").status(HttpStatus.OK.value()).build());
    }

    @PostMapping("/delete")
    public ResponseEntity<CustomResponse> delete(@Valid @RequestBody AddressTypesMasterDTO dto) {
        service.delete(dto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully delete operation completed").status(HttpStatus.OK.value()).build());
    }

    @GetMapping("/delete/{type}/{status}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("type") String type,
            @Valid @NotBlank @Size(min = 1, max = 1) @PathVariable("status") String status
    ) {
        service.delete(type, status);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(CustomResponse.builder().message("Successfully delete operation completed").status(HttpStatus.OK.value()).build());
    }
}
