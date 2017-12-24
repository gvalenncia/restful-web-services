package com.training.rws.dao;

import com.training.rws.dto.PostDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostDAO {
    private static List<PostDTO> postDTOS = new ArrayList<>();
    private static int postCount = 3;

    static {
        postDTOS.add(new PostDTO(1, 1, new Date(), "post1", "adadfadfadsfasf"));
        postDTOS.add(new PostDTO(2, 1, new Date(), "post2", "adadfadfadsfasf"));
        postDTOS.add(new PostDTO(3, 1, new Date(), "post3", "adadfadfadsfasf"));
        postDTOS.add(new PostDTO(4, 2, new Date(), "post4", "adadfadfadsfasf"));
        postDTOS.add(new PostDTO(5, 2, new Date(), "post5", "adadfadfadsfasf"));
    }

    public List<PostDTO> findByUserId(int userId) {
        return postDTOS.stream()
                .filter(post -> post.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
