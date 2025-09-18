package com.example.forum.service;

import com.example.forum.dao.LikeMapper;
import com.example.forum.dao.PostingMapper;
import com.example.forum.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostingMapper postingMapper;

    @Autowired
    private LikeMapper likeMapper;
    public int addPost(Posting post) {
        return postingMapper.insertSelective(post);
    }


    public String deletePost(PostingKey postingKey) {
        int result = postingMapper.deleteByPrimaryKey(postingKey);
        if (result == 1)
            return "success";
        else
            return "fail";
    }

    public String updatePost(Posting posting) {
        int result = postingMapper.updateByPrimaryKeySelective(posting);
        if (result == 1)
            return "success";
        else
            return "fail";
    }

    public Posting selectPostByKey(PostingKey postingKey) {
        return postingMapper.selectByPrimaryKey(postingKey);
    }


    public List<Posting> selectPostByTitle(String title,Integer page, Integer size, boolean DESC) {
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria criteria = postingExample.createCriteria();
        if(DESC){
            postingExample.setOrderByClause("post_date DESC");
        }else
        {
            postingExample.setOrderByClause("post_date ASC");
        }
        System.out.println("title "+title);
        if( title!=null&&!title.equals("")) {
            criteria.andTitleLike("%" + title + "%");
            //System.out.println("title "+title)
        }
        List<Posting> postList = postingMapper.selectByExampleWithBLOBs(postingExample);
        List<Posting> list;
        if (postList == null)
            return null;
        if ((page) * 10 < postList.size())
            list = postList.subList((page - 1) * 10, (page) * 10);
        else
            list = postList.subList((page - 1) * 10, postList.size());
        //System.out.println(list.get(1).toString());
        //System.out.println("list "+list);
        return list;
    }

    public List<Posting> selectPostByLabel(String label, Integer page, Integer size, boolean DESC) {
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria criteria = postingExample.createCriteria();
        if(DESC){
            postingExample.setOrderByClause("post_date DESC");
        }else
        {
            postingExample.setOrderByClause("post_date ASC");
        }
        criteria.andLabelEqualTo(label);
        List<Posting> postList = postingMapper.selectByExampleWithBLOBs(postingExample);
        List<Posting> list;
        if (postList == null &&postList.size()==0)
            return null;
        if ((page) * 10 < postList.size())
            list = postList.subList((page - 1) * 10, (page) * 10);
        else
            list = postList.subList((page - 1) * 10, postList.size());
        //System.out.println(list.get(1).toString());
        //System.out.println("list "+list);
        return list;
    }

    public List<Posting> selectPostByLabel(String title, String label, Integer page, Integer size, boolean DESC) {
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria criteria = postingExample.createCriteria();
        if(DESC){
            postingExample.setOrderByClause("post_date DESC");
        }else
        {
            postingExample.setOrderByClause("post_date ASC");
        }
        if( title!=null&&!title.equals("")) {
            criteria.andTitleLike("%" + title + "%");
            //System.out.println("title "+title)
        }
        criteria.andLabelEqualTo(label);
        List<Posting> postList = postingMapper.selectByExampleWithBLOBs(postingExample);
        List<Posting> list;
        if (postList == null&&postList.size()==0)
            return null;
        if ((page) * 10 < postList.size())
            list = postList.subList((page - 1) * 10, (page) * 10);
        else
            list = postList.subList((page - 1) * 10, postList.size());
        //System.out.println(list.get(1).toString());
        //System.out.println("list "+list);
        return list;
    }
    public int getTotalByTitle(String title){
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria criteria = postingExample.createCriteria();
        System.out.println(title);
        if( title!=null&&!title.equals(""))
            criteria.andTitleLike("%"+title+"%");
        //System.out.println(postingMapper.countByExample(postingExample));
        return postingMapper.countByExample(postingExample);
    }
    public int getTotalByLabel(String label) {
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria criteria = postingExample.createCriteria();
        System.out.println(label);
        criteria.andLabelEqualTo(label);
        //System.out.println(postingMapper.countByExample(postingExample));
        return postingMapper.countByExample(postingExample);
    }
    public int getTotalByLabel(String title,String label) {
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria criteria = postingExample.createCriteria();
        System.out.println(label);
        criteria.andLabelEqualTo(label);
        if( title!=null&&!title.equals(""))
            criteria.andTitleLike("%"+title+"%");
        //System.out.println(postingMapper.countByExample(postingExample));
        return postingMapper.countByExample(postingExample);
    }
    public int getTotal(){
        PostingExample postingExample = new PostingExample();
        return postingMapper.countByExample(postingExample);
    }

    public List<Posting> findCollention(Integer id) {
        LikeExample likeExample = new LikeExample();
        LikeExample.Criteria criteria = likeExample.createCriteria();
        criteria.andSenderIdEqualTo(id);
        criteria.andCollectionEqualTo(false);
        List<Like> likes = likeMapper.selectByExample(likeExample);
        List collections= new ArrayList();
        for (Like like: likes) {
            collections.add(like.getPostId());
        }
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria postingCriteria = postingExample.createCriteria();
        if (collections.size()!=0) {
            postingCriteria.andPostIdIn(collections);
            return postingMapper.selectByExampleWithBLOBs(postingExample);
        }else {
            return null;
        }
//        System.out.println("coll:  "+postingMapper.selectByExampleWithBLOBs(postingExample));

    }

    public List<Posting> findMyPosting(Integer id) {
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria postingCriteria = postingExample.createCriteria();
        postingCriteria.andIdEqualTo(id);
        return postingMapper.selectByExampleWithBLOBs(postingExample);
    }

    public List<Posting> selectAll(Integer page, Integer size, boolean DESC) {
        PostingExample postingExample = new PostingExample();
        PostingExample.Criteria criteria = postingExample.createCriteria();
        if(DESC){
            postingExample.setOrderByClause("post_date DESC");
        }else
        {
            postingExample.setOrderByClause("post_date ASC");
        }
        List<Posting> postList = postingMapper.selectByExampleWithBLOBs(postingExample);
        List<Posting> list;
        if (postList == null)
            return null;
        if ((page) * 10 < postList.size())
            list = postList.subList((page - 1) * 10, (page) * 10);
        else
            list = postList.subList((page - 1) * 10, postList.size());
        //System.out.println(list.get(1).toString());
        //System.out.println(list.get(1).getId());
        //System.out.println(list);
        return list;
    }


}
