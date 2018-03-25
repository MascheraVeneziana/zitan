package org.mascheraveneziana.zitan.web;

import java.io.IOException;
import java.util.Date;

import java.util.Map;
import java.util.TimeZone;


import org.mascheraveneziana.zitan.domain.User;
import org.mascheraveneziana.zitan.dto.MeetingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;


@RestController
public class CalendarController {
	private static final String APPLICATION_NAME = "zitan";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static  com.google.api.services.calendar.Calendar client;
	
	@Autowired
    OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;
   
	@PostMapping("/add")
	public ResponseEntity<String> addEvent(@RequestBody MeetingDto meetingDto, OAuth2AuthenticationToken authentication) {
		try {
			OAuth2AuthorizedClient authorizedClient = 
					this.authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
			GoogleCredential credential = new GoogleCredential().setAccessToken(authorizedClient.getAccessToken().getTokenValue());
			client = new com.google.api.services.calendar.Calendar.Builder(
					new NetHttpTransport(), JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
			
			Map<String, Object> map = authentication.getPrincipal().getAttributes();
	        User user = new User();
	        user.setName((String) map.get("name"));
	        user.setEmail((String) map.get("email"));
	        addEvent(user.getEmail(), meetingDto);
		}catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return new ResponseEntity<>(meetingDto.toString(), HttpStatus.OK);
	}
	
//	private static CalendarList showCalendars() throws IOException {
//	    CalendarList feed = client.calendarList().list().execute();
//	    return feed;
//	  }
	
	private static void addEvent(String email, MeetingDto meetingDto) throws IOException {
		Event event = new Event();
	    event.setSummary(meetingDto.getName());
	    String str = meetingDto.getDate().toString() + "T";
	    String utc = "+09:00";
	    DateTime start = new DateTime(str + meetingDto.getStartTime().toString() + utc);
	    
	    event.setStart(new EventDateTime().setDateTime(start));
	    DateTime end = new DateTime(str + meetingDto.getEndTime().toString() + utc);
	    event.setEnd(new EventDateTime().setDateTime(end));
		client.events().insert(email, event).execute();
	}
	
	
}
