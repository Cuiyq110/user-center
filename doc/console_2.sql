create database user_center_prod;
use user_center_prod;

create user 'user_center_prod'@'%' identified by '123456';

grant all on user_center_prod.* to 'user_center_prod'@'%';