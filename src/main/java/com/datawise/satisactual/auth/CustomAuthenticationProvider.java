package com.datawise.satisactual.auth;

import com.datawise.satisactual.entities.UserMaster;
import com.datawise.satisactual.repository.LoginVerificationRepository;
import com.datawise.satisactual.repository.UserMasterRepository;
import com.datawise.satisactual.utils.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserMasterRepository repository;
    @Autowired
    private LoginVerificationRepository loginVerificationRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String signature = CryptoUtil.getEncryptedPassword(authentication.getCredentials().toString());

        Optional<UserMaster> userMaster = repository.findById(username);
        if (userMaster.isEmpty()) {
            throw new BadCredentialsException("Details not found");
        }

        if (!userMaster.get().getTxtUserSignature().equals(signature)) {
            loginVerificationRepository.updateUserLoginFailed(userMaster.get().getNumFailedPwd() + 1, username);
            throw new BadCredentialsException("Password mismatch");
        }

        return new UsernamePasswordAuthenticationToken(username, signature, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public List<GrantedAuthority> getStudentRoles(String studentRoles) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        String[] roles = studentRoles.split(",");
        for (String role : roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.replaceAll("\\s+", "")));
        }
        return grantedAuthorityList;
    }
}
