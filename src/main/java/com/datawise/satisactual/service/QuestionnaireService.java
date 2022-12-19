package com.datawise.satisactual.service;

import com.datawise.satisactual.repository.StoredProcedureRepository;
import com.datawise.satisactual.repository.UserMasterRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import static java.util.stream.Collectors.groupingBy;

@Service
public class QuestionnaireService {

    @Autowired
    private UserMasterRepository userMasterRepository;
    @Autowired
    private StoredProcedureRepository storedProcedureRepository;

    public JSONArray getQuestionnaire(String pTxtCurrUser, String pidQuestionnaire, String pCodLanguage) {

        int questionId;

        if (!StringUtils.hasText(pCodLanguage)) pCodLanguage = "ENG";

        JSONArray questionnaireArray = new JSONArray();
        if ("-1".equals(pidQuestionnaire)) {
            questionId = Integer.parseInt(String.valueOf(storedProcedureRepository.findQuestionnaires(pTxtCurrUser).get("id_questionnaire")));
        } else {
            questionId = userMasterRepository.findIdQuestionnaire(pidQuestionnaire);
        }

        List<Tuple> links = userMasterRepository.findQuestionnaireQuestionLinks(questionId);
        List<Tuple> translations = userMasterRepository.findQuestionnaireTranslations(questionId);
        List<Tuple> transOptions = userMasterRepository.findQuestionnaireTransOptions(questionId);

        List<Tuple> result = storedProcedureRepository.findQuestionnaireByLang(questionId, pCodLanguage);

        if (CollectionUtils.isEmpty(result)) return questionnaireArray;

        JSONObject questionnaire = new JSONObject();
        Tuple tuple = result.get(0);
        questionnaire(questionnaire, tuple);

        Map<String, List<Tuple>> map = result.stream().collect(groupingBy(t -> String.valueOf(t.get("id_question"))));
        JSONArray questions = new JSONArray();
        map.forEach((k,v) -> {
            JSONObject question = questions(tuple);
            JSONArray qLanguages = new JSONArray();
            JSONArray qLinks = new JSONArray();
            JSONArray qOptions = new JSONArray();
            v.forEach(t -> {
                translations.stream()
                        .filter(lang ->
                                String.valueOf(lang.get("id_questionnaire")).equals(String.valueOf(t.get("id_questionnaire"))) &&
                                String.valueOf(lang.get("id_question")).equals(String.valueOf(t.get("id_question")))
                        )
                        .forEach(lang -> qLanguages.put(this.languages(lang)));

                links.stream()
                        .filter(link -> String.valueOf(link.get("id_question")).equals(String.valueOf(t.get("id_question"))))
                        .forEach(link -> qLinks.put(links(link)));

                if (StringUtils.hasText(String.valueOf(t.get("cod_option")))) {
                    qOptions.put(options(t, transOptions));
                }
            });
            question.put("Options", qOptions);
            question.put("Languages", qLanguages);
            question.put("Links", qLinks);
            questions.put(question);
        });
        questionnaire.put("Questions", questions);
        questionnaireArray.put(questionnaire);
        return questionnaireArray;
    }

    private void questionnaire(JSONObject questionnaire, Tuple tuple) {
        questionnaire.put("id_questionnaire", tuple.get("id_questionnaire"));
        questionnaire.put("txt_questionnaire_name", tuple.get("txt_questionnaire_name"));
        questionnaire.put("flg_assessment", tuple.get("flg_assessment"));
        questionnaire.put("flg_randomize", tuple.get("flg_randomize"));
        questionnaire.put("flg_show_score_on_submit", tuple.get("flg_show_score_on_submit"));
        questionnaire.put("num_max_minutes_to_respond", tuple.get("num_max_minutes_to_respond"));
        questionnaire.put("num_pass_score", tuple.get("num_pass_score"));
        questionnaire.put("cod_css_theme", tuple.get("cod_css_theme"));
        questionnaire.put("Labels", labels(tuple));
    }

