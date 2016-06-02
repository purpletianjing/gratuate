ALTER TABLE homeworkQuizScoreSheet DROP COLUMN paperId;
ALTER TABLE homeworkQuizScoreSheet DROP COLUMN examerId;
ALTER TABLE homeworkQuizScoreSheet DROP COLUMN homeworkQuizId;
ALTER TABLE homeworkQuizScoreSheet change githubAddress homeworkURL VARCHAR(512) NOT NULL ;
ALTER TABLE homeworkQuizScoreSheet ADD COLUMN homeworkSubmitId INT NOT NULL ;
ALTER TABLE homeworkQuizScoreSheet ADD COLUMN version VARCHAR(512) NOT NULL ;
ALTER TABLE homeworkQuizScoreSheet ADD COLUMN branch VARCHAR(32) NOT NULL ;
ALTER TABLE homeworkQuizScoreSheet RENAME TO homeworkPostHistory;