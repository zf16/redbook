package com.chuwa.chuwa.controller;

import com.chuwa.chuwa.payload.PostDto;
import com.chuwa.chuwa.payload.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chuwa.chuwa.service.PostService;
import util.AppConstants;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

//    @Autowired
//    PostService postService;

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto response = postService.createPost(postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @GetMapping("")
//    public List<PostDto> getAllPosts() {
//        return postService.getAllPosts();
//    }

    @GetMapping("")
    public PostResponse getAllPosts(
          @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
          @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
          @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
          @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir

    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}
