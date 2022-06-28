package kr.codesquad.issuetraker.controller;

import kr.codesquad.issuetraker.dto.*;
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
    public ResponseEntity<MilestoneListResponseDto> loadAllMilestones() {
        return ResponseEntity.ok(milestoneService.getAllMilestones());
    }

    @PostMapping
    public ResponseEntity<MilestoneCreationResponseDto> createMilestone(@RequestBody MilestoneCreationRequestDto requestDto) {
        return ResponseEntity.ok(milestoneService.createMilestone(requestDto));
    }

    @GetMapping("/{milestoneId}")
    public ResponseEntity<MilestoneDetailResponseDto> loadMilestoneDetail(@PathVariable Long milestoneId) {
        return ResponseEntity.ok(milestoneService.getMilestoneDetail(milestoneId));
    }

    @PatchMapping("/{milestoneId}")
    public ResponseEntity<GeneralResponseDto> modifyMilestone(@PathVariable Long milestoneId, @RequestBody MilestoneModificationRequestDto requestDto) {
        return ResponseEntity.ok(milestoneService.modifyMilestone(milestoneId, requestDto));
    }

    @DeleteMapping("/{milestoneId}")
    public ResponseEntity<GeneralResponseDto> deleteMilestone(@PathVariable Long milestoneId) {
        return ResponseEntity.ok(milestoneService.deleteMilestone(milestoneId));
    }

}
