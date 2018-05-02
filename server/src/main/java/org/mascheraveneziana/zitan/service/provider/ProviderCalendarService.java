package org.mascheraveneziana.zitan.service.provider;

import org.mascheraveneziana.zitan.domain.provider.ProviderMeetingGroup;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

// TODO Google固有になっている
import com.google.api.services.calendar.model.Event;

@Service
public interface ProviderCalendarService {

    public Event getEvent(OAuth2AuthenticationToken authentication, String eventId);

    public Event createEvent(OAuth2AuthenticationToken authentication, Event event, boolean notify);

    public Event updateEvent(OAuth2AuthenticationToken authentication, Event event, boolean notify);

    public void deleteEvent(OAuth2AuthenticationToken authentication, String eventId, boolean notify);

    public ProviderMeetingGroup getStatus(OAuth2AuthenticationToken authentication, ProviderMeetingGroup group);

}
