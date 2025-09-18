package com.example.forum.service;

import com.example.forum.dao.LikeMapper;
import com.example.forum.entity.Like;
import com.example.forum.entity.LikeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeMapper likeMapper;

    public Like getLike(LikeKey likeKey){
//        System.out.println(likeKey);
//        System.out.println(likeMapper.selectByPrimaryKey(likeKey));
        return likeMapper.selectByPrimaryKey(likeKey);
    }

    public String changeLike(Like like){
        LikeKey likeKey = new LikeKey();
        likeKey.setPosterId(like.getPosterId());
        likeKey.setPostId(like.getPostId());
        likeKey.setSenderId(like.getSenderId());
        int i = 0;
        //System.out.println(likeKey.getSenderId());
        if(getLike(likeKey)==null)
            i=likeMapper.insertSelective(like);
        else
            i=likeMapper.updateByPrimaryKeySelective(like);

        if(i == 1)
            return "success";
        else
            return "fail";
    }

}
