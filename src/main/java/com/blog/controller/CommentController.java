package com.blog.controller;

import com.blog.entity.Comments;
import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam(name = "postId") long postId,
                                                  @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable long postId){
        List<CommentDto> dtos = commentService.getCommentByPostId(postId);
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComment(){
        List<CommentDto> dtos = commentService.getAllComments();
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long commentId, @RequestBody CommentDto dto){
        CommentDto dtos = commentService.updateComment(commentId, dto);
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }
}
