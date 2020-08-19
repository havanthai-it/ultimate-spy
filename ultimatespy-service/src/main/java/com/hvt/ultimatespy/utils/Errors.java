package com.hvt.ultimatespy.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

public class Errors {
    public static Exception INTERNAL_SERVER_ERROR = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Errors");
    public static Exception BAD_REQUEST_EXCEPTION = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request exception");
}
