package com.mysite.BeBeeKeepingGreen.record;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordForm {
    @NotEmpty(message="내용을 적어주세요")
    private String content;
}
