package com.training.rws.dao;

import com.training.rws.dto.PostDTO;
import com.training.rws.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDAO {
    private static List<UserDTO> userDTOS = new ArrayList<>();
    private static int usersCount = 3;

    static {
        userDTOS.add(new UserDTO(1, "german", new Date(), "hiddenText",  new ArrayList<PostDTO>()));
        userDTOS.add(new UserDTO(2, "ruth", new Date(), "hiddenText",  new ArrayList<PostDTO>()));
        userDTOS.add(new UserDTO(3, "lina", new Date(), "hiddenText",  new ArrayList<PostDTO>()));
    }

    public List<UserDTO> findAll(){
        return userDTOS;
    }

    public UserDTO save(UserDTO userDTO){
        if(this.findByName(userDTO.getName()) != null){
            return null;
        } else {
            if(userDTO.getId() == null){
                userDTO.setId(++usersCount);
                userDTO.setPassword("1234");
            }
            userDTOS.add(userDTO);
            return userDTO;
        }
    }

    public UserDTO findOne(int id){
        for(UserDTO userDTO : userDTOS){
            if (userDTO.getId().intValue() == id){
                return userDTO;
            }
        }
        return null;
    }

    public UserDTO findByName(String name){
        for(UserDTO userDTO : userDTOS){
            if(userDTO.getName().equalsIgnoreCase(name)){
                return userDTO;
            }
        }
        return null;
    }

    public UserDTO deleteById(int id) {
        Iterator<UserDTO> iterator = userDTOS.iterator();
        while (iterator.hasNext()){
            UserDTO userDTO = iterator.next();
            if (userDTO.getId() == id){
                iterator.remove();
                return userDTO;
            }
        }
        return null;
    }
}
