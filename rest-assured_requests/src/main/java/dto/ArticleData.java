package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record ArticleData(
        String slug,
        String title,
        String description,
        String body,
        List tagList,
        String createdAt,
        String updatedAt,
        String favorited,
        String favoritesCount,
        Author author
) {
}

