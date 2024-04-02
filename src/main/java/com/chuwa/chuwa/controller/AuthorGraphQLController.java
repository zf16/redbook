package com.chuwa.chuwa.controller;

import com.chuwa.chuwa.payload.AuthorDto;
import com.chuwa.chuwa.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AuthorGraphQLController {
    private final AuthorService authorService;

    public AuthorGraphQLController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @MutationMapping
    public AuthorDto createAuthor(@Argument String name) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(name);

        return authorService.createAuthor(authorDto);
    }

}
