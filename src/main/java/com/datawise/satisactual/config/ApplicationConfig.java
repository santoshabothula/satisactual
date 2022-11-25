package com.datawise.satisactual.config;

import com.datawise.satisactual.model.ApplicationConfigDetails;
import com.datawise.satisactual.utils.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Value("${application.access-details}")
    private String encryptedEntities;

    @Bean
    public ApplicationConfigDetails loadAccessDetails() {
        CryptoUtil cu = new CryptoUtil();
        ApplicationConfigDetails configDetails = new ApplicationConfigDetails();

        try {
            configDetails.setDBIPAddress(cu.decrypt((encryptedEntities.split(","))[0]));
            configDetails.setDBUsername(cu.decrypt((encryptedEntities.split(","))[1]));
            configDetails.setDBPassword(cu.decrypt((encryptedEntities.split(","))[2]));

            configDetails.setRptIPAddress(cu.decrypt((encryptedEntities.split(","))[3]));
            configDetails.setImgIPAddress(cu.decrypt((encryptedEntities.split(","))[4]));
            configDetails.setInactiveTimeout(cu.decrypt((encryptedEntities.split(","))[5]));
            configDetails.setFailedPwdLimit(cu.decrypt((encryptedEntities.split(","))[6]));
            configDetails.setAlertsEMailID(cu.decrypt((encryptedEntities.split(","))[7]));
            configDetails.setAlertsEMailPWD(cu.decrypt((encryptedEntities.split(","))[8]));
            configDetails.setAlertsEMailProtocol(cu.decrypt((encryptedEntities.split(","))[9]));
            configDetails.setAlertsEMailClient(cu.decrypt((encryptedEntities.split(","))[10]));
            configDetails.setAlertsEMailPort(cu.decrypt((encryptedEntities.split(","))[11]));
            configDetails.setAlertsEMailSSL(cu.decrypt((encryptedEntities.split(","))[12]));
            configDetails.setDSN(cu.decrypt((encryptedEntities.split(","))[13]));
            configDetails.setDocUploadPath(cu.decrypt((encryptedEntities.split(","))[14]));
            configDetails.setAVRecordingsPath(cu.decrypt((encryptedEntities.split(","))[15]));
            configDetails.setGoogleTranslateKey(cu.decrypt((encryptedEntities.split(","))[16]));
        } catch (Exception ex) {
            log.error("Error reading Accessdetails.txt", ex);
        }
        return configDetails;
    }

}
