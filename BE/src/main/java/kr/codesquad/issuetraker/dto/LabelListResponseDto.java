package kr.codesquad.issuetraker.dto;

import java.util.ArrayList;
import java.util.List;

public class LabelListResponseDto {
    private List<LabelListElementDto> labels;

    public LabelListResponseDto(List<LabelListElementDto> labels) {
        this.labels = new ArrayList<>(labels);
    }
}
