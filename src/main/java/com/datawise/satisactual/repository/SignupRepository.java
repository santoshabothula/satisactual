package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface SignupRepository extends JpaRepository<UserMaster, String> {

    @Query(name = "signup.cod-user-role", nativeQuery = true)
    List<String> findCodeUserRole();

    @Query(name = "signup.dat-system-as-of", nativeQuery = true)
    List<Tuple> findDateSystemAsOf();

    @Query(name = "signup.code-menu-option", nativeQuery = true)
    String findMaxCodMenuOption();

    @Query(name = "signup.user-mail-id", nativeQuery = true)
    List<Tuple> findUserByMailId(String txtUserEmailId);

    @Query(name = "signup.third-party-id", nativeQuery = true)
    String findThirdPartyId(String txtThirdPartyName);

    @Query(name = "signup.user-messages", nativeQuery = true)
    String findUserMessages();

    @Query(name = "signup.admin-mail", nativeQuery = true)
    Tuple findAdminMail();

    @Query(name = "signup.third-party-mail", nativeQuery = true)
    Tuple findThirdPartyMail(BigInteger idParentCompany3rdParty);

}
