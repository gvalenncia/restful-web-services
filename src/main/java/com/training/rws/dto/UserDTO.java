package com.training.rws.dto;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class UserDTO {

    private Integer id;

    @Size(min = 6, message = "'name' field length should be 6 characters at least")
    private String name;

    @Past(message = "'brthDate' field should be in the past")
    private Date birthDate;

    public UserDTO(){}

    public UserDTO(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
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
}
