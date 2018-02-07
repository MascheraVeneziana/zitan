package org.mascheraveneziana.zitan.google;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AllFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException, UnsupportedEncodingException {

        HttpServletRequest htreq = (HttpServletRequest) request;
        HttpServletResponse htres = (HttpServletResponse) response;

        HttpSession session = htreq.getSession(false);
        if (session == null) {

            // new user, create session, get google data (redirect)
            String url = GAuth.getInstance().getEncodedAuthUrl();
            htres.sendRedirect(url);

        } else {

            // confirm user name, password
        }
    }

}
