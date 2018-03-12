package org.mascheraveneziana.zitan.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MeetingDto {
	
	private String name;
	private String room;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date date;
	private Time startTime;
	private Time endTime;
	
	public MeetingDto (String name, String room, Date date, Time startTime, Time endTime) {
		this.name = name;
		this.room = room;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public MeetingDto () {}
	
	
	
}
