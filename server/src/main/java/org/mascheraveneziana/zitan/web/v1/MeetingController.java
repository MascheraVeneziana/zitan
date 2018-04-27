package org.mascheraveneziana.zitan.web.v1;

import java.io.IOException;
import java.util.Map;

import org.mascheraveneziana.zitan.domain.User;
import org.mascheraveneziana.zitan.dto.MeetingDto;
import org.mascheraveneziana.zitan.repository.MeetingRepository;
import org.mascheraveneziana.zitan.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
@RequestMapping("/api/v1/meeting")
public class MeetingController {
	private static final String APPLICATION_NAME = "zitan";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static  com.google.api.services.calendar.Calendar client;
	
	@Autowired
    OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    MeetingRepository mRepository;
    @Autowired
    CalendarService calendarService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String getMeeting() {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
          json = mapper.writeValueAsString(mRepository.findAll());
          return json;
        } catch (JsonProcessingException e) {
          e.printStackTrace();
          return "{\"error\": \"Can not read Meeting Data.\"}";
        }
    }

    
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String createMeeting(@RequestBody MeetingDto meetingDto, OAuth2AuthenticationToken authentication) throws IOException {
    	OAuth2AuthorizedClient authorizedClient = 
				this.authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		GoogleCredential credential = new GoogleCredential().setAccessToken(authorizedClient.getAccessToken().getTokenValue());
		client = new com.google.api.services.calendar.Calendar.Builder(
				new NetHttpTransport(), JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
		
		Map<String, Object> map = authentication.getPrincipal().getAttributes();
        User user = new User();
        user.setName((String) map.get("name"));
        user.setEmail((String) map.get("email"));
        calendarService.addEvent(user.getEmail(), meetingDto, client);
//    	String str = "{ \"name\": \"Zitan\", \"version\": \"1.0.0\" }";
        String str =  "OK";
        return str;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateMeeting() {
        String str = "{ \"name\": \"Zitan\", \"version\": \"1.0.0\" }";
        return str;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteMeeting() {
        String str = "{ \"name\": \"Zitan\", \"version\": \"1.0.0\" }";
        return str;
    }

}
