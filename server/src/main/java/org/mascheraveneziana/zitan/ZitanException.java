package org.mascheraveneziana.zitan;

import org.springframework.http.HttpStatus;

public class ZitanException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;

    public ZitanException(Throwable e, HttpStatus httpStatus) {
        super(e);
        this.httpStatus = httpStatus;
    }

    public ZitanException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus= httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
