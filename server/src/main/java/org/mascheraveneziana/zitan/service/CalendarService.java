package org.mascheraveneziana.zitan.service;

import java.io.IOException;
import java.util.ArrayList;

import java.util.stream.Collectors;
import java.util.*;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.Meeting;
import org.mascheraveneziana.zitan.dto.MeetingDto;
import org.mascheraveneziana.zitan.dto.UserDto;
import org.mascheraveneziana.zitan.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

@Service
public class CalendarService {

	@Autowired
    OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    MeetingRepository meetingRepository;

	public void addEvent(String email, MeetingDto meetingDto, com.google.api.services.calendar.Calendar client) throws IOException {
		Event event = new Event();
		Boolean canSend = true;
	    event.setSummary(meetingDto.getName());
	    String str = meetingDto.getDate().toString() + "T";
	    String utc = "+09:00";
	    DateTime start = new DateTime(str + meetingDto.getStartTime().toString() + utc);

	    event.setStart(new EventDateTime().setDateTime(start));
	    DateTime end = new DateTime(str + meetingDto.getEndTime().toString() + utc);
	    event.setEnd(new EventDateTime().setDateTime(end));
	    ArrayList<EventAttendee> attendeeList = new ArrayList<EventAttendee>();


	    for(UserDto user : meetingDto.getUserList()) {
	    		System.out.println(user.getEmail());
	    		EventAttendee eventAttendee = new EventAttendee();
		    	if(user.getEmail() == null) {
		    		break;
		    	}
		    	eventAttendee.setEmail(user.getEmail());
		    	//任意のやつ
		    	eventAttendee.setOptional(meetingDto.getCanFree());
		    	attendeeList.add(eventAttendee);
	    }
	    EventAttendee eventAttendee = new EventAttendee();
	    eventAttendee.setEmail(meetingDto.getRoom());
	    attendeeList.add(eventAttendee);

	    event.setAttendees(attendeeList);
		client.events().insert(email, event).setSendNotifications(canSend).execute();
	}

    public java.util.List<MeetingDto> getMeetingDtoList() {
        java.util.List<Meeting> meetings = meetingRepository.findAll();
        java.util.List<MeetingDto> meetingDtos = meetings.stream().map(meeting -> {
            MeetingDto dto = translateToDto(meeting);
            return dto;
        }).collect(Collectors.toList());
        return meetingDtos;
    }

    public MeetingDto getMeetingDtoById(Long id) {
        Optional<Meeting> meetingOptional = meetingRepository.findById(id);
        Meeting meeting = meetingOptional.orElse(null);
        if (meeting == null) {
            throw new ZitanException("not found meeting", HttpStatus.NOT_FOUND);
        }

        MeetingDto dto = translateToDto(meeting);
        return dto;
    }

    public MeetingDto updateMeeting(MeetingDto oldDto) {
        // TODO: 実行してきたエンドユーザーにミーティングを更新する権限があるかの確認が必要
        Optional<Meeting> meetingOptional = meetingRepository.findById(oldDto.getId());
        Meeting meeting = meetingOptional.orElse(null);
        if (meeting == null) {
            throw new ZitanException("not found meeting", HttpStatus.NOT_FOUND);
        }

        meeting.setName(oldDto.getName());
        meeting.setRoom(oldDto.getRoom());
        meeting.setDate(oldDto.getDate());
        meeting.setStartTime(oldDto.getStartTime());
        meeting.setEndTime(oldDto.getEndTime());
        // TODO: 登録する必要があるかどうか
//        meeting.setMember(null);
        meeting.setDescription(oldDto.getDescription());
        meeting.setGoal(oldDto.getGoal());

        Meeting newMeeting = meetingRepository.save(meeting);
        MeetingDto newMeetingDto = translateToDto(newMeeting);
        return newMeetingDto;
    }

    public void deleteMeeting(Long id) {
        // TODO: 実行してきたエンドユーザーに引数のIDのミーティングを削除する権限があるかの確認が必要
        Optional<Meeting> meetingOptional = meetingRepository.findById(id);
        Meeting meeting = meetingOptional.orElse(null);
        if (meeting == null) {
            throw new ZitanException("not found meeting", HttpStatus.NOT_FOUND);
        }

        meetingRepository.delete(meeting);
    }

    private MeetingDto translateToDto(Meeting meeting) {
        java.util.List<UserDto> members = meeting.getMember().stream().map(member -> {
            UserDto userDto = new UserDto(member.getEmail());
            return userDto;
        }).collect(Collectors.toList());

        MeetingDto dto = new MeetingDto(
                meeting.getId(),
                meeting.getName(),
                meeting.getRoom(),
                meeting.getDate(),
                meeting.getStartTime(),
                meeting.getEndTime(),
                members,
                meeting.getDescription(),
                meeting.getGoal(),
                // TODO 何を指定すればＯＫ？
                true);
        return dto;
    }

}
