package com.github.eliux.mega.cmd;

import java.util.concurrent.Callable;

public abstract class AbstractMegaCmdCaller<T> extends AbstractMegaCmd implements Callable<T> {

}
