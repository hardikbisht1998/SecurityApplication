package com.hardik.SpringSecurity.SecurityApplication.repositories;


import com.hardik.SpringSecurity.SecurityApplication.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

}
