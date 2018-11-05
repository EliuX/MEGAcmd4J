package com.github.eliux.mega.auth;

import com.github.eliux.mega.error.MegaLoginException;
import com.github.eliux.mega.MegaSession;

/**
 * Abstraction of authentication mechanisms, used to provide an strategy
 * of creating a MEGA session.
 */
public abstract class MegaAuth {

    abstract public MegaSession login() throws MegaLoginException;
}
