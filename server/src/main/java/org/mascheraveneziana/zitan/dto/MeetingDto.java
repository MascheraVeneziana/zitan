package org.mascheraveneziana.zitan.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MeetingDto {

    private Long id;
	private String name;
	private String room;

	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date date;
	private Time startTime;
	private Time endTime;
	private List<UserDto> userList;
	private 	String description;
	private String goal;
	private Boolean canFree;

	public MeetingDto (Long id, String name, String room, Date date, Time startTime, Time endTime, List<UserDto> userList, String description, String goal, Boolean canFree) {
	    this.id = id;
	    this.name = name;
		this.room = room;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userList = userList;
		this.description = description;
		this.goal = goal;
		this.canFree = canFree;
	}

	public MeetingDto () {}



}
