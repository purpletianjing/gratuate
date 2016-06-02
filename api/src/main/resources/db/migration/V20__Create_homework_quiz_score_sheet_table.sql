CREATE TABLE homeworkQuizScoreSheet(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    examerId INT NOT NULL ,
    paperId INT NOT NULL ,
    homeworkQuizItemId INT NOT NULL ,
    userAnswer VARCHAR (512) NOT NULL
)