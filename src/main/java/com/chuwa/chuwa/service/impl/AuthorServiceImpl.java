package com.chuwa.chuwa.service.impl;

import com.chuwa.chuwa.dao.AuthorRepository;
import com.chuwa.chuwa.entity.Author;
import com.chuwa.chuwa.exception.ResourceNotFoundException;
import com.chuwa.chuwa.payload.AuthorDto;
import com.chuwa.chuwa.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = mapToEntity(authorDto);
        Author authorSaved = authorRepository.save(author);
        return mapToDto(authorSaved);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
              .map(this::mapToDto)
              .collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthorById(long id) {
        Author author = authorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Author", "Id", id));
        return mapToDto(author);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto, long id) {
        Author author = authorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Author", "Id", id));

        // update author
        author.setName(authorDto.getName());

        Author updatedAuthor = authorRepository.save(author);
        return mapToDto(updatedAuthor);
    }

    @Override
    public void deleteAuthorById(long id) {
        Author author = authorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Author", "Id", id));

        authorRepository.delete(author);
    }

    private AuthorDto mapToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        return authorDto;
    }

    private Author mapToEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        return author;
    }
}
