package com.datawise.satisactual.service;

import com.datawise.satisactual.entities.*;
import com.datawise.satisactual.exception.SatisActualProcessException;
import com.datawise.satisactual.model.SignupRequest;
import com.datawise.satisactual.repository.SignupRepository;
import com.datawise.satisactual.repository.UserMasterRepository;
import com.datawise.satisactual.repository.UserMessageRepository;
import com.datawise.satisactual.repository.UserXRolesRepository;
import com.datawise.satisactual.utils.Const;
import com.datawise.satisactual.utils.CryptoUtil;
import com.datawise.satisactual.utils.MailSenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class SignupService {

    @Autowired
    private SignupRepository signupRepository;
    @Autowired
    private UserMasterRepository userMasterRepository;
    @Autowired
    private UserXRolesRepository userXRolesRepository;
    @Autowired
    private MailSenderUtil mailSenderUtil;
    @Autowired
    private UserMessageRepository userMessageRepository;

    @Value("${application.scheme}")
    private String scheme;

    @Value("${application.server-name}")
    private String serverName;

    @Value("${application.server-port}")
    private String serverPort;

    @Value("${application.context-path}")
    private String contextPath;

    @Value("${application.welcome-mail.subject}")
    private String subject;

    @Value("${application.welcome-mail.security.subject}")
    private String securitySubject;

    @Value("${application.welcome-mail.third-party.subject}")
    private String thirdPartySubject;

    private String userMessage = Const.EMPTY;

    @Transactional
    public String signup(SignupRequest request) throws NoSuchAlgorithmException {
        String txtUserEmailId = Const.EMPTY;

        String codUserRole = findCodUserRole();
        Date dateSystemAsOf = findDateSystemAsOf();
        String codMenuOption = findMaxCodMenuOption();

        List<Tuple> rows = signupRepository.findUserByMailId(request.getEmail());
        if (Objects.nonNull(rows) && rows.size() > 0) {
            for(Tuple row: rows) {
                txtUserEmailId = String.valueOf(row.get(Const.LABEL_TXT_USER_EMAIL_ID));
            }
        }

        UserMessage message = null;
        if(Const.EMPTY.equalsIgnoreCase(txtUserEmailId)) {
            processNoExistingUser(request, codMenuOption, dateSystemAsOf, codUserRole);
            message = userMessageRepository.findByIdAndCodRecStatus(UserMessageEmbeddedKey.builder().numMessage(Const.MESSAGE_NUM_1142).codLanguage(Const.LANG_ENG_CODE).build(), Const.INDICATOR_A);
        } else {
            message = userMessageRepository.findByIdAndCodRecStatus(UserMessageEmbeddedKey.builder().numMessage(Const.MESSAGE_NUM_1111).codLanguage(Const.LANG_ENG_CODE).build(), Const.INDICATOR_A);
        }
        return (Objects.nonNull(message)) ? message.getTxtUserMessage() : Const.SIGNUP_ERROR_MESSAGE;
    }

    private void processNoExistingUser(SignupRequest request, String codMenuOption, Date dateSystemAsOf, String codUserRole) throws NoSuchAlgorithmException {
        UserMaster userMaster = saveUser(request, codMenuOption, dateSystemAsOf);
        UserXRoles userXRoles = UserXRoles.builder()
                .id(UserXRolesEmbeddedKey.builder().txtLoginId(userMaster.getTxtLoginId()).codUserRole(codUserRole).codRecStatus(userMaster.getCodRecStatus()).build())
                .txtLastMakerId(userMaster.getTxtLastCheckerId())
                .datLastMaker(new Date()).build();
        userXRolesRepository.save(userXRoles);
        sendUserMail(userMaster);
        sendAdminMail(userMaster);
    }

    private String findCodUserRole() {
        List<String> codUserRoles = signupRepository.findCodeUserRole();
        if (Objects.isNull(codUserRoles)) throw new SatisActualProcessException(Const.SIGNUP_ERROR_MSG_COD_USER_ROLE_NOT_FOUND);
        return codUserRoles.get(0);
    }

    private Date findDateSystemAsOf() {
        List<Tuple> rows = signupRepository.findDateSystemAsOf();
        if (Objects.isNull(rows)) throw new SatisActualProcessException(Const.SIGNUP_ERROR_MSG_SYSTEM_DATE_NOT_FOUND);

        Tuple row = rows.get(0);
        return (Date) row.get(Const.LABEL_DAT_SYSTEM_AS_OF_CAMEL_CASE);
    }

    private String findMaxCodMenuOption() {
        return signupRepository.findMaxCodMenuOption();
    }

    private UserMaster saveUser(SignupRequest request, String codMenuOption, Date dateSystemAsOf) throws NoSuchAlgorithmException {

        String thirdPartyId = signupRepository.findThirdPartyId(request.getOrganization());
        String companyId = StringUtils.hasText(thirdPartyId) ? thirdPartyId : Const.INDICATOR_MINUS_1;

        UserMaster userMaster = UserMaster.builder()
                .txtLoginId(request.getFirstName() + Const.UNDERSCORE + request.getLastName())
                .txtUserSignature(CryptoUtil.generatePassword(request.getFirstName(), request.getLastName()))
                .flgForcePasswdChg(Const.INDICATOR_N)
                .txtUserFname(request.getFirstName())
                .txtUserLname(request.getLastName())
                .txtUserEmailId(request.getEmail())
                .txtUserMobilePhone(request.getPhoneNumber())
                .idParentCompany3rdparty(BigInteger.valueOf(Long.parseLong(companyId)))
                .codHomeModule(Const.LABEL_SATI)
                .codHomeMenu(codMenuOption)
                .codHomeBranch(Const.LABEL_D_WISE)
                .datProfileCreated(dateSystemAsOf)
                .flgDisabled(Const.INDICATOR_N)
                .flgUserLoggedIn(Const.INDICATOR_N)
                .numTimeStartWkday(Integer.valueOf(Const.LABEL_0800))
                .numTimeEndWkday(Integer.valueOf(Const.LABEL_1700))
                .numTimeStartWkend(Const.INDICATOR_0)
                .numTimeEndWkend(Const.INDICATOR_0)
                .numTimeStartHoliday(Const.INDICATOR_0)
                .numTimeEndHoliday(Const.INDICATOR_0)
                .numFailedPwd(Const.INDICATOR_0)
                .datProfileExpiry(null)
                .codRecStatus(Const.INDICATOR_N)
                .txtLastMakerId(Const.LABEL_SYSTEM)
                .datLastMaker(dateSystemAsOf)
                .txtLastCheckerId(Const.LABEL_SYSTEM)
                .datLastChecker(dateSystemAsOf)
                .txtParentCompanyName(request.getOrganization()).build();
        return userMasterRepository.save(userMaster);
    }

    private void sendUserMail(UserMaster userMaster) {
        sendMail(userMaster.getTxtLoginId(), subject, userMaster.getTxtUserEmailId(), Const.PATH_EXT_INDEX + userMaster.getTxtLoginId());
    }

    private void sendAdminMail(UserMaster userMaster) {
        Tuple tuple = signupRepository.findAdminMail();
        if (Objects.isNull(tuple)) return;

        String mailId = String.valueOf(tuple.get(Const.LABEL_USER_EMAIL_ID));
        if (StringUtils.hasText(mailId)) {
            sendMail(userMaster.getTxtLoginId(), securitySubject, mailId, Const.PATH_EXT_AUTHORIZE);
            sendThirdPartyMail(userMaster, mailId);
        }
    }

    private void sendThirdPartyMail(UserMaster userMaster, String adminMailId) {
        Tuple tuple = signupRepository.findThirdPartyMail(userMaster.getIdParentCompany3rdparty());
        if (Objects.isNull(tuple)) return;

        String mailId = String.valueOf(tuple.get(Const.LABEL_USER_EMAIL_ID));
        if (!StringUtils.hasText(mailId)) {
            mailId = adminMailId;
        }
        sendMail(userMaster.getTxtLoginId(), thirdPartySubject, mailId, Const.PATH_EXT_AUTHORIZE);
    }

    private void sendMail(String userId, String subject, String mailTo, String extPath) {
        if (!StringUtils.hasText(userMessage)) userMessage = signupRepository.findUserMessages();

        if (StringUtils.hasText(userMessage)) {
            userMessage = userMessage.replace(Const.AND, userId);
            userMessage = userMessage +"<br>"+ scheme + "://" + serverName + Const.COLON + serverPort + contextPath + extPath;
            Map<String, Object> props = new HashMap<>();
            props.put(Const.MAIL_TEMPLATE_PARAM_MESSAGE, userMessage);
            mailSenderUtil.sendMail(Const.MAIL_TEMPLATE_WELCOME_SIGNUP, mailTo, subject, props);
        }
    }
}
