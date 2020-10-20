drop database if exists guests_test;
create database guests_test;
use guests_test;
create table guest (
    guest_id int primary key auto_increment,
    first_name varchar(25),
    last_name varchar(50),
    email varchar(50),
    phone varchar(50),
    state varchar(50)
);
delimiter //
create procedure set_known_good_state()
begin
    truncate table guest;
    insert into guest 
        (guest_id,first_name,last_name,email,phone,state) 
    values
        (1, 'Sullivan', 'Lomas', 'slomas0@mediafire.com', '(702) 7768761', 'NV'),
        (2, 'Olympie', 'Gecks', 'ogecks1@dagondesign.com', '(202) 2528316', 'DC'),
        (3, 'Tremain', 'Carncross', 'tcarncross2@japanpost.jp', '(313) 2245034', 'MI');
end //
delimiter ;

use guests_test;
call set_known_good_state();