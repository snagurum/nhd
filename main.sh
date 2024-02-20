#! /bin/bash

export mysql_user=nse
export mysql_pass=nse
export mysql_host=localhost
export mysql_db=nse

./loadStockList.sh
./updateStock.sh
