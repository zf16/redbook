package com.chuwa.chuwa.controller;

import com.chuwa.chuwa.payload.AuthorDto;
import com.chuwa.chuwa.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorGraphQLController {
    private final AuthorService authorService;

    public AuthorGraphQLController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public AuthorDto authorById(@Argument long id) {
        return authorService.getAuthorById(id);
    }

    @MutationMapping
    public AuthorDto createAuthor(@Argument String name) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(name);

        return authorService.createAuthor(authorDto);
    }

}
