package com.crocobet.usermanagement.exception;

import com.crocobet.usermanagement.util.ExceptionUtil;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String resource;
    private final String Identifier;

    public NotFoundException(String resource, String Identifier) {
        super(ExceptionUtil.buildMessageFrom(resource, Identifier));
        this.resource = resource;
        this.Identifier = Identifier;
    }
}
