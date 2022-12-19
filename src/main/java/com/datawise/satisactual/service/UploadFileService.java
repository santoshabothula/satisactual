package com.datawise.satisactual.service;

import com.datawise.satisactual.model.ApplicationConfigDetails;
import com.datawise.satisactual.model.UploadFileRequest;
import com.datawise.satisactual.utils.Const;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class UploadFileService {

    @Autowired
    private ApplicationConfigDetails configDetails;

    @Value("${application.upload-file-path}")
    private String dataDirectory;

    public JSONObject uploadFile(UploadFileRequest request, MultipartFile file) {
        JSONObject response = new JSONObject();
        String destinationFileName = Const.EMPTY;
        String imgIPAddress = configDetails.getImgIPAddress();
        String docUploadPath = configDetails.getDocUploadPath();
        String AVRecordingsPath = configDetails.getAVRecordingsPath();

        switch (request.getCodFileType()) {
            case "RESP":
                dataDirectory = File.separator + AVRecordingsPath + File.separator + request.getIdCampaign() + File.separator + request.getIdCampaignWave();
                break;
            case "CUST":
                dataDirectory = File.separator + imgIPAddress + File.separator + request.getCodFileType() + File.separator + request.getCodImgType();
                break;
            default:
                dataDirectory = File.separator + docUploadPath + File.separator + request.getCodFileType() + File.separator + request.getCodDocType();
                break;
        }

        if (Objects.nonNull(file)) {
            String sourceFileName = file.getOriginalFilename();
            String fileExtension = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1);
            File fileSaveDir = new File(dataDirectory);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }

            String filePath = dataDirectory + File.separator;
            File uploadedFile = new File(filePath + request.getIdEntity().trim() + Const.DOT + fileExtension);
            if (request.getCodFileType().equals("RESP")) {
                uploadedFile = new File(filePath + request.getIdResponse().trim() + "_" + request.getIdQuestion().trim() +"."+fileExtension);
            }

            try {
                Path path = Paths.get(uploadedFile.toURI());
                Files.write(path, file.getBytes());
                destinationFileName = uploadedFile.getCanonicalPath();
            } catch (IOException ex) {
                response(response, Const.INDICATOR_0, "Failed to upload Files", Const.EMPTY);
                return response;
            }
        }
        response(response, Const.INDICATOR_1, "File Uploaded Successfully", destinationFileName);
        return response;
    }

    private void response(JSONObject res, Integer code, String message, String destinationFileName) {
        res.put("response_code", code);
        res.put("response_message", message);
        res.put("txt_file_path", destinationFileName);
    }
}
