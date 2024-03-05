create database cuiyq;
use cuiyq;

create user 'cuiyq'@'%' identified by '123456';

grant all on cuiyq.* to 'cuiyq'@'%';