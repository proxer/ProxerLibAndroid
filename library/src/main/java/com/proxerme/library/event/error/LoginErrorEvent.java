package com.proxerme.library.event.error;

import android.support.annotation.NonNull;

import com.proxerme.library.connection.ProxerException;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class LoginErrorEvent extends ErrorEvent {
    public LoginErrorEvent(@NonNull ProxerException exception) {
        super(exception);
    }
}
