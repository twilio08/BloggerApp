package com.blog.service;

import com.blog.entity.Comments;
import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
     CommentDto createComment(long postId, CommentDto commentDto);

     void deleteComment(long commmentId);

     List<CommentDto> getCommentByPostId(long postId);

     List<CommentDto> getAllComments();

     CommentDto updateComment(long commentId, CommentDto dto);
     
}
