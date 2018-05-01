package org.mascheraveneziana.zitan.domain.provider.google;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GoogleFreeBusyForm {

    private Date timeMin;
    private Date timeMax;
    private List<String> mailList;

}
