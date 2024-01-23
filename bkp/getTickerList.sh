#curl -s -o getTickerList.csv https://nsearchives.nseindia.com/content/equities/EQUITY_L.csv  -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0'
tickerList=$(curl -s https://nsearchives.nseindia.com/content/equities/EQUITY_L.csv  -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0')
echo 'tickerList'
echo ------------
echo $tickerList 