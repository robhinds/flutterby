package com.tmm.enterprise.microblog.helper;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateHelperTest {

	@Test
	public void testGetTimeAgo() {
		Date old = new Date();
		String s = DateHelper.getTimeAgo(old);
		assertEquals("just now", s);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(old);
		
	}
	
	@Test
	public void testGetTimeAgo1() {
		Date old = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(old);
		
		//change hours -1
		int current = cal.get(Calendar.HOUR);
		int updated = current==0 ? 11 : current-1;
		cal.set(Calendar.HOUR, updated);
		
		String s = DateHelper.getTimeAgo(cal.getTime());
		assertEquals("1 hour ago", s);
	}
	
	@Test
	public void testGetTimeAgo2() {
		Date old = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(old);
		
		//change minutes -1
		int current = cal.get(Calendar.MINUTE);
		int updated = current==0 ? 59 : current-1;
		cal.set(Calendar.MINUTE, updated);
		
		String s = DateHelper.getTimeAgo(cal.getTime());
		assertEquals("1 minute ago", s);
	}
	
	@Test
	public void testGetTimeAgo3() {
		Date old = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(old);
		
		//change minutes -1
		int current = cal.get(Calendar.DAY_OF_YEAR);
		int updated = current==0 ? 365 : current-1;
		cal.set(Calendar.DAY_OF_YEAR, updated);
		
		String s = DateHelper.getTimeAgo(cal.getTime());
		assertEquals("1 day ago", s);
	}
	
	
	@Test
	public void testGetTimeAgo4() {
		Date old = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(old);
		
		//change minutes -1
		int current = cal.get(Calendar.YEAR);
		int updated = current-1;
		cal.set(Calendar.YEAR, updated);
		
		String s = DateHelper.getTimeAgo(cal.getTime());
		assertEquals("1 year ago", s);
	}
	
	@Test
	public void testGetTimeAgo5() {
		Date old = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(old);
		
		//change minutes -1
		int current = cal.get(Calendar.YEAR);
		int updated = current-2;
		cal.set(Calendar.YEAR, updated);
		
		String s = DateHelper.getTimeAgo(cal.getTime());
		assertEquals("2 years ago", s);
	}

}
