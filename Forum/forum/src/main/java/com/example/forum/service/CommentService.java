package com.example.forum.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.example.forum.dao.CommentMapper;
import com.example.forum.entity.Comment;
import com.example.forum.entity.CommentExample;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public String addComment(Comment comment){
        int result = commentMapper.insertSelective(comment);
        if(result ==1)
            return "success";
        else
            return "fail";
    }

    public String deleteComment(Comment comment){
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();

        criteria.andPostIdEqualTo(comment.getPostId());
        criteria.andPosterIdEqualTo(comment.getPosterId());
        criteria.andSenderIdEqualTo(comment.getSenderId());
        criteria.andCommentDateEqualTo(comment.getCommentDate());
        int result = commentMapper.deleteByExample(commentExample);
        if(result ==1)
            return "success";
        else
            return "fail";
    }

    public List<Comment> getComments(Integer page,Integer size,Integer postId,Integer posterId){
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andPostIdEqualTo(postId);
        criteria.andPosterIdEqualTo(posterId);
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        List<Comment> list;
        if (commentList == null)
            return null;
        if ((page) * size < commentList.size())
            list = commentList.subList((page - 1) * size, (page) * size);
        else
            list = commentList.subList((page - 1) * size, commentList.size());
        //System.out.println(list.get(1).toString());
        //System.out.println(list.get(1).getId());
        //System.out.println(list);
        return list;
    }


    public List<Comment> getMyComments(Integer id) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andSenderIdEqualTo(id);
        return commentMapper.selectByExample(commentExample);
    }

    public int getCommentsTotal(Integer postId, Integer posterId) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andPostIdEqualTo(postId);
        criteria.andPosterIdEqualTo(posterId);
        return commentMapper.countByExample(commentExample);
    }
}
