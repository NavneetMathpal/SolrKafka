package com.tata.service.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangesolrCompatibleDate {

	public static Date changeStringToDate(String date) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Date convertedDate = null;

        try {
         convertedDate = formatter.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
		return convertedDate;
	}

}
