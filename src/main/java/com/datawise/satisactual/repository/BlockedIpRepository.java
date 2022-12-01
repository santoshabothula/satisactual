package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.BlockedIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedIpRepository extends JpaRepository<BlockedIp, String> {}
