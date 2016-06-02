CREATE TABLE blankQuiz (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    sectionId INT NOT NULL,
    type VARCHAR(64),
    count INT NOT NULL,
    hardCount INT NOT NULL,
    normalCount INT NOT NULL,
    easyCount INT NOT NULL
)