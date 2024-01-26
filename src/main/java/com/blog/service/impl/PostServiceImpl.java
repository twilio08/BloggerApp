package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {

        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post savedPost = postRepo.save(post);

        PostDto dto = new PostDto();
        dto.setId(savedPost.getId());
        dto.setTitle(savedPost.getTitle());
        dto.setDescription(savedPost.getDescription());
        dto.setContent(savedPost.getContent());
      //  dto.setMessage("Post Is Created");
        return dto;


    }

    @Override
    public void deletePost(long id) {
      Post post= postRepo.findById(id).orElseThrow(

               ()->new ResourceNotFoundException("Post Not Found with Id:"+id)
       );
       postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id:" + postId)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post savePost = postRepo.save(post);

        PostDto dto = mapToDto(savePost);
        return dto;
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepo.findAll();
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;
    }

    PostDto mapToDto(Post post){
        PostDto dto=new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }
}
