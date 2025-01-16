package com.hardik.SpringSecurity.SecurityApplication.services;



import com.hardik.SpringSecurity.SecurityApplication.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPost();

    PostDTO createNewPost(PostDTO postDTO);

    PostDTO getPostById(Long id);

    PostDTO updatePost(Long id, PostDTO inputPost);
}
