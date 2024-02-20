-- SYMBOL,NAME OF COMPANY, SERIES, DATE OF LISTING, PAID UP VALUE, MARKET LOT, ISIN NUMBER, FACE VALUE

 CREATE USER 'nse'@'%' IDENTIFIED BY 'nse';
 GRANT ALL PRIVILEGES ON *.* TO 'nse'@'%'
     WITH GRANT OPTION;



create table load_stocks(
    ticker           varchar(50),
    name             varchar(200),
    series           varchar(20),
    date_of_listing  varchar(20),
    paid_up_value    varchar(20),
    market_lot       varchar(20),
    isin             varchar(20),
    face_value       varchar(20),
    is_new           bool default 0
) ;



create table stock(
    id               int auto_increment primary key,
    ticker           varchar(50),
    name             varchar(200),
    series           varchar(20),
    date_of_listing  date,
    paid_up_value    int,
    market_lot       int,
    isin             varchar(20),
    face_value       int
);