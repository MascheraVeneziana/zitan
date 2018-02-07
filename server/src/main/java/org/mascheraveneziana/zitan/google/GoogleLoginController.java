package org.mascheraveneziana.zitan.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpSession;

import org.mascheraveneziana.zitan.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GoogleLoginController {

    @RequestMapping("/google-login")
    public Object authenticate(HttpSession session,
            @RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
            @RequestParam(name = "error", required = false) String error) throws IOException {

        System.out.println(session.getId());

        if (error != null) {

            // TODO: send error
            System.out.println(error);
            return "";

        } else if (!state.equals(GAuth.getInstance().getState())) {

            // TODO: send error
            return "";

        } else {
            GoogleUser gUser = getGUser(code);

            // TODO: url
            HttpURLConnection http = (HttpURLConnection) new URL("https://www.googleapis.com/plus/v1/people/me/openIdConnect").openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Authorization", "OAuth " + gUser.getAccessToken());
            http.connect();

            int sc = http.getResponseCode();
            String msg = http.getResponseMessage();
            System.out.println(sc);
            System.out.println(msg);

            GoogleUser newUser = null;
            if (sc / 100 == 2) {
                try (
                    InputStream in = http.getInputStream();
                ) {
                    newUser = new ObjectMapper().readValue(in, GoogleUser.class);

                }
                System.out.println(newUser.getSub());
                System.out.println(newUser.getEmail());
                System.out.println(newUser.getName());
                User user = new User();
                user.setName(newUser.getName());
                user.setEmail(newUser.getEmail());
                return user;
            } else {
                throw new RuntimeErrorException(new Error(""));
            }
        }
    }

    private GoogleUser getGUser(String code) throws IOException {
        HttpURLConnection http = (HttpURLConnection) new URL(GAuth.GOOGLE_TOKEN_URL).openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        try (
            OutputStream out = http.getOutputStream();
            PrintStream ps = new PrintStream(out);
        ) {
            ps.print(GAuth.getInstance().getEncodedTokenBody(code));
        }
        http.connect();

        // TODO: log
        int sc = http.getResponseCode();
        String msg = http.getResponseMessage();

        if (sc / 100 == 2) {
            GoogleUser gUser = null;
            try (
                InputStream in = http.getInputStream();
            ) {
                gUser = new ObjectMapper().readValue(in, GoogleUser.class);
            }
            System.out.println(gUser.getAccessToken());
            return gUser;
        } else {
            // TODO: return error
            System.out.println(sc);
            System.out.println(msg);
            throw new RuntimeErrorException(new Error(""));
        }
    }

}
