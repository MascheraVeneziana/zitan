package org.mascheraveneziana.zitan.service.provider;

import java.util.Date;
import java.util.List;

import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface ProviderStatusService {

    public List<ProviderAccount> getStatus(OAuth2AuthenticationToken authentication, Date timeMin, Date timeMax, List<String> idList);

}
