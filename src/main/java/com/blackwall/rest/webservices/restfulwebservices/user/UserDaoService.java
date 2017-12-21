package com.blackwall.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "german", new Date()));
        users.add(new User(2, "ruth", new Date()));
        users.add(new User(3, "lina", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(usersCount++);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        for(User user: users){
            if (user.getId().intValue() == id){
                return user;
            }
        }
        return null;
    }
}
