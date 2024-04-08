package com.chuwa.chuwa.service.impl;

import com.chuwa.chuwa.dao.CommentRepository;
import com.chuwa.chuwa.dao.PostRepository;
import com.chuwa.chuwa.entity.Comment;
import com.chuwa.chuwa.entity.Post;
import com.chuwa.chuwa.exception.BlogAPIException;
import com.chuwa.chuwa.exception.ResourceNotFoundException;
import com.chuwa.chuwa.payload.CommentDto;
import com.chuwa.chuwa.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        // retrieve post entity by id
        Post post = postRepository.findById(postId)
              .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        // set post to comment entity
        Comment comment = mapToComment(commentDto);
        comment.setPost(post);

        // save entity to DB
        Comment savedComment = commentRepository.save(comment);

        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        // retrieve comments by postId
        return commentRepository.findByPostId(postId).stream()
              .map(this::mapToDto)
              .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        // retrieve post entity by id
        Post post = postRepository.findById(postId)
              .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId)
              .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        if (comment.getPost().getId() != post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        // retrieve post entity by id
        Post post = postRepository.findById(postId)
              .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId)
              .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        if (comment.getPost().getId() != post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {

        // retrieve post entity by id
        Post post = postRepository.findById(postId)
              .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId)
              .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        if (comment.getPost().getId() != post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment) {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    private Comment mapToComment(CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }
}
