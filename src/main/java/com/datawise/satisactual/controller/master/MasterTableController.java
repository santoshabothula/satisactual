package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.model.master.dto.MasterTableDTO;
import com.datawise.satisactual.service.master.MasterTableService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mst/master-table")
@CrossOrigin
public class MasterTableController {

    @Autowired
    private MasterTableService service;

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<MasterTableDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllTableNames());
    }

    @GetMapping("/{table-name}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<MasterTableDTO> getByTableName(@PathVariable("table-name") String tableName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByTableName(tableName));
    }

}
