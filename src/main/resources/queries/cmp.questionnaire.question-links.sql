select l.id_question, l.cod_option, l.id_linked_question, l.enu_link_type
from cmp_questionnaire_question_links l left outer join cmp_question_options o on l.id_question = o.id_question and l.cod_option = o.cod_option
where l.id_questionnaire = :id_questionnaire
order by l.id_question, ifnull(o.num_display_sequence, 9999), l.cod_option, l.id_linked_question