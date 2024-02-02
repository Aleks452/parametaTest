package com.parameta.parametaEmployees.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parameta.parametaEmployees.exceptions.InternalErrorException;

public class Utilities {
	
	private static final Logger logger = LoggerFactory.getLogger(Utilities.class);
	
	public static int validateYears(Date date) {
        
		// Convert Date to LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();

        // Obtain the current date
        LocalDate currentDate = LocalDate.now();

        // get age in years
        Period period = Period.between(localDate, currentDate);
        int year = period.getYears();

        return year;
    }
	
	public static int validateDays(Date date) {
        
		// Convert Date to LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();

        // Obtain the current date
        LocalDate currentDate = LocalDate.now();

        // get age in years
        Period period = Period.between(localDate, currentDate);
        int days = period.getDays();

        return days;
    }
	
	public static String getPeriod(Date date) {
        
		// Convert Date to LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();

        // Obtain the current date
        LocalDate currentDate = LocalDate.now();

        // get period
        Period period = Period.between(localDate, currentDate);
        
        // convert data to get information
        return String.format("%d years, %d months and %d days",
                period.getYears(),
                period.getMonths(),
                period.getDays());
    }
	
	public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) {
		try
		{
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		}
        catch (DatatypeConfigurationException ex) {
            // Internal error exception
        	logger.error(ex.getMessage());
        	throw new InternalErrorException("{\"message\": \"" +  Constants.INTERNAL_ERROR + "\",\"code\": \"INTERNAL_ERROR\"}");
        }
    }
}
