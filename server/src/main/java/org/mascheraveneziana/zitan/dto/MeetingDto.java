package org.mascheraveneziana.zitan.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MeetingDTO {
	
	private String name;
	private String room;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public MeetingDTO (String name, String room, LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.name = name;
		this.room = room;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public MeetingDTO () {}
	
	
	
}
