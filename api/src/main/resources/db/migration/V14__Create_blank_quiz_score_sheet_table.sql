CREATE TABLE blankQuizScoreSheet (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    examerId INT NOT NULL ,
    blankQuizId INT NOT NULL ,
    quizItemId INT NOT NULL ,
    userAnswer VARCHAR (128) NOT NULL
);
