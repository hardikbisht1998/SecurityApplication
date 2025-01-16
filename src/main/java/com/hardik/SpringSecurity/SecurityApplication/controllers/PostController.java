package com.hardik.SpringSecurity.SecurityApplication.controllers;



import com.hardik.SpringSecurity.SecurityApplication.dto.PostDTO;
import com.hardik.SpringSecurity.SecurityApplication.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    @Autowired
    private  PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts(){
       return postService.getAllPost();
    }

    @GetMapping(path = "/{postId}")
    public PostDTO getPostById(@PathVariable(name="postId") Long id){
        return postService.getPostById(id);
    }


    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @PutMapping("/{postId}")
    public PostDTO updatePost(@PathVariable(name="postId") Long id,@RequestBody PostDTO inputPost){
        return postService.updatePost(id,inputPost);
    }
}
