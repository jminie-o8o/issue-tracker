package kr.codesquad.issuetraker.sevice;

import kr.codesquad.issuetraker.domain.milestone.Milestone;
import kr.codesquad.issuetraker.domain.milestone.MilestoneRepository;
import kr.codesquad.issuetraker.dto.MilestoneCreationRequestDto;
import kr.codesquad.issuetraker.dto.MilestoneCreationResponseDto;
import kr.codesquad.issuetraker.dto.MilestoneDetailResponseDto;
import kr.codesquad.issuetraker.dto.MilestoneListResponseDto;
import kr.codesquad.issuetraker.exception.MilestoneNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public List<MilestoneListResponseDto> getAllMilestones() {
        List<Milestone> milestones = milestoneRepository.findAll();
        return milestones.stream()
                .map(MilestoneListResponseDto::of)
                .collect(Collectors.toList());
    }

    public MilestoneCreationResponseDto createMilestone(MilestoneCreationRequestDto requestDto) {
        Milestone milestone = Milestone.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .dueDate(requestDto.getDueDate())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();

        Milestone savedMilestone = milestoneRepository.save(milestone);
        return new MilestoneCreationResponseDto(savedMilestone.getId());
    }

    public MilestoneDetailResponseDto getMilestoneDetail(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> new MilestoneNotFoundException());
        return MilestoneDetailResponseDto.of(milestone);
    }
}
