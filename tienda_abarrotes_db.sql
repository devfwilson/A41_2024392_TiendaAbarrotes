drop database if exists tienda_abarrotes_db;
create database tienda_abarrotes_db;
use tienda_abarrotes_db;
 
create table Productos(
	codigo_producto integer auto_increment,
    nombre varchar (64),
    precio decimal(10,2),
    stock integer,
    constraint pk_productos primary key (codigo_producto)
);

create table Compras (
	codigo_compra integer auto_increment,
    codigo_producto integer,
    cantidad integer,
    total decimal(10,2),
    constraint pk_compras primary key (codigo_compra),
    constraint fk_compras_productos foreign key (codigo_producto)
		references Productos (codigo_producto) on delete cascade
);

select * from Productos;