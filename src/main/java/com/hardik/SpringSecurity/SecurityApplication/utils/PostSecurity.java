package com.hardik.SpringSecurity.SecurityApplication.utils;

import com.hardik.SpringSecurity.SecurityApplication.dto.PostDTO;
import com.hardik.SpringSecurity.SecurityApplication.entities.User;
import com.hardik.SpringSecurity.SecurityApplication.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostSecurity {
    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO postDTO=postService.getPostById(postId);
        return postDTO.getAuther().getId().equals(user.getId());

    }
}
