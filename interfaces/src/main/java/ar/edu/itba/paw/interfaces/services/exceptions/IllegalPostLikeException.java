package ar.edu.itba.paw.interfaces.services.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomException;

public class IllegalPostLikeException extends CustomException {

    public IllegalPostLikeException() {
        super("Illegal post like");
        responseStatus = 400;
    }
}
