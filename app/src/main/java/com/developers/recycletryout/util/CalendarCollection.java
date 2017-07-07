package com.developers.recycletryout.util;

import java.util.ArrayList;

public class CalendarCollection {
	int id;
	String name;
	String event_desc_sort;
	String event_desc_full;
	String date;
	String event_type;
	String key;
	public static ArrayList<CalendarCollection> date_collection_arr;
	
	
	
	
	public CalendarCollection() {
		super();
	}
	public CalendarCollection(String date,String name,String key) {
		super();
		this.date = date;
		this.name = name;
		this.key = key;
		
	}
	public CalendarCollection(String name, String event_desc_sort,
			String event_desc_full, String date, String event_type) {
		super();
		this.name = name;
		this.event_desc_sort = event_desc_sort;
		this.event_desc_full = event_desc_full;
		this.date = date;
		this.event_type = event_type;
	}
	public CalendarCollection(int id, String name, String event_desc_sort,
			String event_desc_full, String date, String event_type) {
		super();
		this.id = id;
		this.name = name;
		this.event_desc_sort = event_desc_sort;
		this.event_desc_full = event_desc_full;
		this.date = date;
		this.event_type = event_type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEvent_desc_sort() {
		return event_desc_sort;
	}
	public void setEvent_desc_sort(String event_desc_sort) {
		this.event_desc_sort = event_desc_sort;
	}
	public String getEvent_desc_full() {
		return event_desc_full;
	}
	public void setEvent_desc_full(String event_desc_full) {
		this.event_desc_full = event_desc_full;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
