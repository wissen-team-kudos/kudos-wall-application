insert into users values
	(1,'Mandar','Mandar'),
	(2,'Sayali','Sayali'),
	(3,'Sreejit','Sreejit'),
	(4,'Saurabh','Saurabh'),
	(5,'Vinayak','Vinayak');

insert into `rooms_of_user` values
	(1,'Grads','Grads'),
	(2,'KudosTeam','KudosTeam'),
	(3,'Mentors','Mentors');

insert into kudos values
	(1,'Great work!',3),
	(2,'Well done',4),
	(3,'Great going',5);

insert into users_rooms values
	(1,1),
	(1,2),
	(2,1),
    (2,2),
    (3,1),
	(3,2),
    (4,2),
    (4,3),
    (5,2),
    (5,3);
    
insert into users_kudos values
	(1,1),
	(2,1),
	(1,2),
    (2,2),
    (3,2),
	(1,3),
    (2,3),
    (3,3);
    
insert into rooms_kudos values
	(1,1),
	(2,2),
	(2,3);
