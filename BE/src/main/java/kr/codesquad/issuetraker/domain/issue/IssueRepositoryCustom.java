package kr.codesquad.issuetraker.domain.issue;

import kr.codesquad.issuetraker.dto.SearchFilterDto;

import java.util.List;

public interface IssueRepositoryCustom {
    List<Issue> findIssuesByFilter(SearchFilterDto searchFilterDto);

}
