package kr.codesquad.issuetraker.controller;

import kr.codesquad.issuetraker.dto.MilestoneCreationRequestDto;
import kr.codesquad.issuetraker.dto.MilestoneCreationResponseDto;
import kr.codesquad.issuetraker.dto.MilestoneListResponseDto;
import kr.codesquad.issuetraker.sevice.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping
    public ResponseEntity<List<MilestoneListResponseDto>> loadAllMilestones() {
        return ResponseEntity.ok(milestoneService.getAllMilestones());
    }

    @PostMapping
    public ResponseEntity<MilestoneCreationResponseDto> createMilestone(@RequestBody MilestoneCreationRequestDto requestDto) {
        return ResponseEntity.ok(milestoneService.createMilestone(requestDto));
    }

}
