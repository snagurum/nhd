-- SYMBOL,NAME OF COMPANY, SERIES, DATE OF LISTING, PAID UP VALUE, MARKET LOT, ISIN NUMBER, FACE VALUE

 CREATE USER 'nse'@'%' IDENTIFIED BY 'nse';
 GRANT ALL PRIVILEGES ON *.* TO 'nse'@'%'
     WITH GRANT OPTION;



create table load_tickers(
    id               int auto_increment primary key,
    ticker           varchar(50),
    name             varchar(100),
    series           varchar(5),
    date_of_listing  varchar(20),
    paid_up_value    varchar(20),
    market_lot       varchar(20),
    isin             varchar(20),
    face_value       varchar(20)
) 