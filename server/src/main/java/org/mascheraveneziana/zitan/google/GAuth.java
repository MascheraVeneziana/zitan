package org.mascheraveneziana.zitan.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.Random;

public class GAuth {

    public static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    public static final String GOOGLE_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";

    private static GAuth instance;

    public static GAuth getInstance() throws IOException {
        if (instance == null) {
            instance = new GAuth();
        }
        return instance;
    }

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String openIdRealm;
    private String state;
    private String encodedAuthUrl;

    private GAuth() throws IOException {
        Properties prop = new Properties();
        try (
            InputStream in = GAuth.class.getClassLoader().getResourceAsStream("application.properties");
        ) {
            prop.load(in);
        }
        this.clientId = prop.getProperty("gauth.clientid");
        this.clientSecret = prop.getProperty("gauth.clientsecret");
        this.redirectUri = prop.getProperty("gauth.redirecturi");
        this.openIdRealm = prop.getProperty("gauth.openidrealm");

        // create state
        char[] cs = new char[10];
        Random random = new Random();
        String src = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        for (int i = 0; i < cs.length; i++) {
            cs[i] = src.charAt(random.nextInt(src.length()));
        }
        this.state = new String(cs);

        this.encodedAuthUrl = new StringBuffer().append(GOOGLE_AUTH_URL)
                .append("?client_id=").append(URLEncoder.encode(clientId, "UTF-8"))
                // TODO: リストで持って作成した方が見栄えはいい
                .append("&scope=").append("openid+email+profile")
                .append("&response_type=code")
                .append("&redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8"))
                .append("&state=").append(state)
                .append("&openid_realm=").append(URLEncoder.encode(openIdRealm, "UTF-8"))
                .toString();
    }

    public String getState() {
        return state;
    }

    public String getEncodedAuthUrl() {
        return encodedAuthUrl;
    }

    public String getEncodedTokenBody(String code) throws UnsupportedEncodingException {
        String body = new StringBuffer()
                .append("code=").append(code)
                .append("&client_id=").append(clientId)
                .append("&client_secret=").append(clientSecret)
                .append("&redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8"))
                .append("&grant_type=authorization_code")
                .toString();
        return body;
    }

}
