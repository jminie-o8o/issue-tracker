package kr.codesquad.issuetraker.domain.issue;

import kr.codesquad.issuetraker.domain.BaseTimeEntity;
import kr.codesquad.issuetraker.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    private String content;
    private boolean isDeleted;

    public Comment(Issue issue, User author, String content) {
        this.issue = issue;
        this.author = author;
        this.content = content;
    }

    public void modifyContent(String content) {
        this.content = content;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }
}
