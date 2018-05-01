package org.mascheraveneziana.zitan.domain.provider.google;

import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;

public class GoogleAccount extends ProviderAccount {

    private boolean free;

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

}
