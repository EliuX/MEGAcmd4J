package io.github.eliux.mega.auth;

import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.error.MegaLoginException;

/**
 * Abstraction of authentication mechanisms, used to provide an strategy
 * of creating a MEGA session.
 */
public abstract class MegaAuth {

    abstract public MegaSession login() throws MegaLoginException;
}
