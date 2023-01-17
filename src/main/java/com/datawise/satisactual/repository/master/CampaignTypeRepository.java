package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CampaignTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CampaignTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignTypeRepository extends IBaseRepository<CampaignTypeEntity, CampaignTypeEmbeddedKey> {}
