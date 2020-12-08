package ar.edu.itba.paw.interfaces.services.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomException;

public class IllegalUserUnfollowException extends CustomException {

    public IllegalUserUnfollowException() {
        super("Illegal user unfollow");
        responseStatus = 400;
    }
}
