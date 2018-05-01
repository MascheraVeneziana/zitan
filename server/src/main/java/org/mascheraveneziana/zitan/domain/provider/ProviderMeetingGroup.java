package org.mascheraveneziana.zitan.domain.provider;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProviderMeetingGroup {

    private Date timeMin;
    private Date timeMax;
    private boolean openable;
    private List<ProviderAccount> accounts;

}
