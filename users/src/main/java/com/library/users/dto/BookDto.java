package com.library.users.dto;

import java.util.UUID;

public record BookDto(

    UUID id,
    String title,
    String author,
    String category,
    String isbn

) {

}
