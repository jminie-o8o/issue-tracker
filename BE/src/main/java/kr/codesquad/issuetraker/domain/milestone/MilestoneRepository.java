package kr.codesquad.issuetraker.domain.milestone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    @Query(value = "SELECT m FROM Milestone m WHERE m.isDeleted = FALSE")
    public List<Milestone> findAllAvailableIssues();
}
