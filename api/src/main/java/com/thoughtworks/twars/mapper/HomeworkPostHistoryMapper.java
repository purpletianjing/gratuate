package com.thoughtworks.twars.mapper;

import com.thoughtworks.twars.bean.HomeworkPostHistory;

import java.util.List;

public interface HomeworkPostHistoryMapper {

    int insertHomeworkPostHistory(HomeworkPostHistory homeworkPostHistory);

    List<HomeworkPostHistory> findByHomeworkSubmitId(int homeworkSubmitId);
}
