package com.thoughtworks.twars.mapper;

import com.thoughtworks.twars.bean.HomeworkSubmit;

import java.util.List;

public interface HomeworkSubmitMapper {

    int insertHomeworkSubmit(HomeworkSubmit homeworkSubmit);

    List<HomeworkSubmit> findByScoreSheetId(int scoreSheetId);
}
