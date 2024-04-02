package com.chuwa.chuwa.service;

import com.chuwa.chuwa.payload.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(long id);

    AuthorDto updateAuthor(AuthorDto authorDto, long id);

    void deleteAuthorById(long id);
}
