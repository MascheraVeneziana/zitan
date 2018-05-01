package org.mascheraveneziana.zitan.service.provider.google;

import java.util.List;
import java.util.stream.Collectors;

import org.mascheraveneziana.zitan.domain.provider.ProviderUser;
import org.mascheraveneziana.zitan.domain.provider.google.GoogleUser;
import org.mascheraveneziana.zitan.service.provider.ProviderUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;

public class GoogleUserService implements ProviderUserService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    /**
     * エンドユーザーのGoogleユーザーアカウントの情報を取得する
     */
    @Override
    public ProviderUser getMe(OAuth2AuthenticationToken authentication) throws Exception {
        Directory directoryService = getDirectoryService(authentication);

        String myId = authentication.getPrincipal().getName();

        // https://developers.google.com/admin-sdk/directory/v1/reference/users/get
        User googleUser = directoryService.users().get(myId)
                .setViewType("domain_public")
                .execute();

        ProviderUser user = new GoogleUser();
        user.setId(googleUser.getId());
        user.setName(googleUser.getName().getFullName());
        user.setEmail(googleUser.getPrimaryEmail());

        return user;
    }

    /**
     * 引数に指定したGoogleのIDを持つGoogleユーザーアカウントの情報を取得する
     */
    @Override
    public ProviderUser getUserById(OAuth2AuthenticationToken authentication, String id) throws Exception {
        Directory directoryService = getDirectoryService(authentication);

        // https://developers.google.com/admin-sdk/directory/v1/reference/users/get
        User googleUser = directoryService.users().get(id)
                .setViewType("domain_public")
                .execute();

        ProviderUser user = new GoogleUser();
        user.setId(googleUser.getId());
        user.setName(googleUser.getName().getFullName());
        user.setEmail(googleUser.getPrimaryEmail());

        return user;
    }

    /**
     * （このクラス特有のメソッド）
     * 引数に指定したEmailアドレスを持つGoogleユーザーアカウントを取得する
     * @param authentication
     * @param email Googleアカウントのプライマリーメールアドレス
     * @return
     * @throws Exception
     */
    public ProviderUser getUserByEmail(OAuth2AuthenticationToken authentication, String email) throws Exception {
        Directory directoryService = getDirectoryService(authentication);

        // https://developers.google.com/admin-sdk/directory/v1/reference/users/get
        User googleUser = directoryService.users().get(email)
                .setViewType("domain_public")
                .execute();

        ProviderUser user = new GoogleUser();
        user.setId(googleUser.getId());
        user.setName(googleUser.getName().getFullName());
        user.setEmail(googleUser.getPrimaryEmail());

        return user;
    }

    /**
     * 社員全員のGoogleユーザーアカウントの情報を取得する
     */
    @Override
    public List<ProviderUser> getUsers(OAuth2AuthenticationToken authentication) throws Exception {
        Directory directoryService = getDirectoryService(authentication);

        // https://developers.google.com/admin-sdk/directory/v1/reference/users/list
        Users googleUsers = directoryService.users().list()
                .setDomain("unirita.co.jp")
                .setMaxResults(500)
                .setOrderBy("email")
                .setProjection("full")
                .setViewType("domain_public").execute();

        List<ProviderUser> userList = googleUsers.getUsers()
                .stream().map(googleUser -> {
                    ProviderUser user = new GoogleUser();
                    user.setId(googleUser.getId());
                    user.setName(googleUser.getName().getFullName());
                    user.setEmail(googleUser.getPrimaryEmail());
                    return user;
                }).collect(Collectors.toList());

        return userList;
    }

    private Directory getDirectoryService(OAuth2AuthenticationToken authentication) {
        String id = authentication.getPrincipal().getName();
        OAuth2AuthorizedClient authorizedClient =
                authorizedClientService.loadAuthorizedClient("google", id);
        Credential credential = new GoogleCredential()
                .setAccessToken(authorizedClient.getAccessToken().getTokenValue());

        Directory directoryService = new Directory.Builder(
                new NetHttpTransport(), new JacksonFactory(), credential)
                // TODO: read from properties file
                // .setApplicationName("")
                .build();
        return directoryService;
    }

}
