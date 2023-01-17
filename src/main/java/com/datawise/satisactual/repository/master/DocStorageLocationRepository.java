package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DocStorageLocationEmbeddedKey;
import com.datawise.satisactual.entities.master.DocStorageLocationEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocStorageLocationRepository extends IBaseRepository<DocStorageLocationEntity, DocStorageLocationEmbeddedKey> {}
