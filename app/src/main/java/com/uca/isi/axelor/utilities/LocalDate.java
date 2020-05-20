package com.uca.isi.axelor.utilities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocalDate {

    public String getDateInString(String dateISOformat){

        if(dateISOformat != null ){
            if(!dateISOformat.isEmpty()){
                DateTimeFormatter parser1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                DateTime time = parser1.parseDateTime(dateISOformat);
                Date date = time.toDate();

                return new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES")).format(date);
            }
        }
        return "";
    }
}
