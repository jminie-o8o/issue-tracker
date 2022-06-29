package kr.codesquad.issuetraker.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LabelListResponseDto {
    private List<LabelListElementDto> labels;

    public LabelListResponseDto(List<LabelListElementDto> labels) {
        this.labels = new ArrayList<>(labels);
    }
}
