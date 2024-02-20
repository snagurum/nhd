update load_stocks lt
    set 
        lt.is_new=1
    where lt.ticker not in (select ticker from stock );


insert into stock(
    ticker             ,
    name               ,
    series             ,
    date_of_listing    ,
    paid_up_value      ,
    market_lot         ,
    isin               ,
    face_value         
) select 
        ticker             ,
        name               ,
        series             ,
        STR_TO_DATE(date_of_listing, '%d-%M-%Y')    ,
        paid_up_value      ,
        market_lot         ,
        isin               ,
        face_value         
    from  
        load_stocks;