package com.example.forum.controller;

import com.example.forum.entity.Posting;
import com.example.forum.entity.PostingKey;
import com.example.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @RequestMapping("/addPost")
    public String addPost(@RequestBody Posting post) {
        int result = postService.addPost(post);
        if (result == 1)
            return "success";
        else
            return "fail";
    }

    @RequestMapping("/deletePost/{id}/{postId}")
    public String deletePost(@PathVariable("id") Integer id, @PathVariable("postId") Integer postId) {
        PostingKey postingKey = new PostingKey();
        postingKey.setId(id);
        postingKey.setPostId(postId);
        //System.out.println(postingKey);
        return postService.deletePost(postingKey);
    }

    @RequestMapping("/updatePost")
    public String updatePost(@RequestBody Posting post) {
        return postService.updatePost(post);
    }

    @RequestMapping("/getOnePost/{id}/{postId}")
    public Posting selectPostByKey(@PathVariable("id") Integer id, @PathVariable("postId") Integer postId) {
        PostingKey postingKey = new PostingKey();
        postingKey.setId(id);
        postingKey.setPostId(postId);
        System.out.println(postingKey);
        //System.out.println(postService.selectPostByKey(postingKey));
        //System.out.println(postingKey);
        return postService.selectPostByKey(postingKey);
    }

    @GetMapping("/selectAll/{page}/{size}/{DESC}")
    @ResponseBody
    public List<Posting> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("DESC") boolean DESC) {
        return postService.selectAll(page, size , DESC);
    }


    @RequestMapping("/selectAll/{title}/{page}/{size}/{DESC}")
    @ResponseBody
    public List<Posting> selectPostByTitle(@PathVariable("title") String title,@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("DESC") boolean DESC) {
        return postService.selectPostByTitle(title,page,size, DESC);
    }

    @RequestMapping("/selectAllByLabel/{label}/{page}/{size}/{DESC}")
    @ResponseBody
    public List<Posting> selectPostByLabel(@PathVariable("label") String label,@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("DESC") boolean DESC) {
        return postService.selectPostByLabel(label,page,size, DESC);
    }

    @RequestMapping("/selectAllByLabel/{title}/{label}/{page}/{size}/{DESC}")
    @ResponseBody
    public List<Posting> selectPostByLabel(@PathVariable("title") String title,@PathVariable("label") String label,@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("DESC") boolean DESC) {
        return postService.selectPostByLabel(title,label,page,size, DESC);
    }

    @RequestMapping("/total")
    public int getTotal(){
        return postService.getTotal();
    }

    @RequestMapping("/total/{title}")
    public int getTotalByTitle(@PathVariable("title") String title){
        return postService.getTotalByTitle(title);
    }

    @RequestMapping("/totalByLabel/{label}")
    public int getTotalByLabel(@PathVariable("label") String label){
        return postService.getTotalByLabel(label);
    }

    @RequestMapping("/totalByLabel/{title}/{label}")
    public int getTotalByLabel(@PathVariable("title") String title,@PathVariable("label") String label){
        return postService.getTotalByLabel(title,label);
    }
    @RequestMapping("/selectByCollection/{id}")
    @ResponseBody
    public List<Posting> findCollention(@PathVariable("id") Integer id) {
        return postService.findCollention(id);
    }

    @RequestMapping("/selectByPosterId/{id}")
    @ResponseBody
    public List<Posting> findMyPosting(@PathVariable("id") Integer id) {
        return postService.findMyPosting(id);
    }
}
