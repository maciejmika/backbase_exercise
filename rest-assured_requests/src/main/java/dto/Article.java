package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.soabase.recordbuilder.core.RecordBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record Article(ArticleData article) {
}
