package kr.codesquad.issuetraker.sevice;

import kr.codesquad.issuetraker.domain.milestone.Milestone;
import kr.codesquad.issuetraker.domain.milestone.MilestoneRepository;
import kr.codesquad.issuetraker.dto.*;
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

    public MilestoneListResponseDto getAllMilestones() {
        List<Milestone> milestones = milestoneRepository.findAllAvailableIssues();
        List<MilestoneListElementDto> milestoneElements = milestones.stream()
                .map(MilestoneListElementDto::of)
                .collect(Collectors.toList());
        return new MilestoneListResponseDto(milestoneElements);
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

    public GeneralResponseDto modifyMilestone(Long milestoneId, MilestoneModificationRequestDto requestDto) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> new MilestoneNotFoundException());
        milestone.modifyContentsWith(requestDto);
        milestoneRepository.save(milestone);
        return new GeneralResponseDto(200, "마일스톤이 성공적으로 수정되었습니다.");
    }

    public GeneralResponseDto deleteMilestone(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> new MilestoneNotFoundException());
        milestone.markAsDeleted();
        milestoneRepository.save(milestone);
        return new GeneralResponseDto(200, "마일스톤이 성공적으로 삭제되었습니다.");
    }
}
