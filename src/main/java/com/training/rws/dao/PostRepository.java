package com.training.rws.dao;

import com.training.rws.dto.PostDTO;
import com.training.rws.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostDTO, Integer>{}
