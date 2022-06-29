package kr.codesquad.issuetraker.domain.milestone;

import kr.codesquad.issuetraker.domain.BaseTimeEntity;
import kr.codesquad.issuetraker.domain.issue.Issue;
import kr.codesquad.issuetraker.domain.user.User;
import kr.codesquad.issuetraker.dto.MilestoneModificationRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Milestone extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long id;
    private String title;
    private String description;
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "milestone")
    private final List<Issue> issues = new ArrayList<>();
    private LocalDate dueDate;
    private boolean isDeleted;

    public List<Issue> getIssues() {
        return new ArrayList<>(issues);
    }

    public void modifyContentsWith(MilestoneModificationRequestDto requestDto) {
        title = requestDto.getTitle();
        description = requestDto.getDescription();
        dueDate = requestDto.getDueDate();
    }

    public void markAsDeleted() {
        isDeleted = true;
    }
}
