package kr.codesquad.issuetraker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class KakaoAccessTokenResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
}
