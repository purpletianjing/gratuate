package com.thoughtworks.twars.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Section {
    private int id;
    private int paperId;
    private String description;
    private String type;
    private List<Integer> quizzes;

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Integer> quizzes) {
        this.quizzes = quizzes;
    }

    public Map getResponseInfo() {
        Map result = new HashMap<>();
        result.put("id", id);
        result.put("description", description);
        result.put("type", type);

        List<Map> quizzesInfo = quizzes.stream()
                .map(id -> {
                    Map quiz = new HashMap();
                    quiz.put("id", id);
                    quiz.put("definition_uri", getDefinition(id));
                    quiz.put("items_uri", getItemsUri(id));
                    return quiz;
                }).collect(Collectors.toList());

        result.put("quizzes", quizzesInfo);

        return result;
    }

    private String getItemsUri(Integer id) {
        if ("blankQuizzes".equals(type)) {
            return "blankQuizzes/" + id + "/items";
        }
        return null;
    }

    private String getDefinition(Integer id) {
        if ("blankQuizzes".equals(type)) {
            return "blankQuizzes/" + id;
        } else if ("homeworkQuizzes".equals(type)) {
            return "homeworkQuizzes/" + id;
        }
        return null;
    }
}
