package com.info.eassignment.users.exception;

public class EntityAttributeAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -4210472616677941415L;

    public EntityAttributeAlreadyExistsException() {
        super();
    }

    public EntityAttributeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAttributeAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAttributeAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
