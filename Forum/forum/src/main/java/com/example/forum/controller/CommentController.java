package com.example.forum.controller;

import com.example.forum.entity.Comment;
import com.example.forum.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/addComment")
    public String addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @RequestMapping("/deleteComment")
    public String deleteComment(@RequestBody Comment comment){
        return commentService.deleteComment(comment);
    }

    @RequestMapping("/selectAll/{page}/{size}/{postId}/{posterId}")
    @ResponseBody
    public List<Comment> getComments(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("postId") Integer postId, @PathVariable("posterId") Integer posterId){
        return commentService.getComments(page, size ,postId ,posterId);
    }

    @RequestMapping("/getMyComment/{id}")
    @ResponseBody
    public List<Comment> getMyComments(@PathVariable("id") Integer id){
        return commentService.getMyComments(id);
    }

    @RequestMapping("/total/{postId}/{posterId}")
    public int getCommentsTotal(@PathVariable("postId") Integer postId, @PathVariable("posterId") Integer posterId){
        return commentService.getCommentsTotal(postId ,posterId);
    }
}
