package com.training.rws.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.training.rws.controller.exception.UserExistsException;
import com.training.rws.controller.exception.UserNotFoundException;
import com.training.rws.dao.PostRepository;
import com.training.rws.dao.UserRepository;
import com.training.rws.dto.PostDTO;
import com.training.rws.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/users")
    public ResponseEntity<MappingJacksonValue> retrieveAllUsers(){
        List<UserDTO> userDTOS = userRepository.findAll();

        MappingJacksonValue mapping = filterFields(userDTOS, new HashSet(Arrays.asList("id","name", "lastName", "birthDate")));

        return userDTOS.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok().body(mapping);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<MappingJacksonValue> retrieveUser(@PathVariable int id){
        UserDTO userDTO = userRepository.findOne(id);
        if(userDTO == null){
            throw new UserNotFoundException("The user with id: " + id + ", was not found");
        }

        //applying hateoas
        Resource<UserDTO> resource = new Resource<UserDTO>(userDTO);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok().body(filterFields(resource, new HashSet()));
    }

    @PostMapping(path = "/users")
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO newUserDTO = userRepository.save(userDTO);
        if (newUserDTO == null){
            throw new UserExistsException("The user with name: " + userDTO.getName() + ", already exists");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUserDTO.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.delete(id);
    }

    @GetMapping(path = "/users/{id}/posts")
    public ResponseEntity<List<PostDTO>> retrieveUserPosts(@PathVariable int id){
        UserDTO userDTO = userRepository.findOne(id);
        if (userDTO == null){
            throw new UserNotFoundException("id: " + id);
        }

        return userDTO.getPosts().isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(userDTO.getPosts());
    }

    @PostMapping(path = "/users/{id}/posts")
    public ResponseEntity<PostDTO> craetePost(@PathVariable int id,
                                                    @RequestBody  PostDTO post){
        UserDTO userDTO = userRepository.findOne(id);
        if (userDTO == null){
            throw new UserNotFoundException("id: " + id);
        }

        post.setUserDTO(userDTO);
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    private static MappingJacksonValue filterFields(Object entity, Set fields){
        SimpleBeanPropertyFilter filter;
        if (fields.isEmpty()){
            filter = SimpleBeanPropertyFilter.serializeAll();
        } else {
            filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
        }

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDTOFilter", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(entity);
        mapping.setFilters(filters);

        return mapping;
    }
}
