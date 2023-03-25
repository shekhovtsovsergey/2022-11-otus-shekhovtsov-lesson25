package ru.otus.lesson25.dto;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String name;
    private Long author;
    private Long genre;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() && getClass() != o.getClass().getSuperclass()) return false;
        BookDto bookDto = (BookDto) o;
        return id.equals(bookDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name,author,genre);
    }


}
