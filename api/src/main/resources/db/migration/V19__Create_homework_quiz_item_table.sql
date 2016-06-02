CREATE TABLE homeworkQuizItem (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    description VARCHAR (2048) NOT NULL,
    evaluateScript VARCHAR (2048) NOT NULL ,
    evaluateRepository VARCHAR (2048) NOT NULL ,
    templateRepository VARCHAR (512) NOT NULL
)