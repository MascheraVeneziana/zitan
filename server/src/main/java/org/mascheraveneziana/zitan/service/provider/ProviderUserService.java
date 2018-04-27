package org.mascheraveneziana.zitan.service.provider;

import java.util.List;

import org.mascheraveneziana.zitan.domain.provider.ProviderUser;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface ProviderUserService {

    public ProviderUser getMe(OAuth2AuthenticationToken authentication) throws Exception;

    public ProviderUser getUserById(OAuth2AuthenticationToken authentication, String id) throws Exception;

    public List<ProviderUser> getUsers(OAuth2AuthenticationToken authentication) throws Exception;

}
