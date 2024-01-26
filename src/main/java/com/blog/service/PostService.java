package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

  public  void deletePost(long id);

   public PostDto updatePost(long postId, PostDto postDto);

    List<PostDto> getAllPost();
}
