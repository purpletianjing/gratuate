package com.thoughtworks.twars.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Paper {
    private int id;
    private int makerId;
    private List<Section> sections;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMakerId() {
        return makerId;
    }

    public void setMakerId(int makerId) {
        this.makerId = makerId;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Map getResponseInfo() {
        Map result = new HashMap<>();

        result.put("id", id);

        List<Map> sectionsInfo = sections.stream()
                .map(section -> section.getResponseInfo())
                .collect(Collectors.toList());

        result.put("sections", sectionsInfo);

        return result;
    }
}
