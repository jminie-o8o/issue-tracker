package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.label.Label;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelListElementDto {
    private Long labelId;
    private String name;
    private String description;
    private String backgroundColor;

    public static LabelListElementDto of(Label label) {
        return LabelListElementDto.builder()
                .labelId(label.getId())
                .name(label.getName())
                .description(label.getDescription())
                .backgroundColor(label.getBackgroundColor())
                .build();
    }
}
