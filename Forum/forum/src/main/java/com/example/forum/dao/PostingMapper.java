package com.example.forum.dao;

import com.example.forum.entity.Posting;
import com.example.forum.entity.PostingExample;
import com.example.forum.entity.PostingKey;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2022/05/18
*/
@Mapper
public interface PostingMapper {
    int countByExample(PostingExample example);

    int deleteByExample(PostingExample example);

    int deleteByPrimaryKey(PostingKey key);

    int insert(Posting record);

    int insertSelective(Posting record);

    List<Posting> selectByExampleWithBLOBs(PostingExample example);

    List<Posting> selectByExample(PostingExample example);

    Posting selectByPrimaryKey(PostingKey key);

    int updateByExampleSelective(@Param("record") Posting record, @Param("example") PostingExample example);

    int updateByExampleWithBLOBs(@Param("record") Posting record, @Param("example") PostingExample example);

    int updateByExample(@Param("record") Posting record, @Param("example") PostingExample example);

    int updateByPrimaryKeySelective(Posting record);

    int updateByPrimaryKeyWithBLOBs(Posting record);

    int updateByPrimaryKey(Posting record);
}