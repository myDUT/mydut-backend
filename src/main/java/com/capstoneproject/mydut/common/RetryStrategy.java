package com.capstoneproject.mydut.common;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * @author vndat00
 * @since 11/3/2024
 */
public interface RetryStrategy {
    void execute(IntConsumer handler, Consumer<Exception> exceptionConsumer) throws Exception;
}
