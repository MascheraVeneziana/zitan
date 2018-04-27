package org.mascheraveneziana.zitan.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

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
	private List<UserDto> userList;
	
	public MeetingDto (String name, String room, Date date, Time startTime, Time endTime, List<UserDto> userList) {
		this.name = name;
		this.room = room;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userList = userList; 
	}
	
	public MeetingDto () {}
	
	
	
}