    private JSONObject labels(Tuple tuple) {
        JSONObject label = new JSONObject();
        label.put("txt_comments_label", tuple.get("txt_comments_label"));
        label.put("txt_start_label", tuple.get("txt_start_label"));
        label.put("txt_resume_label", tuple.get("txt_resume_label"));
        label.put("txt_submit_label", tuple.get("txt_submit_label"));
        label.put("txt_exit_label", tuple.get("txt_exit_label"));
        label.put("txt_save_and_return_label", tuple.get("txt_save_and_return_label"));
        label.put("txt_nextpage_label", tuple.get("txt_nextpage_label"));
        label.put("txt_prevpage_label", tuple.get("txt_prevpage_label"));
        label.put("txt_firstpage_label", tuple.get("txt_firstpage_label"));
        label.put("txt_lastpage_label", tuple.get("txt_lastpage_label"));
        label.put("txt_thankyou_label", tuple.get("txt_thankyou_label"));
        label.put("txt_sorry_label", tuple.get("txt_sorry_label"));
        label.put("txt_clickhere_label", tuple.get("txt_clickhere_label"));
        label.put("txt_surveylink_label", tuple.get("txt_surveylink_label"));
        label.put("txt_review_response_label", tuple.get("txt_review_response_label"));
        label.put("txt_edit_response_label", tuple.get("txt_edit_response_label"));
        label.put("txt_confirm_response_label", tuple.get("txt_confirm_response_label"));
        return label;
    }

    private JSONObject questions(Tuple tuple) {
        JSONObject questions = new JSONObject();
        questions.put("num_question_page", tuple.get("num_question_page"));
        questions.put("num_question_sequence", tuple.get("num_question_sequence"));
        questions.put("id_question", tuple.get("id_question"));
        questions.put("txt_question_num", tuple.get("txt_question_num"));
        questions.put("txt_question_helptext", tuple.get("txt_question_helptext"));
        questions.put("flg_mandatory", tuple.get("flg_mandatory"));
        questions.put("flg_group_header", tuple.get("flg_group_header"));
        questions.put("num_group_children", tuple.get("num_group_children"));
        questions.put("num_options", tuple.get("num_options"));
        questions.put("flg_has_dependents", tuple.get("flg_has_dependents"));
        questions.put("num_question_group", tuple.get("num_question_group"));
        questions.put("txt_question_text", tuple.get("txt_question_text"));
        questions.put("txt_telecaller_prompt", tuple.get("txt_telecaller_prompt"));
        questions.put("txt_addl_comments_prompt", tuple.get("txt_addl_comments_prompt"));
        questions.put("cod_question_type", tuple.get("cod_question_type"));
        questions.put("enu_base_question_type", tuple.get("enu_base_question_type"));
        questions.put("flg_av_recording_reqd", tuple.get("flg_av_recording_reqd"));
        questions.put("num_rating_levels", tuple.get("num_rating_levels"));
        questions.put("num_days_earliest", tuple.get("num_days_earliest"));
        questions.put("num_days_latest", tuple.get("num_days_latest"));
        questions.put("num_value_min", tuple.get("num_value_min"));
        questions.put("num_value_max", tuple.get("num_value_max"));
        questions.put("enu_display_type", tuple.get("enu_display_type"));
        questions.put("flg_rating_one_highest", tuple.get("flg_rating_one_highest"));
        questions.put("txt_high_rating_label", tuple.get("txt_high_rating_label"));
        questions.put("txt_low_rating_label", tuple.get("txt_low_rating_label"));
        questions.put("id_prerequisite_question", tuple.get("id_prerequisite_question"));
        questions.put("cod_prerequisite_options", tuple.get("cod_prerequisite_options"));
        questions.put("flg_qualifying_question", tuple.get("flg_qualifying_question"));
        questions.put("flg_hide_question", tuple.get("flg_hide_question"));
        questions.put("flg_qualify_if_yes", tuple.get("flg_qualify_if_yes"));
        questions.put("id_redirect_if_yes", tuple.get("id_redirect_if_yes"));
        questions.put("id_redirect_if_no", tuple.get("id_redirect_if_no"));
        questions.put("txt_response_format", tuple.get("txt_response_format"));
        questions.put("num_max_chars_response", tuple.get("num_max_chars_response"));
        questions.put("num_min_options_reqd", tuple.get("num_min_options_reqd"));
        questions.put("num_max_options_allowed", tuple.get("num_max_options_allowed"));
        questions.put("flg_bold", tuple.get("flg_bold"));
        questions.put("flg_italic", tuple.get("flg_italic"));
        questions.put("flg_underline", tuple.get("flg_underline"));
        questions.put("num_font_size", tuple.get("num_font_size"));
        questions.put("txt_font_name", tuple.get("txt_font_name"));
        questions.put("num_rating_addl_text_threshold", tuple.get("num_rating_addl_text_threshold"));
        questions.put("num_rating_escalation_threshold", tuple.get("num_rating_escalation_threshold"));
        return questions;
    }

