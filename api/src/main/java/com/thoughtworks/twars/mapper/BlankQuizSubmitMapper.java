package com.thoughtworks.twars.mapper;

import com.thoughtworks.twars.bean.BlankQuizSubmit;

import java.util.List;

public interface BlankQuizSubmitMapper {

    int insertBlankQuizSubmit(BlankQuizSubmit blankQuizSubmit);

    List<BlankQuizSubmit> findByScoreSheetId(int scoreSheetId);
}
