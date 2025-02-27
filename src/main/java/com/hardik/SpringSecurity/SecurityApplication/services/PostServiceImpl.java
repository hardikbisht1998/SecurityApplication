package com.hardik.SpringSecurity.SecurityApplication.services;



import com.hardik.SpringSecurity.SecurityApplication.dto.PostDTO;
import com.hardik.SpringSecurity.SecurityApplication.entities.PostEntity;
import com.hardik.SpringSecurity.SecurityApplication.entities.User;
import com.hardik.SpringSecurity.SecurityApplication.exceptions.ResourceNotFoundException;
import com.hardik.SpringSecurity.SecurityApplication.repositories.PostRepository;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;


    private final ModelMapper modelMapper;



    @Override
    public List<PostDTO> getAllPost() {
        return postRepository.findAll().stream().map(postEntity -> modelMapper.map(postEntity,PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity postEntity=modelMapper.map(postDTO, PostEntity.class);
        postEntity.setAuther(user);
        return modelMapper.map(postRepository.save(postEntity),PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long id) {
       // User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      //  log.info("User is {}",user);
        return modelMapper.map(postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("PostNot Found")),PostDTO.class);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO inputPost) {

         PostEntity olderPost=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post Not Found"));
//         inputPost.setId(id);
         modelMapper.map(inputPost,olderPost);
         return modelMapper.map(postRepository.save(olderPost),PostDTO.class);
    }


}
