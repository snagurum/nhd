

create database nse;
create user  nse with password 'nse';
Grant create on database nse to nse;
create schema nse;


create table nse.audit(
    job_id           varchar(20),
    job_name         varchar(50),
    start_date       timestamp,
    end_date         timestamp
);

create table nse.load_stocks(
    ticker           varchar(50),        -- SYMBOL
    name             varchar(200),       -- NAME OF COMPANY
    series           varchar(20),        -- SERIES
    date_of_listing  varchar(20),        -- DATE OF LISTING
    paid_up_value    varchar(20),        -- PAID UP VALUE
    market_lot       varchar(20),        -- MARKET LOT
    isin             varchar(20),        -- ISIN NUMBER
    face_value       varchar(20),        -- FACE VALUE
    is_new           boolean,
    crd_date         timestamp,
    upd_date         timestamp,
    crd_by           varchar(20),
    upd_by           varchar(20)
) ;

create table nse.stocks(
    ticker           varchar(50),        -- SYMBOL
    name             varchar(200),       -- NAME OF COMPANY
    series           varchar(20),        -- SERIES
    date_of_listing  date,               -- DATE OF LISTING
    paid_up_value    varchar(20),        -- PAID UP VALUE
    market_lot       varchar(20),        -- MARKET LOT
    isin             varchar(20),        -- ISIN NUMBER
    face_value       varchar(20),        -- FACE VALUE
    crd_date         timestamp,
    upd_date         timestamp,
    crd_by           varchar(20),
    upd_by           varchar(20)
) ;

create table nse.bulk_load_date(
    start_date date,
    end_date date,
    crd_date         timestamp,
    upd_date         timestamp,
    crd_by           varchar(20),
    upd_by           varchar(20)
);

create table nse.load_bulk_stock_dates(
    ticker           varchar(50),        -- SYMBOL
    start_date date,
    end_date date,
    is_loaded boolean default false,
    crd_date         timestamp,
    upd_date         timestamp,
    crd_by           varchar(20),
    upd_by           varchar(20)
);

create table nse.load_stock_price(
    ticker       varchar(50),        -- SYMBOL
    price_date   varchar(20),
    series       varchar(10),
    open         varchar(20),
    high         varchar(20),
    low          varchar(20),
    prev_close   varchar(20),
    ltp          varchar(20),
    close        varchar(20),
    vwap         varchar(20),
    high52       varchar(20),
    low52        varchar(20),
    volume       varchar(20),
    value        varchar(20),
    no_of_trades varchar(20),
    crd_date         timestamp   default current_timestamp,
    upd_date         timestamp   default current_timestamp,
    crd_by           varchar(20),
    upd_by           varchar(20)
);


-- create table nse.load_cronjobs(
--     name             varchar(50)   PRIMARY KEY,
--     type             varchar(50),
--     cron_exp         varchar(50),
--     job              varchar(200),
--     total_loads      integer,
--     pending_loads    integer,
--     status           varchar(20) default 'absent',
--     last_success_run timestamp,
--     crd_date         timestamp   default current_timestamp,
--     upd_date         timestamp   default current_timestamp,
--     crd_by           varchar(20),
--     upd_by           varchar(20)
-- );

create table nse.cronjobs(
    name             varchar(50)   PRIMARY KEY,
    type             varchar(50),
    cron_exp         varchar(50),
    job              varchar(200),
    total_loads      integer,
    pending_loads    integer,
    status           varchar(20)     default 'absent',
    last_success_run timestamp,
    crd_date         timestamp   default current_timestamp,
    upd_date         timestamp   default current_timestamp,
    crd_by           varchar(20),
    upd_by           varchar(20)
);

create table nse.load_daily_stock_dates(
    ticker           varchar(50),        -- SYMBOL
    price_date       date,
    is_loaded        boolean default false,
    crd_date         timestamp,
    upd_date         timestamp,
    crd_by           varchar(20),
    upd_by           varchar(20)
);

alter table stocks add column status varchar(10) default 'active';


create table nse.load_daily_stock_price(
    ticker                 varchar(50),        -- SYMBOL
    price_date             date,
    info                   text,
    metadata               text,
    security_info          text,
    sdd_details            text,
    price_info             text,
    industry_info          text,
    preopen_market         text,
    bulk_block_deals       text,
    market_dept_order_book text,
    security_wise_dp       text,
    crd_date         timestamp   default current_timestamp,
    upd_date         timestamp   default current_timestamp,
    crd_by           varchar(20),
    upd_by           varchar(20)
);


-- insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-1993','31-Dec-1993');
-- insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-1994','31-Dec-1994');
-- insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-1995','31-Dec-1995');

insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-1996','31-Dec-1996');

insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-1997','31-Dec-1997');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-1998','31-Dec-1998');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-1999','31-Dec-1999');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2000','31-Dec-2000');

insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2001','31-Dec-2001');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2002','31-Dec-2002');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2003','31-Dec-2003');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2004','31-Dec-2004');

insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2005','31-Dec-2005');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2006','31-Dec-2006');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2007','31-Dec-2007');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2008','31-Dec-2008');

insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2009','31-Dec-2009');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2010','31-Dec-2010');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2011','31-Dec-2011');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2012','31-Dec-2012');

insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2013','31-Dec-2013');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2014','31-Dec-2014');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2015','31-Dec-2015');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2016','31-Dec-2016');

insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2017','31-Dec-2017');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2018','31-Dec-2018');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2019','31-Dec-2019');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2020','31-Dec-2020');

insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2021','31-Dec-2021');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2022','31-Dec-2022');
insert into nse.bulk_load_date(start_date, end_date)values('01-Jan-2023','31-Dec-2023');



