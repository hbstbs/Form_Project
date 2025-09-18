package com.example.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2022/05/18
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like extends LikeKey {
    private Boolean likes;

    private Boolean collection;
}