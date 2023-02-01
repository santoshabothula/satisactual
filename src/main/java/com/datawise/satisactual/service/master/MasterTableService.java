package com.datawise.satisactual.service.master;

import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.MasterTableDTO;
import com.datawise.satisactual.repository.MasterTableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterTableService {

    @Autowired
    private MasterTableRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    public List<MasterTableDTO> getAllTableNames() {
        return repository.findByCodRecordStatus(CodRecordStatus.A.name()).stream().
                map(table -> MasterTableDTO.builder().tableName(table.getTableName()).tableDescription(table.getTableDesc()).build()).
                collect(Collectors.toList());
    }

    public MasterTableDTO getByTableName(String tableName) {
        return mapper.map(repository.findByTableNameAndCodRecordStatus(tableName, CodRecordStatus.A.name()), MasterTableDTO.class);
    }
}
