package org.mascheraveneziana.zitan.service.provider;

import java.util.List;

import org.mascheraveneziana.zitan.domain.provider.ProviderResource;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface ProviderResourceService {

    public List<ProviderResource> getResources(OAuth2AuthenticationToken authentication) throws Exception;
    
    public ProviderResource getResourceById(OAuth2AuthenticationToken authentication, String id) throws Exception;

}
