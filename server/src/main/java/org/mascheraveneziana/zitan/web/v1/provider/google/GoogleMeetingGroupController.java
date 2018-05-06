package org.mascheraveneziana.zitan.web.v1.provider.google;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.provider.ProviderMeetingGroup;
import org.mascheraveneziana.zitan.service.provider.ProviderCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ap1/v1/provider/google/meeting/openable")
public class GoogleMeetingGroupController {

    @Autowired
    ProviderCalendarService calendarService;

    @PostMapping
    public ProviderMeetingGroup status(OAuth2AuthenticationToken authentication, @RequestBody ProviderMeetingGroup group) {
        ProviderMeetingGroup resultGroup = calendarService.getStatus(authentication, group);
        return resultGroup;
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
