package kr.codesquad.issuetraker.domain.issue;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.codesquad.issuetraker.dto.SearchFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static kr.codesquad.issuetraker.domain.issue.QIssue.issue;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class IssueRepositoryImpl implements IssueRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Issue> findIssuesByFilter(SearchFilterDto searchFilterDto) {

        return queryFactory.selectFrom(issue)
                .where(issue.isDeleted.eq(false),
                        likeTitle(searchFilterDto.getTitle()),
                        eqIsOpened(searchFilterDto.getIsOpened()),
                        eqAuthorId(searchFilterDto.getAuthorId()),
                        eqLabelId(searchFilterDto.getLabelId()),
                        eqMilestoneId(searchFilterDto.getMilestoneId()))
                .fetch();
    }

    private BooleanExpression likeTitle(String title) {
        if (Objects.isNull(title)) {
            return null;
        }
        return issue.title.like("%" + title + "%");
    }

    private BooleanExpression eqIsOpened(Boolean isOpened) {
        if (Objects.isNull(isOpened)) {
            return issue.isOpened.eq(true);
        }
        return issue.isOpened.eq(isOpened);
    }

    private BooleanExpression eqAuthorId(Long authorId) {
        if (Objects.isNull(authorId)) {
            return null;
        }
        return issue.author.id.eq(authorId);
    }

    private BooleanExpression eqLabelId(Long labelId) {
        if (Objects.isNull(labelId)) {
            return null;
        }
        return issue.label.id.eq(labelId);
    }

    private BooleanExpression eqMilestoneId(Long milestoneId) {
        if (Objects.isNull(milestoneId)) {
            return null;
        }
        return issue.milestone.id.eq(milestoneId);
    }
}