    private JSONObject languages(Tuple tuple) {
        JSONObject lang = new JSONObject();
        lang.put("id_questionnaire", tuple.get("id_questionnaire"));
        lang.put("id_question", tuple.get("id_question"));
        lang.put("cod_language", tuple.get("cod_language"));
        lang.put("txt_question_text", tuple.get("txt_question_text"));
        lang.put("txt_question_helptext", tuple.get("txt_question_helptext"));
        lang.put("txt_telecaller_prompt", tuple.get("txt_telecaller_prompt"));
        lang.put("txt_addl_comments_prompt", tuple.get("txt_addl_comments_prompt"));
        lang.put("txt_high_rating_label", tuple.get("txt_high_rating_label"));
        lang.put("txt_low_rating_label", tuple.get("txt_low_rating_label"));
        return lang;
    }

    private JSONObject links(Tuple tuple) {
        JSONObject link = new JSONObject();
        link.put("id_question", tuple.get("id_question"));
        link.put("cod_option", tuple.get("cod_option"));
        link.put("id_linked_question", tuple.get("id_linked_question"));
        link.put("enu_link_type", tuple.get("enu_link_type"));
        return link;
    }

    private JSONObject options(Tuple tuple, List<Tuple> transOptions) {
        JSONObject option = new JSONObject();
        option.put("cod_option", tuple.get("cod_option"));
        option.put("txt_option_text", tuple.get("txt_option_text"));
        option.put("num_display_sequence", tuple.get("num_display_sequence"));
        option.put("flg_capture_addl_text", tuple.get("flg_capture_addl_text"));
        option.put("txt_additional_text_prompt", tuple.get("txt_additional_text_prompt"));
        option.put("id_parent_question", tuple.get("id_parent_question"));
        option.put("cod_parent_question_option", tuple.get("cod_parent_question_option"));
        option.put("id_skip_to_question", tuple.get("id_skip_to_question"));
        option.put("flg_qualifying_response", tuple.get("flg_qualifying_response"));
        option.put("num_answer_weight", tuple.get("num_answer_weight"));
        option.put("num_contact_list_col", tuple.get("num_contact_list_col"));
        option.put("flg_escalation_reqd", tuple.get("flg_escalation_reqd"));
        option.put("flg_default_response", tuple.get("flg_default_response"));

        JSONArray optionLang = transOptions.stream()
                .filter(opt ->
                        String.valueOf(opt.get("cod_option")).equals(String.valueOf(tuple.get("cod_option"))) &&
                                String.valueOf(opt.get("cod_option")).equals("N") &&
                                String.valueOf(opt.get("id_question")).equals(String.valueOf(tuple.get("id_question"))) &&
                                String.valueOf(opt.get("id_questionnaire")).equals(String.valueOf(tuple.get("id_questionnaire")))
                ).map(this::optionLang).collect(Collector.of(JSONArray::new, JSONArray::put, JSONArray::put));
        option.put("OptionLanguage", optionLang);
        return option;
    }

    private JSONObject optionLang(Tuple tuple) {
        JSONObject optionLang = new JSONObject();
        optionLang.put("cod_option", tuple.get("cod_option"));
        optionLang.put("cod_language", tuple.get("cod_language"));
        optionLang.put("txt_option_text", tuple.get("txt_option_text"));
        optionLang.put("txt_additional_text_prompt", tuple.get("txt_additional_text_prompt"));
        return optionLang;
    }
}
