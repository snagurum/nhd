#! /bin/bash

tickerList=$(curl -X GET -sI https://nseindia.com  \
  -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0')

echo $tickerList  