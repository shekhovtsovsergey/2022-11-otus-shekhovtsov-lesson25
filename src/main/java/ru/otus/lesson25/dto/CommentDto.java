package ru.otus.lesson25.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String book;
    private String authorName;
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() && getClass() != o.getClass().getSuperclass()) return false;
        CommentDto commentDto = (CommentDto) o;
        return id.equals(commentDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,book,authorName,comment);
    }


}
