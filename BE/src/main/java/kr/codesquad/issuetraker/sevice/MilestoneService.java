package kr.codesquad.issuetraker.sevice;

import kr.codesquad.issuetraker.domain.milestone.Milestone;
import kr.codesquad.issuetraker.domain.milestone.MilestoneRepository;
import kr.codesquad.issuetraker.dto.MilestoneListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
