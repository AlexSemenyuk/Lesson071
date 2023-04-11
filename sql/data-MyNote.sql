use myNotes;
insert into categories (num, category)
VALUES (1, 'House'),
       (2, 'Work'),
       (3, 'Fitness'),
       (4, 'Shopping');

insert into conditions (num, `condition`)
VALUES (1, 'Active'),
       (2, 'Done');

insert into priorities (num, priority)
VALUES (1, 'High'),
       (2, 'Normal'),
       (3, 'Low');

DELETE
FROM tasks;

insert into tasks (name, description, category_id, deadline, priority_id, condition_id)
VALUES ("Buy milk", "1 bottle", 4, "2023-04-10", 2, 1),
       ("Made something", "For small daughter", 1, "2023-05-12", 3, 1),
       ("Take something", "For Jake", 2, "2023-06-08", 3, 1),
       ("Training", "Pay for the lesson", 3, "2023-05-10", 1, 1),
       ("Buy beer", "1 bottle", 4, "2023-04-18", 2, 1),
       ("Training", "Take running shoes and sports uniform", 3, "2023-05-20", 1, 1),
       ("Take something", "For Ann", 2, "2023-10-10", 1, 1),
       ("Buy bread", "1 bread loaf", 4, "2023-04-14", 2, 1),
       ("Happy birthday", "Present for husband", 1, "2023-07-10", 1, 1);

UPDATE tasks
SET condition_id=1
WHERE id = 84;

select *
from tasks
order by category_id asc;



