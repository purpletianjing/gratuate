package com.thoughtworks.twars.resource;

import com.thoughtworks.twars.bean.ScoreSheet;
import com.thoughtworks.twars.mapper.ScoreSheetMapper;
import com.thoughtworks.twars.resource.quiz.scoresheet.BlankQuizScoreSheetService;
import com.thoughtworks.twars.resource.quiz.scoresheet.HomeworkQuizScoreSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Path("/scoresheets")
@Api
public class ScoreSheetResource extends Resource {
    @Inject
    private ScoreSheetMapper scoreSheetMapper;
    @Inject
    private BlankQuizScoreSheetService blankQuizScoreSheet;
    @Inject
    private HomeworkQuizScoreSheetService homeworkQuizScoreSheet;

    @GET
    @ApiResponses(value = {@ApiResponse(code = 200, message = "get all scoresheets successful"),
            @ApiResponse(code = 404, message = "get all scoresheets failed")})
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<ScoreSheet> scoreSheets = scoreSheetMapper.findAll();

        if (scoreSheets == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List result = scoreSheets.stream()
                .map(item -> {
                    Map map = new HashMap();
                    map.put("uri", "scoresheets/" + item.getId());

                    return map;
                })
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(result).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "insert scoresheet successful")})
    public Response insertScoreSheet(
            @ApiParam(name = "data", value = "include all info when you insert scoreSheet",
                    required = true)
            Map data) {
        int examerId = (int) data.get("examerId");
        int paperId = (int) data.get("paperId");
        int scoreSheetId;

        ScoreSheet scoreSheet = new ScoreSheet();
        scoreSheet.setPaperId(paperId);
        scoreSheet.setExamerId(examerId);

        try {
            ScoreSheet selectScoreSheet = scoreSheetMapper.selectScoreSheet(scoreSheet);

            if (selectScoreSheet != null) {
                scoreSheetId = selectScoreSheet.getId();
            } else {
                scoreSheetMapper.insertScoreSheet(scoreSheet);
                scoreSheetId = scoreSheet.getId();
            }

            List<Map> blankQuizSubmits = (List<Map>) data.get("blankQuizSubmits");
            List<Map> homeworkSubmits = (List<Map>) data.get("homeworkSubmits");

            if (blankQuizSubmits != null) {
                blankQuizScoreSheet.insertQuizScoreSheet(data, scoreSheetId);
            }

            if (homeworkSubmits != null) {
                homeworkQuizScoreSheet.insertQuizScoreSheet(data, scoreSheetId);
            }

            Map result = new HashMap<>();
            result.put("uri", "scoresheets/" + scoreSheetId);

            return Response.status(Response.Status.CREATED).entity(result).build();


        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @GET
    @Path("/{id}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "get one scoreSheet successfully"),
            @ApiResponse(code = 404, message = "get one scoreSheet failed")})
    @Produces(MediaType.APPLICATION_JSON)
    public Response findOne(
            @ApiParam(name = "scoreSheetId", value = "int",required = true)
            @PathParam("id") int id
    ) {
        ScoreSheet scoreSheet = scoreSheetMapper.findOne(id);

        if (scoreSheet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Map<String, Object> examerUri = new HashMap<>();
        Map<String, Object> paperUri = new HashMap<>();
        examerUri.put("uri", "examer/" + scoreSheet.getExamerId());
        paperUri.put("uri", "paper/" + scoreSheet.getPaperId());

        Map map = new HashMap<>();
        map.put("examer", examerUri);
        map.put("paper", paperUri);
        map.put("blankQuizSubmits", blankQuizScoreSheet.getQuizScoreSheet(id));
        map.put("homeworkSubmits", homeworkQuizScoreSheet.getQuizScoreSheet(id));

        return Response.status(Response.Status.OK).entity(map).build();
    }

}
