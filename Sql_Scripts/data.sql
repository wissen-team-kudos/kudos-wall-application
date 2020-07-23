insert into users values
	(1,'user1','pass1'),
	(2,'user2','pass2'),
	(3,'user3','pass3');

insert into `rooms_of_user` values
	(1,'room1','room1'),
	(2,'room2','room2');

insert into kudos values
	(1,'kudos1',1),
	(2,'kudos2',2),
	(3,'kudos3',3);

insert into users_rooms values
	(1,1),
	(1,2),
	(2,1),
    (3,1),
    (3,2);
    
insert into users_kudos values
	(1,2),
	(1,3),
	(2,1),
    (3,2),
    (3,1);
    
insert into rooms_kudos values
	(1,2),
	(2,3),
	(1,1);
