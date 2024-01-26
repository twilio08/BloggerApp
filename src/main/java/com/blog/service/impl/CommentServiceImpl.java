package com.blog.service.impl;

import com.blog.entity.Comments;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepo;
    private PostRepository postRepo;


    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id:" +postId)
        );
        Comments comments=new Comments();
        comments.setName(commentDto.getName());
        comments.setBody(commentDto.getBody());
        comments.setEmail(commentDto.getEmail());

        comments.setPost(post);
        Comments savedComment = commentRepo.save(comments);
        CommentDto dto = mapToDto(savedComment);
        return dto;

    }

    @Override
    public void deleteComment(long commmentId) {
        Comments comments = commentRepo.findById(commmentId).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with id:" + commmentId)
        );
        commentRepo.deleteById(commmentId);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comments> comments = commentRepo.findByPostId(postId);
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comments> comments = commentRepo.findAll();
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto dto) {
        Comments comments = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found with Id:" + commentId)
        );
        comments.setName(dto.getName());
        comments.setBody(dto.getBody());
        comments.setEmail(dto.getEmail());
        Comments saveComment = commentRepo.save(comments);
        CommentDto dtos = mapToDto(saveComment);
        return dtos;
    }

    CommentDto mapToDto(Comments comments){
        CommentDto dto  = new CommentDto();
        dto.setId(comments.getId());
        dto.setName(comments.getName());
        dto.setBody(comments.getBody());
        dto.setEmail(comments.getEmail());
        return dto;
    }
}
