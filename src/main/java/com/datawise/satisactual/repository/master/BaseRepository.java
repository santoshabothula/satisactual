package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.BaseDTO;
import com.datawise.satisactual.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BaseRepository {

    @Autowired
    private MasterTableRepository masterTableRepository;

    void findRecordWithStatusIn(BaseDTO baseDTO, List<CodRecordStatus> statuses) {
        
    }
}
