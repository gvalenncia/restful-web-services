package com.training.rws.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class PostDTO {

    @Id
    @GeneratedValue
    private int id;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserDTO userDTO;

    public PostDTO(){}

    public PostDTO(int id, String description, UserDTO userDTO) {
        this.setId(id);
        this.setDescription(description);
        this.setUserDTO(userDTO);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
