package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTemplateDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_training_type")
    private String codTrainingType;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_training_desc")
    private String trainingDesc;

	@JsonProperty("flg_cycle_linked")
    private FlagYesNo cycleLinked;
}
