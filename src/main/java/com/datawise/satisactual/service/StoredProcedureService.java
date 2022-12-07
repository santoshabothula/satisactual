package com.datawise.satisactual.service;

import com.datawise.satisactual.mapper.StoredProcedureMapper;
import com.datawise.satisactual.model.*;
import com.datawise.satisactual.repository.StoredProcedureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StoredProcedureService {

    @Autowired
    private StoredProcedureRepository repository;

    public List<CampaignDetails> findCampaign(String userId) {
        return StoredProcedureMapper.mapCampaigns(repository.findCampaign(userId));
    }

    public List<WaveDetails> findWaves(String campaignId) {
        return StoredProcedureMapper.mapWaves(repository.findWaves(campaignId));
    }
}
