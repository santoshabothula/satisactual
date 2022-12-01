package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.UserXRoles;
import com.datawise.satisactual.entities.UserXRolesEmbeddedKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserXRolesRepository extends JpaRepository<UserXRoles, UserXRolesEmbeddedKey> {}
