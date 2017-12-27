package com.training.rws.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.training.rws.controller.exception.ExceptionResponse;
import com.training.rws.controller.exception.UserExistsException;
import com.training.rws.controller.exception.UserNotFoundException;
import com.training.rws.dao.PostDAO;
import com.training.rws.dto.PostDTO;
import com.training.rws.dto.UserDTO;
import com.training.rws.dao.UserDAO;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
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
    private UserDAO userDAO;

    @Autowired
    private PostDAO postDAO;

    @GetMapping(path = "/users")
    public ResponseEntity<MappingJacksonValue> retrieveAllUsers(){
        List<UserDTO> userDTOS = userDAO.findAll();

        MappingJacksonValue mapping = filterFields(userDTOS, new HashSet(Arrays.asList("id","name", "lastName", "birthDate")));

        return userDTOS.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok().body(mapping);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<MappingJacksonValue> retrieveUser(@PathVariable int id){
        UserDTO userDTO = userDAO.findOne(id);
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
        UserDTO newUserDTO = userDAO.save(userDTO);
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
        UserDTO userDTO = userDAO.deleteById(id);
        if(userDTO == null){
            throw new UserNotFoundException("The user with id: " + id + ", was not found for deleting");
        }
    }

    @GetMapping(path = "/users/{id}/posts")
    public ResponseEntity<List<PostDTO>> retrieveUserPosts(@PathVariable int id){
        List<PostDTO> postDTOS = postDAO.findByUserId(id);
        return postDTOS.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(postDTOS);
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
