TICK=INFY

echo
echo
echo '01 .... load home page nseindia.com'
echo '===============================>>>>>>>>>>>>>>>>>>>>>'
header=$(curl -ILs https://www.nseindia.com  \
    -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0' \
    -H 'Accept-Language: en-US,en;q=0.5'    \
    -H 'Cache-Control: no-cache'    \
    -H 'Connection: keep-alive' )
echo $header


curl  -s 'https://www.nseindia.com/'    \
    -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0'     \
    -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8'  \
    -H 'Accept-Language: en-US,en;q=0.5'    \
    -H 'DNT: 1'     \
    -H 'Connection: keep-alive'     \
    -H 'Upgrade-Insecure-Requests: 1'   \
    -H 'Sec-Fetch-Dest: document'   \
    -H 'Sec-Fetch-Mode: navigate'   \
    -H 'Sec-Fetch-Site: none'   \
    -H 'Sec-Fetch-User: ?1'     \
    -H 'Pragma: no-cache'   \
    -H 'Cache-Control: no-cache'    \
    -H 'TE: trailers'                        \
   -H 'Accept-Encoding: gzip, deflate, br'    | gunzip - >file.html



# echo
# echo
# echo '02 .... autocomplete ticker nseindia.com/api/search/autocomplete(https://www.nseindia.com/api/search/autocomplete?q=INFY)'
# echo '===============================>>>>>>>>>>>>>>>>>>>>>'
# header1=$(curl -sI 'https://www.nseindia.com/api/search/autocomplete?q=INFY'                                              \
#     -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0'             \
#     -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Accept-Language: en-US,en;q=0.5'            \
#     -H 'X-Requested-With: XMLHttpRequest'                                                                       \
#     -H 'DNT: 1'                                                                                                 \
#     -H 'Connection: keep-alive'                                                                                 \
#     -H 'Referer: https://www.nseindia.com/'                                                                     \
#     -H 'Sec-Fetch-Dest: empty'                                                                                  \
#     -H 'Sec-Fetch-Mode: cors'                                                                                   \
#     -H 'Sec-Fetch-Site: same-origin'                                                                            \
#     -H 'Pragma: no-cache'                                                                                       \
#     -H 'Cache-Control: no-cache'                                                                                \
#     -H 'TE: trailers'                                                                                           \
#     -H 'Cookie: $header'  )
# #    -H 'Cookie: nsit=9DkgTQN50665OxyZoJnNt9QC; nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTcwNTc3MDM4OSwiZXhwIjoxNzA1Nzc3NTg5fQ._cPCSJfpVktv4kY81BYpVZzsJSoE-xcsK72NuRVsl5Q; AKA_A2=A; ak_bmsc=64B119DA47AEE0EC8305C86999B7D2B7~000000000000000000000000000000~YAAQBGU+F4PaVxKNAQAAjKLWJxZ/YUSF+7ohsxhr+61ZcbtthTDBDBopZF8nnimwhiGO45XGhRITyWxQrUDAEI7WUnROHQ6P8JC7nhYPlAGiAvhFCjUl6ef9tS1d6UYtIkjHACWxnJyjhACGvS0UHxLRzbguznWEfzvIgGGdSG9H9HJTYBb6KMmSIiBoQ2efklPGIHDq9QUs/g2hMi9IZec+aW360xZPvE/wnkTc3TV7EAIHwXfIJeH0/mz9AGMIDjJPJ3Qrl6pLXaEcwTax9UOUpd3Puqx0oiO6u8a8L34xQFfrrOX4iHmOPK6tS5APHXt7qqSOFNhyhAf0E5j3PdfBKku4cE1FvlLzzZCLMplXWfa1ZSPw47Lh8Iy8ynB9OtHQhGhXv8ejJl2F8G7qHusGBBcze/WnhgkJlqQ+d29F+4pkHoSgurJ1+Hm2/BC7SLDOhAzZ9DjeDkeOQr5CmuH+PbwCUCaKPIOprr7RaJGoVUEzxevq3NUVkEZOd8EeOXflQg==; defaultLang=en; bm_sv=CF1D7B94FE2E782A4C7CDCB0069AC2FF~YAAQBGU+F/YEWBKNAQAAZPHaJxbxfGYlv3C7BscMToQiJfOTWDaoLKVGeIn2NkrNVC7FP6FotI0kK2Zv75u2LejXembe6CWqxS0uIGH2fqodNrGYqQsLojvhPnL0cMnJF/A55D+Dyp5nyPDI9cMgt3l38Y5gljRK30YzKugIKZaNHwkkY6gWwe3QcOqOouYXtW2jrVHb6TAjjkXoGqb0+IlemMAm2RTe/Y3ejCU97snAeDAKPINV0/mHHdQGvfkoniUZ~1; _ga_87M7PJ3R97=GS1.1.1705770394.1.1.1705770394.0.0.0; _ga=GA1.1.117770128.1705770394' 
# #    -H 'Accept-Encoding: gzip, deflate, br'                                                                        \
# echo $header1


# echo
# echo
# echo '03 .... GET ticker nseindia.com/get-quotes/equity?symbol=INFY'
# echo '===============================>>>>>>>>>>>>>>>>>>>>>'
# header2=$(curl -sI 'https://www.nseindia.com/get-quotes/equity?symbol=INFY'                                            \
#     -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0'         \
#     -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8'      \
#     -H 'Accept-Language: en-US,en;q=0.5'                                                                    \
#     -H 'DNT: 1'                                                                                             \
#     -H 'Connection: keep-alive'                                                                             \
#     -H 'Referer: https://www.nseindia.com/'                                                                 \
#     -H 'Upgrade-Insecure-Requests: 1'                                                                       \
#     -H 'Sec-Fetch-Dest: document'                                                                           \
#     -H 'Sec-Fetch-Mode: navigate'                                                                           \
#     -H 'Sec-Fetch-Site: same-origin'                                                                        \
#     -H 'Sec-Fetch-User: ?1'                                                                                 \
#     -H 'Pragma: no-cache'                                                                                   \
#     -H 'Cache-Control: no-cache'                                                                            \
#     -H 'TE: trailers'                                                                                       \
#     -H 'Cooke: $header1'   )
#     #    -H 'Cookie: nsit=VMgmgajmIlb9lU0iveLKnd9N; nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTcwNTc2ODgzNCwiZXhwIjoxNzA1Nzc2MDM0fQ.y8CbxnIc89zfr3rA3b5lFIaFUlikp09QLQNE1_8mVT0; AKA_A2=A; ak_bmsc=D48EE0C2CDE4C15F448232A4F78BF9D9~000000000000000000000000000000~YAAQBGU+F2nTVhKNAQAAqOm+JxZaV+AMPiqyic4KMGBYy5Q74TvUhHTb7oQuXBpZdTMQlGS6rmRLSfVX/DTgDZAmA30H5WmePGdRarQ9250k6fZLav0iTxRkM2ISiOGHKOJknc9iHE9PGSEbAXLw7aVFtyGbL00ZbS0zBDhjsZa2ZykSH9VRc/zOa8ol5/AWk3NPu3FBs2mVmL/pbwCGOPe+OHzbGc6uD7hsOebm3XWKHLjFox4UsF4A8T/H3h+I3paO5RfizBPu5BxCipLVkHnY3vqIMiqst6n5EhFaxStYB+MAWccP6sxvRSK4yHm5/5LQARCBVKWAiEaXVDCY0m6z1rlyzzYBN9VYqWoXxjCff7Ew7nSeIWuemqAtOtF85amf+PQAbJ2qgraWZWkAiDI9FniOpwDgcMZIDt7cvy4ATfNPLyiOTtGwCDOe/y4NrLCfcpRsIRzhTihC9ykRRi2O0oE61lif6Bu1pJkRQj+GNUx9o/ai4ZDo1mx8+Hu1s1C5Aw==; defaultLang=en; bm_sv=D208A603B3425771E1211099E2F852F7~YAAQBGU+F+c5VxKNAQAAnG7IJxZjQDfHPEW3jpKLXBXKBf2iTaJvLyGzxRZeupU+qMYBMy8OCZg+Kv28Dv1D20bR9hWUrWxbAvh7fvZClwkspvInMS/S+5FQLCJqSU+9YzRIGNALCNsLrWZXpmj8OSV1LiOqZlgHdZF4TA4zldkh2sSH89FAYSAdYwPKy1rTelyotATAevQhIT7Hel9QwgZkpvGfeigxzuf9Ugw+3nigGAMh9mJKG24qsqdseXlG0L/U~1; _ga_87M7PJ3R97=GS1.1.1705768840.1.1.1705768840.0.0.0; _ga=GA1.1.134116567.1705768840'  \
#     #    -H 'Accept-Encoding: gzip, deflate, br'                                                                 \
# echo $header2


# echo
# echo
# echo '04 .... GET ticker-JSON nseindia.com/api/quote-equity?symbol=INFY'
# curl 'https://www.nseindia.com/api/quote-equity?symbol=INFY'                                               \
#     -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0'         \
#     -H 'Accept: */*'                                                                                        \
#     -H 'Accept-Language: en-US,en;q=0.5'                                                                    \
#     -H 'DNT: 1'                                                                                             \
#     -H 'Connection: keep-alive'                                                                             \
#     -H 'Referer: https://www.nseindia.com/get-quotes/equity?symbol=INFY'                                    \
#     -H 'Sec-Fetch-Dest: empty'                                                                              \
#     -H 'Sec-Fetch-Mode: cors'                                                                               \
#     -H 'Sec-Fetch-Site: same-origin'                                                                        \
#     -H 'Pragma: no-cache'                                                                                   \
#     -H 'Cache-Control: no-cache'                                                                            \
#     -H 'TE: trailers'                                                                                       \
#     -H 'Cooke: $header2'     
#     # -H 'Cookie: nsit=9DkgTQN50665OxyZoJnNt9QC; AKA_A2=A; ak_bmsc=64B119DA47AEE0EC8305C86999B7D2B7~000000000000000000000000000000~YAAQBGU+F4PaVxKNAQAAjKLWJxZ/YUSF+7ohsxhr+61ZcbtthTDBDBopZF8nnimwhiGO45XGhRITyWxQrUDAEI7WUnROHQ6P8JC7nhYPlAGiAvhFCjUl6ef9tS1d6UYtIkjHACWxnJyjhACGvS0UHxLRzbguznWEfzvIgGGdSG9H9HJTYBb6KMmSIiBoQ2efklPGIHDq9QUs/g2hMi9IZec+aW360xZPvE/wnkTc3TV7EAIHwXfIJeH0/mz9AGMIDjJPJ3Qrl6pLXaEcwTax9UOUpd3Puqx0oiO6u8a8L34xQFfrrOX4iHmOPK6tS5APHXt7qqSOFNhyhAf0E5j3PdfBKku4cE1FvlLzzZCLMplXWfa1ZSPw47Lh8Iy8ynB9OtHQhGhXv8ejJl2F8G7qHusGBBcze/WnhgkJlqQ+d29F+4pkHoSgurJ1+Hm2/BC7SLDOhAzZ9DjeDkeOQr5CmuH+PbwCUCaKPIOprr7RaJGoVUEzxevq3NUVkEZOd8EeOXflQg==; defaultLang=en; bm_sv=CF1D7B94FE2E782A4C7CDCB0069AC2FF~YAAQBGU+F4OhWBKNAQAAauXrJxZqlbuyjRyOFOum+OA11C/U5FTXCaUsu0vDGBRwgM21/jbDwSQ4QlzUoSpShOT2U4d4mSEz0GK6gDYanelDF35Boi0Ro+ZwjgMuFpPcLbSLwjlXNGYYwXl7hbtJdjs7BWSz0JhIbBYFAnvhshUEbMX2oC1fsS41SetR3G5ZMAwHZNqTG06Fov3sJvo6rrQ0QD19cgew0vIV3dflLtCRKmmgLAMhlCkQNa7KYJzFv8RF~1; _ga_87M7PJ3R97=GS1.1.1705770394.1.1.1705771787.0.0.0; _ga=GA1.1.117770128.1705770394; nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTcwNTc3MTc4NCwiZXhwIjoxNzA1Nzc4OTg0fQ.pto7OCR1uPlPfAuT4M_GHdSrogqaWDkvoTt-AfzAJjY; nseQuoteSymbols=[{"symbol":"INFY","identifier":null,"type":"equity"}]' 
#     # -H 'Accept-Encoding: gzip, deflate, br' 

 curl 'https://www.nseindia.com/'                                                                              \
 -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0'             \
 -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8'                     \
 -H 'Accept-Language: en-US,en;q=0.5'                                                                            \
 -H 'Accept-Encoding: gzip, deflate, br'                                                                        \
 -H 'Connection: keep-alive'                                                                                   \
 -H 'Upgrade-Insecure-Requests: 1'                                                                               \
 -H 'Sec-Fetch-Dest: document'                                                                                      \
 -H 'Sec-Fetch-Mode: navigate'                                                                                 \
 -H 'Sec-Fetch-Site: none'                                                                                           \
 -H 'Sec-Fetch-User: ?1'                                                                                          \
 -H 'Pragma: no-cache'                                                                                      \
 -H 'Cache-Control: no-cache'