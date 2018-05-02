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
	private String description;
	private String goal;

	@JsonFormat(pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date date;

	private Time startTime;
	private Time endTime;
	private UserDto mainUser;
	private List<UserDto> members;
	private String providerEventId;

	// not register to database
	private List<UserDto> resources;
	private Boolean notify;

	public MeetingDto () {
	}

	public MeetingDto (Long id, String name, String room, String description, String goal,
	        Date date, Time startTime, Time endTime, UserDto mainUser, List<UserDto> members, String providerEventId,
	        List<UserDto> resources, Boolean notify) {

	    this.id = id;
	    this.name = name;
		this.room = room;
		this.description = description;
		this.goal = goal;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.mainUser = mainUser;
		this.members = members;
		this.providerEventId = providerEventId;
		this.resources = resources;
		this.notify = notify;
	}

}
