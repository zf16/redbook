package com.chuwa.chuwa.service.impl;

import com.chuwa.chuwa.dao.PostRepository;
import com.chuwa.chuwa.entity.Post;
import com.chuwa.chuwa.exception.ResourceNotFoundException;
import com.chuwa.chuwa.payload.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chuwa.chuwa.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        // business logic is here

        // data coming from POST request
        // DTO --> Entity transfer and then save to DB
//        Post postEntityToBeSaved = new Post();
//        postEntityToBeSaved.setTitle(postDto.getTitle());
//        postEntityToBeSaved.setDescription(postDto.getDescription());
//        postEntityToBeSaved.setContent(postDto.getContent());

        // 把payload转换成entity，这样才能dao去把该数据存到数据库中。
        Post postEntityToBeSaved = mapToEntity(postDto);

        // 调用Dao的save 方法，将entity的数据存储到数据库MySQL
        // save()会返回存储在数据库中的数据
        Post postSaved = postRepository.save(postEntityToBeSaved);

        // Entity --> DTO --> return to API
//        PostDto response = new PostDto();
//        response.setId(postSaved.getId());
//        response.setTitle(postSaved.getTitle());
//        response.setDescription(postSaved.getDescription());
//        response.setContent(postSaved.getDescription());

        // 将save() 返回的数据转换成controller/前端 需要的数据，然后return给controller
        return mapToDto(postSaved);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
              .map(this::mapToDto)
              .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        // update post
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post postToDelete = postRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        postRepository.delete(postToDelete);
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
