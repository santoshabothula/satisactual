select l.id_question, l.cod_language, l.cod_option, l.txt_option_text, l.txt_additional_text_prompt, ifnull(qqo.flg_exclude_option, 'N') as flg_exclude_option, qq.id_questionnaire
from cmp_question_options_lang l left outer join cmp_questionnaire_question_options qqo on qqo.id_questionnaire = :id_questionnaire
and  l.id_question = qqo.id_question and l.cod_option = qqo.cod_option, cmp_questionnaire_questions qq
where l.id_question = qq.id_question
and   qq.cod_rec_status = 'A'
and   qq.id_questionnaire = :id_questionnaire
order by qq.id_questionnaire, qq.id_question, l.cod_option, l.cod_language