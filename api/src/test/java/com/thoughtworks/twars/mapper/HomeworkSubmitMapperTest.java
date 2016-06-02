package com.thoughtworks.twars.mapper;

import com.thoughtworks.twars.bean.HomeworkSubmit;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HomeworkSubmitMapperTest extends TestBase{
    private HomeworkSubmitMapper homeworkSubmitMapper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        homeworkSubmitMapper = session.getMapper(HomeworkSubmitMapper.class);
    }

    @Test
    public void should_return_id_when_insert_homework_submit(){
        HomeworkSubmit homeworkSubmit = new HomeworkSubmit();
        homeworkSubmit.setScoreSheetId(1);
        homeworkSubmit.setHomeworkQuizId(3);

        homeworkSubmitMapper.insertHomeworkSubmit(homeworkSubmit);
        assertThat(homeworkSubmit.getId(), is(8));
    }

}
