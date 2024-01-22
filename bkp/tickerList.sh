#!/bin/bash
#Creates a new variable with a value of "Hello World"
learningbash="https://www.nseindia.com/api/historical/cm/equity?symbol=infy&series=[%22EQ%22]&from=14-01-2023&to=14-01-2024"
echo $learningbash


echo '--------------------------------------'
curl -I  https://www.nseindia.com   \
	 -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0'    \
	 -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8'  \
	 -H 'Accept-Language: en-US,en;q=0.5'  \
	 -H 'Upgrade-Insecure-Requests: 1'  \
	 -H 'Connection: keep-alive' \
	 -H 'Sec-Fetch-Dest: document' \
	 -H 'Sec-Fetch-Mode: navigate' \
	 -H 'Sec-Fetch-Site: none' \
	 -H 'Sec-Fetch-User: ?1' \
	 -H 'TE: trailers'



 #    -H 'Cookie: RT="z=1&dm=nseindia.com&si=a9ce078e-fb07-46e3-95a2-36926eb0dab4&ss=lrd3zett&sl=0&se=8c&tt=0&bcn=%2F%2F684d0d46.akstat.io%2F&ld=6ciy&ul=2xj0"; _ga_87M7PJ3R97=GS1.1.1705212904.2.1.1705213321.0.0.0; _ga=GA1.1.1507871839.1705193978; nseQuoteSymbols=[{"symbol":"INFY","identifier":null,"type":"equity"}]; ak_bmsc=3B59865D5A8E84A4B3E504952368656A~000000000000000000000000000000~YAAQBpUvFzt2fGOMAQAAJvidBhaL4y/8Y8VOmmbst/60w6MUGkh/fw+4ee0dut+EBCfHCKnkWU4wA2t1sgvkwp0Lqf2wYip0dv8wAbFsm3gmdsdPJF/iXSl+CjBDOQ+zCEMT8r4KX2JIFCEVh6aLE7XCIYd3Zku7LbH9PWz9WMLsxwDrf8fJnrvh1vhplqC1JX5VZVTM//xz4amECdRfZuJgf0SikD1DlMoq96mts3+VV5SdzuyClFXYTXbm3Unhrx6HCCXXa8ijl5qfnzqVif7FFQgy/KE6sNTP3sYYzhJ4DEI/QHee8GNz3hyEXkfPjUxoC+ijEjBN+RdeiiSuJixGzkXzRUB2UMcI0kAlpvPCifM1cyDgl7xiyx+GLXP6WfOJaBJP2dEntanimtLjcJMEenoOywhuUbY8soO3ZWAVQ8+HqlZXIPFZLlX9iLgAPE6KiEV+SH1ahHjdBuPVLSkqyuY89A9BiCV5taYD/CnEXJC3v+N7zb9UaazVUshjCb/+oi28XyIYL2VXJloAHAWEEsMh1Os7008Qb2Ynifny4cPRCA==; bm_sv=C8EEF14333B5F4CC46ACE92C9B5F1638~YAAQyXbNF1R+LPmMAQAA5VykBhZu4HfBgb96SsTZ7WP38SGzdXlUqJJMSWhQnTFykIw49ZypqSh26YLErews+VnKp+uPwqGl2O5miUW3bk3l/1+yyz0zSka2bzA3ZX8qPMqPkaElVEDNRkn65GNR/Mq0za5z1F49HvN+VJV0yWxh5CAoBxz0bHs4UN4x/HJfMiCvJIPjNzbfSxV0ZsJlx/2gYVig6bQQE+o0rt1glU390VEXofkkdomIxVhZOmEDKTzYew==~1; nsit=EtAZk0DSLyh8N7-5g2WBeTNr; nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTcwNTIxMzAyOSwiZXhwIjoxNzA1MjIwMjI5fQ.clr24GShMdWe2TEB62LA5u8UntXgiHETJQ2Piyw0IMg; AKA_A2=A; bm_mi=F62F9E9D46BC260B8379F14B5C708FDE~YAAQBpUvFwx2fGOMAQAAmOudBham1HTwN8pDf32WKXDYEgdJyvsF1f7jhWrpUot+MdimnlFejt/pDmBdZ8FBadWI3i6dTrss/rqctTftVhZcRESBUXXjUOkY9h4JuwPi6LkxxV9N8fYHJZpA9EHXK2K9mAxdGTOBakS+VjoD8+3/bXXxRSeKMwjOOmjgAJYm/NSem3rRYwK0Xhr2dlSwSqxtSnyaV7vHhOpzxfEw+Efkb7OgHTJlkBGHRP+0+ZyOBjTeGNbr88NptY+/fTfLJwOSvoRTh7cC6NGhQv9A+S117G6Jhpod3McwQb4skgkgbUkfsAKUEiCAgtnR803KBg==~1; defaultLang=en' \
