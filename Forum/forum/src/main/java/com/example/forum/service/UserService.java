package com.example.forum.service;

import com.example.forum.dao.UserMapper;
import com.example.forum.entity.User;
import com.example.forum.entity.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(Integer id,String password){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if(users!=null&& users.size()!=0)
            return users.get(0);
        else
            return null;
    }

    public User loginByEmail(String email, String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        criteria.andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if(users!=null&& users.size()!=0)
            return users.get(0);
        else
            return null;
    }

    public String checkEmail(String email) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> users = userMapper.selectByExample(userExample);
        if(users!=null&& users.size()!=0)
            return "fail";
        else
            return "success";

    }

    public String register(User user){
        user.setName(user.getEmail());
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        int result = userMapper.insertSelective(user);
        if(result==0){
            return "fail";
        }else
            return "success";
    }

    public User getMessage(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public String updateMessage(User user) {
        //System.out.println(user);
        int result = userMapper.updateByPrimaryKeySelective(user);
        if(result==0){
            return "fail";
        }else
            return "success";
    }


}
