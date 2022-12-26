package com.datawise.satisactual.service;

import com.datawise.satisactual.entities.UserMaster;
import com.datawise.satisactual.exception.SatisActualProcessException;
import com.datawise.satisactual.repository.UserMasterRepository;
import com.datawise.satisactual.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class LogoutService implements LogoutHandler {

    @Autowired
    private UserMasterRepository repository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<UserMaster> optionalUserMaster = repository.findById(request.getParameter("txt_login_id"));
        if (optionalUserMaster.isEmpty()) throw new SatisActualProcessException("Unauthorized");

        UserMaster userMaster = optionalUserMaster.get();
        userMaster.setFlgUserLoggedIn(Const.INDICATOR_N);
        repository.save(userMaster);
    }
}
