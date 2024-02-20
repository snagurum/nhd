#! /bin/bash

# export mysql_user=nse
# export mysql_pass=nse
# export mysql_host=localhost
# export mysql_db=nse

echo '-- getting ticker List ------------'
echo 

#curl -s -o getTickerList.csv https://nsearchives.nseindia.com/content/equities/EQUITY_L.csv  -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0'
tickerList=$(curl -s https://nsearchives.nseindia.com/content/equities/EQUITY_L.csv  \
  -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0')

#echo $tickerList
#tickerCSVFile=getTickerList.csv

# SYMBOL
# NAME OF COMPANY
# SERIES
# DATE OF LISTING
# PAID UP VALUE
# MARKET LOT
# ISIN NUMBER
# FACE VALUE

rm exec_queries.data.sql

mysql -s -u $mysql_user -h $mysql_host -D $mysql_db -e "TRUNCate load_stocks;"

rowCount=1
while IFS="," read -r rec_col1 rec_col2 rec_col3 rec_col4 rec_col5 rec_col6 rec_col7 rec_col8
do

  SYMBOL=$rec_col1
  NOC=$rec_col2
  NOC="${NOC//\'/\'\'}"
  SERIES=$rec_col3
  DOL=$rec_col4
  PUV=$rec_col5
  ML=$rec_col6
  ISIN=$rec_col7
  FV=$rec_col8
  query="insert into load_stocks(ticker,name,series,date_of_listing,paid_up_value,market_lot,isin,face_value)values('$SYMBOL','$NOC','$SERIES','$DOL','$PUV','$ML','$ISIN','$FV');"
  if [ $rowCount -gt 1 ]
  then
    echo $query>>exec_queries.data.sql
  fi
  rowCount=$(( rowCount + 1 ))

#done < <(tail -n +2 $tickerList)
done <<< "$tickerList"

echo rowCount=$rowCount

mysql -s -u $mysql_user -h $mysql_host -D $mysql_db < exec_queries.txt

