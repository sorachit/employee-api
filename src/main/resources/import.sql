INSERT INTO department(code, name) VALUES 
(1, 'Mavel'),
(2, 'DC')
;

INSERT INTO employee(id, first_name,last_name,gender,department,version) VALUES 
(1, 'Tony','Stark','M',1,0),
(2, 'Bruce' , 'Wayne' , 'M' , 2,1),
(3, 'Bruce' , 'Banner' , 'M' , 1,0),
(4, 'Barry' , 'Allen' , 'M' , 2,0),
(5, 'Diana' , 'Prince' , 'F' , 2,0),
(6, 'Clark' , 'Kent' , 'M' , 2,0),
(7, 'Carol' , 'Danvers' , 'F' , 1,0),
(8, 'Steve' , 'Rogers' , 'M' , 1,0),
(9, 'Natasha' , 'Romanova' , 'F' , 1,0),
(10, 'Stephen' , 'Strange' , 'M' , 1,0),
(11, 'Peter' , 'Parker' , 'M' , 1,0),
(12, 'Peter' , 'Quill' , 'M' , 1,0),
(13, 'Arthur' , 'Curry' , 'M' , 2,0),
(14, 'Victor' , 'Stone' , 'M' , 2,0)
;

INSERT INTO ability(id , power , employee) VALUES (1 , 'Rich' , 1);

INSERT INTO employee_email(id,employee_id, email) VALUES 
(1 ,1, 'tony.s@mavel.com'), 
(2 ,1, 'tony.s@gmail.com')
;

