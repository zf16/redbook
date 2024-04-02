package com.chuwa.chuwa.controller;

import com.chuwa.chuwa.payload.PostDto;
import com.chuwa.chuwa.service.PostService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PostGraphQLController {
    private final PostService postService;

    public PostGraphQLController(PostService postService) {
        this.postService = postService;
    }

    @QueryMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @QueryMapping
    public PostDto postById(@Argument long id) {
        return postService.getPostById(id);
    }

    @MutationMapping
    public PostDto createPost(@Argument String title, @Argument String description, @Argument String content) {
        PostDto postDto = new PostDto();
        postDto.setTitle(title);
        postDto.setDescription(description);
        postDto.setContent(content);

        return postService.createPost(postDto);
    }
}
