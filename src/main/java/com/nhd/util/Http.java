package com.nhd.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhd.models.HttpResponse;

public class Http {
    
    private static final Logger log = LoggerFactory.getLogger(Http.class);

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:123.0) Gecko/20100101 Firefox/123.0";
    private static final String ACCEPT =  "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8";
    private static final String ACCEPT_LANGUAGE =  "en-US,en;q=0.5";
    private static final String ACCEPT_ENCODING =  "gzip, deflate, br";

    public static void setDefaultHeaders(HttpURLConnection con) throws IOException {
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept", ACCEPT);
        con.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
//        con.setRequestProperty("Accept-Encoding", ACCEPT_ENCODING);
    }

    public static String readResponseBody(HttpURLConnection con, Boolean appendNewLine) throws IOException {
        StringBuffer response = null;
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
                if(appendNewLine){
                    response.append(System.lineSeparator());
                }
			}
			in.close();
			// System.out.println(response.toString());
		} else {
			log.error("GET request did not work. Response Code = {}", responseCode);
		}
        return response.toString();
    }


    public static HttpResponse loadPage(String urlString, CookieHandler cookies, Boolean appendNewLine) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        setDefaultHeaders(httpURLConnection);
        if (cookies != null){
            httpURLConnection.setRequestProperty("Cookie", cookies.getCookiesAsString());
            cookies.saveCookies(httpURLConnection.getHeaderFields());
        }
        HttpResponse response = new HttpResponse(
            httpURLConnection.getResponseCode(), 
            readResponseBody(httpURLConnection,appendNewLine)
        );
        return response;
  
    }

}

