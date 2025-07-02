package miniprojectver.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum BookStatus {
    DRAFT,
    EDITED,
    PUBLISHED,
    ARCHIVED,
    PENDING
}
