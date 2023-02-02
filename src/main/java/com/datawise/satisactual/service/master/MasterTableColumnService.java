package com.datawise.satisactual.service.master;

import com.datawise.satisactual.entities.master.MasterTableColumnEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.MasterTableColumnDTO;
import com.datawise.satisactual.repository.master.MasterTableColumnRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterTableColumnService {

    @Autowired
    private MasterTableColumnRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    public List<MasterTableColumnDTO> getAllColumnsByTableName(String tableName) {
        return repository.findByIdTableNameAndIdCodRecStatus(tableName, CodRecordStatus.A.name()).stream().
                map(table -> mapper.map(table, MasterTableColumnDTO.class)).
                collect(Collectors.toList());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<MasterTableColumnEntity, MasterTableColumnDTO> dtoMap = mapper.createTypeMap(MasterTableColumnEntity.class, MasterTableColumnDTO.class);
        dtoMap.addMapping(c -> c.getId().getTableName(), MasterTableColumnDTO::setTableName);
        dtoMap.addMapping(c -> c.getId().getColumnName(), MasterTableColumnDTO::setColumnName);
    }
}
