package org.mascheraveneziana.zitan.service.provider;

import org.mascheraveneziana.zitan.domain.provider.ProviderMeetingGroup;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public interface ProviderCalendarService {

    public ProviderMeetingGroup getStatus(OAuth2AuthenticationToken authentication, ProviderMeetingGroup group);

}
