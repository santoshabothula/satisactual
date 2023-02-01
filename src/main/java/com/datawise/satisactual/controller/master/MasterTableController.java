package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.model.master.dto.MasterTableDTO;
import com.datawise.satisactual.service.master.MasterTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mst/master-table")
public class MasterTableController {

    @Autowired
    private MasterTableService service;

    @GetMapping
    public ResponseEntity<List<MasterTableDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllTableNames());
    }

    @GetMapping("/{table-name}")
    public ResponseEntity<MasterTableDTO> getByTableName(@PathVariable("table-name") String tableName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByTableName(tableName));
    }

}
