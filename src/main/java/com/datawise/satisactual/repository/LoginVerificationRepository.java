package com.datawise.satisactual.repository;

import com.datawise.satisactual.custom.models.*;
import com.datawise.satisactual.entities.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LoginVerificationRepository extends JpaRepository<UserMaster, String> {

    @Query(name = "user.login-details", nativeQuery = true)
    UserLoginDetails findByCodRecStatusAndTxtLoginIdAndTxtUserSignature(Integer numFailedPwd, String userId, String userSignature);

    @Query(name = "user.last-login", nativeQuery = true)
    LastLoginDetails findLastLogin(String txtLoginId);

    @Query(name = "user.sys-install-modules", nativeQuery = true)
    UserSysInstallModuleDetails findUserSysInstallModules(String codeModule);

    @Query(name = "user.menu-params", nativeQuery = true)
    MenuParamDetails findUserMenuParams(String codMenuOption, String txtLoginId);

    @Query(name = "user.span-of-control", nativeQuery = true)
    List<UserSpanOfControlDetails> findUserSpanOfControls(String txtLoginId);

    @Query(name = "user.managed", nativeQuery = true)
    List<UserSpanOfControlDetails> findUserManaged(String txtLoginId);

    @Query(name = "user.centers-managed", nativeQuery = true)
    List<UserSpanOfControlDetails> findUserCentersManaged(String txtLoginId);

    @Query(name = "user.messages", nativeQuery = true)
    List<UserMessageDetails> findUserMessages(String lang);

    @Modifying
    @Transactional
    @Query(name = "user.update-login-success", nativeQuery = true)
    void updateUserLoginSuccess(String txtLoginId);

    @Modifying
    @Transactional
    @Query(name = "user.update-login-failed", nativeQuery = true)
    void updateUserLoginFailed(Integer numFailedPwd, String txtLoginId);

    @Modifying
    @Transactional
    @Query(name = "user.log.access.request", nativeQuery = true)
    void logUserAccessRequest(String txtLoginId, String flgSuccess, String txtLoginFailReason, String txtIpAddressSource, String txtBrowserUsed, String txtOsUsed, String txtUserAgentString);

}
