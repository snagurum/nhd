package com.nhd.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookieHandler {
 
    private Map<String, String> cookiesMap = new HashMap<>();

    public void saveCookies(Map<String,List<String>> headers){
        List<String> cookieList = headers.get("Set-Cookie");
        cookieList.forEach(e->{
            cookiesMap.put(
                e.substring(0,e.indexOf("="))
                ,e.substring(e.indexOf("=")+1 ,e.indexOf(";"))
            );
        });
    }

    public String getCookiesAsString(){
        StringBuffer temp = new StringBuffer();
        cookiesMap.keySet().forEach(e->{
            temp.append(e) ;
            temp.append("=");
            temp.append(cookiesMap.get(e));
            temp.append(";");
        });
        return temp.toString();
    }

    public void printCookies(){
        cookiesMap.keySet().forEach(e->{
            System.out.println(e + " = " + cookiesMap.get(e));
        });
    }

    public Map<String, String> getCookiesMap(){
        return cookiesMap;
    }
}
