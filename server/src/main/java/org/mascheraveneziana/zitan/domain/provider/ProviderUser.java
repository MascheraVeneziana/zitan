package org.mascheraveneziana.zitan.domain.provider;

import lombok.Data;

/**
 * GoogleとかYahooとかのサードパーティから取得したユーザー情報を詰めるのに使ってください。
 * 継承して（extends）使ってください。
 *
 */
@Data
public class ProviderUser {

    private String id;
    private String name;
    private String email;

}
