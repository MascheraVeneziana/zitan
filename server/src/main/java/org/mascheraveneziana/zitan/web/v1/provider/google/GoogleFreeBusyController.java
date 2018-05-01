package org.mascheraveneziana.zitan.web.v1.provider.google;

import java.util.List;

import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;
import org.mascheraveneziana.zitan.domain.provider.google.GoogleFreeBusyForm;
import org.mascheraveneziana.zitan.service.provider.ProviderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ap1/v1/provider/google/status")
public class GoogleFreeBusyController {

    @Autowired
    private ProviderStatusService statusService;

    @PostMapping()
    public List<ProviderAccount> status(OAuth2AuthenticationToken authentication,
            @RequestBody GoogleFreeBusyForm form) {

        List<ProviderAccount> accountList = statusService.getStatus(authentication, form.getTimeMin(), form.getTimeMax(), form.getMailList());
        return accountList;
    }

}
