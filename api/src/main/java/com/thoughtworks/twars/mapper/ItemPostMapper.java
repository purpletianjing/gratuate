package com.thoughtworks.twars.mapper;

import com.thoughtworks.twars.bean.ItemPost;

import java.util.List;

public interface ItemPostMapper {

    int insertItemPost(ItemPost itemPost);
    
    List<ItemPost> findByBlankQuizSubmit(int blankQuizSubmitId);
}
