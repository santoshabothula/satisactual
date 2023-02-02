package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.model.master.dto.MasterTableColumnDTO;
import com.datawise.satisactual.service.master.MasterTableColumnService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mst/master-table-column")
public class MasterTableColumnController {

    @Autowired
    private MasterTableColumnService service;

    @GetMapping("/{table-name}")
    @ApiOperation(value = "Master Table Column", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<MasterTableColumnDTO>> getAllColumnsByTableName(@PathVariable("table-name") String tableName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllColumnsByTableName(tableName));
    }
}
