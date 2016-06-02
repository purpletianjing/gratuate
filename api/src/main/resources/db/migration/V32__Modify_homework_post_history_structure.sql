ALTER TABLE homeworkPostHistory DROP COLUMN homeworkQuizItemId;
ALTER TABLE homeworkPostHistory ADD COLUMN status INT NOT NULL ;
ALTER TABLE homeworkPostHistory ADD COLUMN timestamp INTEGER NOT NULL ;

