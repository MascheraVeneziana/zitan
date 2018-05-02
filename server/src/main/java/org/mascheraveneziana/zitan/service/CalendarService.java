package org.mascheraveneziana.zitan.service;

import java.io.IOException;
import java.sql.Time;
import java.util.stream.Collectors;
import java.util.*;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.Meeting;
import org.mascheraveneziana.zitan.domain.User;
import org.mascheraveneziana.zitan.dto.MeetingDto;
import org.mascheraveneziana.zitan.dto.UserDto;
import org.mascheraveneziana.zitan.repository.MeetingRepository;
import org.mascheraveneziana.zitan.service.provider.ProviderCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
    @Autowired
    UserService userService;
    @Autowired
    ProviderCalendarService calendarService;

	public void addEvent(OAuth2AuthenticationToken authentication, String email, MeetingDto meetingDto) throws IOException {
		Event event = new Event();

		Boolean canSend = true;

		event.setSummary(meetingDto.getName());

		DateTime start = getDateTime(meetingDto.getDate(), meetingDto.getStartTime());
	    event.setStart(new EventDateTime().setDateTime(start));

	    DateTime end = getDateTime(meetingDto.getDate(), meetingDto.getEndTime());
	    event.setEnd(new EventDateTime().setDateTime(end));

	    List<EventAttendee> attendeeList = getEventAttendeeList(meetingDto.getUserList());

	    EventAttendee eventAttendee = new EventAttendee();
	    eventAttendee.setEmail(meetingDto.getRoom());
	    attendeeList.add(eventAttendee);

	    event.setAttendees(attendeeList);

	    Event newEvent = calendarService.createEvent(authentication, event);
	}

    public java.util.List<MeetingDto> getMeetingDtoList() {
        java.util.List<Meeting> meetings = meetingRepository.findAll();
        java.util.List<MeetingDto> meetingDtoList = meetings.stream().map(meeting -> {
            MeetingDto dto = translateToDto(meeting);
            return dto;
        }).collect(Collectors.toList());
        return meetingDtoList;
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

    public MeetingDto updateMeeting(OAuth2AuthenticationToken authentication, MeetingDto dto) {
        // ミーティングが存在するか
        Optional<Meeting> meetingOptional = meetingRepository.findById(dto.getId());
        Meeting meeting = meetingOptional.orElse(null);
        if (meeting == null) {
            throw new ZitanException("not found meeting", HttpStatus.NOT_FOUND);
        }

        // 更新は主催者のみが可能
        User user = userService.getUser(authentication.getPrincipal().getName());
        UserDto userDto = dto.getMainUser();
        if (!user.getId().equals(userDto.getId())) {
            throw new ZitanException("no permission", HttpStatus.FORBIDDEN);
        }

        // プロバイダーのデータを更新（プロバイダーで更新できなくても処理は続行する）
        try {
            DateTime startDateTime = getDateTime(dto.getDate(), dto.getStartTime());
            DateTime endDateTime = getDateTime(dto.getDate(), dto.getEndTime());

            Event event = new Event()
                    .setSummary(dto.getName())
                    .setStart(new EventDateTime().setDateTime(startDateTime))
                    .setEnd(new EventDateTime().setDateTime(endDateTime))
                    .setAttendees(getEventAttendeeList(dto.getUserList()));

            Event newEvent = calendarService.updateEvent(authentication, event);
            meeting.setProviderEventId(newEvent.getId());
        } catch (Exception e) {
            // TODO log warn
            e.printStackTrace();
        }

        meeting.setName(dto.getName());
        meeting.setRoom(dto.getRoom());
        meeting.setDate(dto.getDate());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());
        meeting.setMember(new HashSet<User>(getUserList(dto.getUserList())));
        meeting.setDescription(dto.getDescription());
        meeting.setGoal(dto.getGoal());

        // アプリケーションのデータベースを更新
        Meeting newMeeting = meetingRepository.save(meeting);
        MeetingDto newMeetingDto = translateToDto(newMeeting);
        return newMeetingDto;
    }

    public void deleteMeeting(OAuth2AuthenticationToken authentication, Long id) {
        // ミーティングが存在するか
        Optional<Meeting> meetingOptional = meetingRepository.findById(id);
        Meeting meeting = meetingOptional.orElse(null);
        if (meeting == null) {
            throw new ZitanException("not found", HttpStatus.NOT_FOUND);
        }

        // 削除は主催者のみが可能
        User user = userService.getUser(authentication.getPrincipal().getName());
        if (!user.getId().equals(meeting.getMainUser().getId())) {
            throw new ZitanException("no permission", HttpStatus.NOT_FOUND);
        }

        // アプリケーションのデータベースから削除
        meetingRepository.delete(meeting);

        // プロバイダーのデータを削除（プロバイダーで削除できなくても処理は続行する）
        try {
            calendarService.deleteEvent(authentication, meeting.getProviderEventId());
        } catch (Exception e) {
            // TODO log warn  - could not delete
            e.printStackTrace();
        }
    }

    private DateTime getDateTime(Date date, Time time) {
        StringBuffer sb = new StringBuffer().append(date.toString()).append("T").append(time.toString()).append("+09:00");
        DateTime dateTime = new DateTime(sb.toString());
        return dateTime;
    }

    private List<EventAttendee> getEventAttendeeList(List<UserDto> userDtoList) {
        List<EventAttendee> list = new ArrayList<>();
        for (UserDto userDto : userDtoList) {
            EventAttendee eventAttendee = new EventAttendee();

            // メールアドレスが無かったらそのユーザーだけスキップ
            if(userDto.getEmail() == null) {
                // TODO log warn;
                continue;
            }
            eventAttendee.setEmail(userDto.getEmail());

            // TODO 出席が必須か任意かはユーザー自身のデータかもしれない
            //任意のやつ
//            eventAttendee.setOptional(meetingDto.getCanFree());
            list.add(eventAttendee);
        }
        return list;
    }

    private List<User> getUserList(List<UserDto> userDtoList) {
        List<User> list = new ArrayList<>();
        for (UserDto userDto : userDtoList) {
            User user = userService.getUser(userDto.getEmail());
            if (user == null) {
                continue;
                // TODO: log warn - user not found
            }
            list.add(user);
        }
        return list;
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
