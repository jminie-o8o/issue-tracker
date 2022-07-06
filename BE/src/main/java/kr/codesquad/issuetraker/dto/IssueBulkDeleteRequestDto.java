package kr.codesquad.issuetraker.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IssueBulkDeleteRequestDto {
    private List<Long> issueIds;
}
