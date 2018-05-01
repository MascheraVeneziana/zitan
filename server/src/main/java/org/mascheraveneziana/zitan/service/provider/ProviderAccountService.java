package org.mascheraveneziana.zitan.service.provider;

import java.util.List;

import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public interface ProviderAccountService {

    public ProviderAccount getUserById(OAuth2AuthenticationToken authentication, String id);

    public List<ProviderAccount> getUsers(OAuth2AuthenticationToken authentication);

    public List<ProviderAccount> getResources(OAuth2AuthenticationToken authentication);

}
