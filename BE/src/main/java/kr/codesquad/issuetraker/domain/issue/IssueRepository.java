package kr.codesquad.issuetraker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>, IssueRepositoryCustom {
    @Query(value = "SELECT i FROM Issue i where i.isDeleted = FALSE")
    public List<Issue> findAllAvailableIssues();
}
