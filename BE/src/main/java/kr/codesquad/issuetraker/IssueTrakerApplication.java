package kr.codesquad.issuetraker;

import kr.codesquad.issuetraker.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"application.yml", "secret.yml"}, factory = YamlPropertySourceFactory.class)
public class IssueTrakerApplication {
	public static void main(String[] args) {
		SpringApplication.run(IssueTrakerApplication.class, args);
	}
}
