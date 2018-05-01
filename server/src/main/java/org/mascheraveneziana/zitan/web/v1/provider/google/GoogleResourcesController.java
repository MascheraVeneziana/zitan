package org.mascheraveneziana.zitan.web.v1.provider.google;

import java.util.List;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.provider.ProviderResource;
import org.mascheraveneziana.zitan.service.provider.google.GoogleResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/provider/google/resources")
public class GoogleResourcesController {

    private GoogleResourceService resourceService = new GoogleResourceService();

    @GetMapping()
    public List<ProviderResource> resources(OAuth2AuthenticationToken authentication)
            throws ZitanException {

        List<ProviderResource> resources = resourceService.getResources(authentication);
        return resources;
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
