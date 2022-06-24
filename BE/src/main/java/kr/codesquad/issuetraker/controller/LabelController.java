package kr.codesquad.issuetraker.controller;

import kr.codesquad.issuetraker.dto.LabelCreationRequestDto;
import kr.codesquad.issuetraker.dto.LabelCreationResponseDto;
import kr.codesquad.issuetraker.dto.LabelListResponseDto;
import kr.codesquad.issuetraker.sevice.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    @GetMapping
    public ResponseEntity<List<LabelListResponseDto>> loadAllLabels() {
        return ResponseEntity.ok(labelService.getAllLabels());
    }

    @PostMapping
    public ResponseEntity<LabelCreationResponseDto> createLabel(@RequestBody LabelCreationRequestDto requestDto) {
        return ResponseEntity.ok(labelService.createLabel(requestDto));
    }
}
