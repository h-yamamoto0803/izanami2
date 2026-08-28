package com.example.demo.infra.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostTagId implements Serializable {

    private Integer postId;

    private Integer tagId;
}
