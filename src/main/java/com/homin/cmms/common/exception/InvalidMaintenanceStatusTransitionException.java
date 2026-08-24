package com.homin.cmms.common.exception;

public class InvalidMaintenanceStatusTransitionException extends RuntimeException {
    public InvalidMaintenanceStatusTransitionException(String message) {
        super(message);
    }
}
