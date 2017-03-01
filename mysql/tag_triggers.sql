
delimiter //
CREATE TRIGGER userTagRelationTrigger BEFORE INSERT ON UserTagRelation
for each row
BEGIN 
    INSERT INTO test2 SET a2 = NEW.a1;
    DELETE FROM test3 WHERE a3 = NEW.a1;
    UPDATE test4 SET b4 = b4 + 1 WHERE a4 = NEW.a1;
  END;