package com.thoughtworks.twars.mapper;

import com.thoughtworks.twars.bean.SectionQuiz;

import java.util.List;

public interface SectionQuizMapper {

    List<SectionQuiz> findBySectionId(int sectionId );

    int insertSectionQuiz(SectionQuiz sectionQuiz);
}
