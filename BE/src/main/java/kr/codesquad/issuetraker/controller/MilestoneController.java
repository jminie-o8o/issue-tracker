package kr.codesquad.issuetraker.controller;

import kr.codesquad.issuetraker.dto.MilestoneListResponseDto;
import kr.codesquad.issuetraker.sevice.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping
    public ResponseEntity<List<MilestoneListResponseDto>> loadAllMilestones() {
        return ResponseEntity.ok(milestoneService.getAllMilestones());
    }
}
