package io.github.eliux.mega.cmd.cmd;

import java.util.concurrent.Callable;

public abstract class AbstractMegaCmdCaller<T> extends AbstractMegaCmd implements Callable<T> {

}
