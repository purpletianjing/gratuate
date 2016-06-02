ALTER TABLE blankQuizScoreSheet DROP COLUMN paperId;
ALTER TABLE blankQuizScoreSheet DROP COLUMN examerId;
ALTER TABLE blankQuizScoreSheet change blankQuizId blankQuizSubmitId INT NOT NULL ;
ALTER TABLE blankQuizScoreSheet RENAME TO itemPost;
