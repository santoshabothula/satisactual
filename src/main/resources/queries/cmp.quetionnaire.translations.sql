select l.id_question, l.cod_language, l.txt_question_text, l.txt_question_helptext, l.txt_telecaller_prompt, l.txt_addl_comments_prompt, l.txt_high_rating_label, l.txt_low_rating_label , qq.id_questionnaire
from cmp_questions_lang l, cmp_questionnaire_questions qq
where l.id_question = qq.id_question
and l.cod_rec_status = 'A'
and qq.cod_rec_status = 'A'
and qq.id_questionnaire = :id_questionnaire
order by qq.id_questionnaire, qq.id_question, l.cod_language