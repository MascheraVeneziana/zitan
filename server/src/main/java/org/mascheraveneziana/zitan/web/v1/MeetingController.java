package org.mascheraveneziana.zitan.web.v1;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.User;
import org.mascheraveneziana.zitan.dto.MeetingDto;
import org.mascheraveneziana.zitan.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/meeting")
public class MeetingController {

    @Autowired
    private CalendarService calendarService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MeetingDto> getMeetingDtoList() {
        List<MeetingDto> meetingDtoList = calendarService.getMeetingDtoList();
        return meetingDtoList;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String createMeeting(@RequestBody MeetingDto meetingDto, OAuth2AuthenticationToken authentication) throws IOException {

		Map<String, Object> map = authentication.getPrincipal().getAttributes();
        User user = new User();
        user.setName((String) map.get("name"));
        user.setEmail((String) map.get("email"));
        calendarService.addEvent(authentication, user.getEmail(), meetingDto);
        return "ok";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public MeetingDto getMeeting(@PathVariable Long id) {
        MeetingDto meetingDto = calendarService.getMeetingDtoById(id);
        return meetingDto;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/id/{id}")
    public MeetingDto updateMeeting(OAuth2AuthenticationToken authentication,
            @PathVariable Long id, MeetingDto dto) {

        MeetingDto updated = calendarService.updateMeeting(authentication, dto);
        return updated;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/id/{id}")
    public void deleteMeeting(OAuth2AuthenticationToken authentication,
            @PathVariable Long id) {

        calendarService.deleteMeeting(authentication, id);
    }

    @ExceptionHandler(ZitanException.class)
    public ResponseEntity<String> handleZitanException(ZitanException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Throwable e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
