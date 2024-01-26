package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.service.PostService;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
   public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,BindingResult bindingResult) {
       if(bindingResult.hasErrors()) {
           return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post Is Deleted",HttpStatus.OK);
    }
    //http://localhost:8080/api/posts?postId=1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<PostDto> updatePost(@RequestParam(name = "postId") long postId ,
                                              @RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost(){
        List<PostDto> dto = postService.getAllPost();
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}
