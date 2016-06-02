package com.thoughtworks.twars.mapper;

import com.thoughtworks.twars.bean.Paper;

import java.util.List;

public interface PaperMapper {

    List<Paper> findAll();

    Paper getPaperById(int id);

    Paper getOnePaper(int id);

    int insertPaper(Paper paper);
}
