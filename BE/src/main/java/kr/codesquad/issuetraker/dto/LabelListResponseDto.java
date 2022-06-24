package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.label.Label;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelListResponseDto {
    private Long labelId;
    private String name;
    private String description;
    private String backgroundColor;

    public static LabelListResponseDto of(Label label) {
        return LabelListResponseDto.builder()
                .labelId(label.getId())
                .name(label.getName())
                .description(label.getDescription())
                .backgroundColor(label.getBackgroundColor())
                .build();
    }
}
