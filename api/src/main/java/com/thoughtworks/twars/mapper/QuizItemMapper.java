package com.thoughtworks.twars.mapper;

import com.thoughtworks.twars.bean.QuizItem;

import java.util.List;

public interface QuizItemMapper {

    List<QuizItem> getAllQuizItems();

    int insertQuizItem(QuizItem quizItem);

    QuizItem getQuizItemById(int id);

    List<QuizItem> getEasyItems(int easyCount);

    List<QuizItem> getNormalItems(int normalCount);

    List<QuizItem> getHardItems(int hardItems);

    List<QuizItem> getExampleItems(int exampleCount);
}
