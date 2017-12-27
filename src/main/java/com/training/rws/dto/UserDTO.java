package com.training.rws.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@JsonFilter("UserDTOFilter")
@ApiModel(description = "Details about the users")
public class UserDTO {

    @JsonIgnore
    private String password;

    private Integer id;

    @Size(min = 6, message = "The length of the field 'name', should be 6 characters at least")
    @ApiModelProperty(notes = "The length of the field 'name', should be 6 characters at least")
    private String name;

    private String lastName;

    private List<PostDTO> posts;

    @ApiModelProperty(notes = "The field 'birthDate', should be less than the current date")
    @Past(message = "The field 'birthDate', should be less than the current date")
    private Date birthDate;

    public UserDTO(){}

    public UserDTO(Integer id, String name, Date birthDate, String password, List<PostDTO> posts) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.setPassword(password);
        this.setPosts(posts);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}
