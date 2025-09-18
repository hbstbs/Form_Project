package com.example.forum.controller;

import com.example.forum.entity.Like;
import com.example.forum.entity.LikeKey;
import com.example.forum.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @RequestMapping("/getLike/{postId}/{posterId}/{senderId}")
    @ResponseBody
    private Like getLike(@PathVariable("postId") Integer postId, @PathVariable("posterId") Integer posterId, @PathVariable("senderId") Integer senderId){
        LikeKey likeKey = new LikeKey();
        likeKey.setPosterId(posterId);
        likeKey.setPostId(postId);
        likeKey.setSenderId(senderId);
        System.out.println(likeKey);
        //System.out.println(likeService.getLike(likeKey));
        return likeService.getLike(likeKey);
    }

    @RequestMapping("/updateLike")
    public String updateLike(@RequestBody com.example.forum.entity.Like like){
        return likeService.changeLike(like);
    }
}
