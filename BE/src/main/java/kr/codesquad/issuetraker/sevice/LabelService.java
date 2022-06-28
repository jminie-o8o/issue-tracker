package kr.codesquad.issuetraker.sevice;

import kr.codesquad.issuetraker.domain.label.Label;
import kr.codesquad.issuetraker.domain.label.LabelRepository;
import kr.codesquad.issuetraker.dto.LabelCreationRequestDto;
import kr.codesquad.issuetraker.dto.LabelCreationResponseDto;
import kr.codesquad.issuetraker.dto.LabelListElementDto;
import kr.codesquad.issuetraker.dto.LabelListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelListResponseDto getAllLabels() {
        List<Label> labels = labelRepository.findAll();
        List<LabelListElementDto> labelElements = labels.stream()
                .map(LabelListElementDto::of)
                .collect(Collectors.toList());
        return new LabelListResponseDto(labelElements);
    }

    public LabelCreationResponseDto createLabel(LabelCreationRequestDto requestDto) {
        Label label = Label.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .backgroundColor(requestDto.getBackgroundColor())
                .build();
        Label savedLabel = labelRepository.save(label);
        return new LabelCreationResponseDto(savedLabel.getId());
    }
}
