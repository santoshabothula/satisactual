package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UploadFileRequest {

    @JsonProperty("id_entity")
    private String idEntity;

    @JsonProperty("id_campaign")
    private String idCampaign;

    @JsonProperty("id_campaign_wave")
    private String idCampaignWave;

    @JsonProperty("id_response")
    private String idResponse;

    @JsonProperty("id_question")
    private String idQuestion;

    @JsonProperty("cod_file_type")
    private String codFileType;

    @JsonProperty("cod_doc_type")
    private String codDocType;

    @JsonProperty("cod_img_type")
    private String codImgType;

    @JsonProperty("id_entity")
    private String id_entity;

    @JsonProperty("id_campaign")
    private String id_campaign;

    @JsonProperty("id_campaign_wave")
    private String id_campaign_wave;

    @JsonProperty("id_response")
    private String id_response;

    @JsonProperty("id_question")
    private String id_question;

    @JsonProperty("cod_file_type")
    private String cod_file_type;

    @JsonProperty("cod_doc_type")
    private String cod_doc_type;

    @JsonProperty("cod_img_type")
    private String cod_img_type;

    @JsonProperty("file")
    private MultipartFile file;

    public void setId_entity(String id_entity) {
        this.id_entity = id_entity;
        this.idEntity = id_entity;
    }

    public void setId_campaign(String id_campaign) {
        this.id_campaign = id_campaign;
        this.idCampaign = id_campaign;
    }

    public void setId_campaign_wave(String id_campaign_wave) {
        this.id_campaign_wave = id_campaign_wave;
        this.idCampaignWave = id_campaign_wave;
    }

    public void setId_response(String id_response) {
        this.id_response = id_response;
        this.idResponse = id_response;
    }

    public void setId_question(String id_question) {
        this.id_question = id_question;
        this.idQuestion = id_question;
    }

    public void setCod_file_type(String cod_file_type) {
        this.cod_file_type = cod_file_type;
        this.codFileType = cod_file_type;
    }

    public void setCod_doc_type(String cod_doc_type) {
        this.cod_doc_type = cod_doc_type;
        this.codDocType = cod_doc_type;
    }

    public void setCod_img_type(String cod_img_type) {
        this.cod_img_type = cod_img_type;
        this.codImgType = cod_img_type;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
