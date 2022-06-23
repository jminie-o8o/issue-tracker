package kr.codesquad.issuetraker.controller;

import kr.codesquad.issuetraker.dto.*;
import kr.codesquad.issuetraker.sevice.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;

    @GetMapping
    public ResponseEntity<List<IssueListResponseDto>> loadAllIssues(@ModelAttribute SearchFilterDto searchFilterDto) {
        return ResponseEntity.ok(issueService.getAllIssues(searchFilterDto));
    }

    @PostMapping
    public ResponseEntity<NewIssueResponseDto> createIssue(@RequestBody NewIssueRequestDto requestDto) {
        return ResponseEntity.ok(issueService.createIssue(requestDto));
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDetailResponseDto> loadIssueDetails(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.getIssueDetail(issueId));
    }

    @PatchMapping("/{issueId}")
    public ResponseEntity<GeneralResponseDto> modifyIssueContent(@PathVariable Long issueId, @RequestBody IssueModificationRequestDto requestDto) {
        return ResponseEntity.ok(issueService.modifyIssueContent(issueId, requestDto));
    }

    @PatchMapping("/{issueId}/status")
    public ResponseEntity<GeneralResponseDto> changeIssueStatus(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.changeIssueStatus(issueId));
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<GeneralResponseDto> deleteIssue(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.deleteIssue(issueId));
    }

    @GetMapping("/{issueId}/comments")
    public ResponseEntity<List<CommentListResponseDto>> loadAllComments(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.getAllComments(issueId));
    }

    @PostMapping("/{issueId}/comments")
    public ResponseEntity<NewCommentResponseDto> createComment(@PathVariable Long issueId, @RequestBody NewCommentRequestDto requestDto) {
        return ResponseEntity.ok(issueService.createComment(issueId, requestDto));
    }
}
