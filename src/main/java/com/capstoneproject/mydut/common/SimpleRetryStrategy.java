package com.capstoneproject.mydut.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * @author vndat00
 * @since 11/3/2024
 */

@Log4j2
@RequiredArgsConstructor
public class SimpleRetryStrategy implements RetryStrategy {
    private final int maxRetries;
    private final long retryDelayMillis;

    @Override
    public void execute(IntConsumer handler, Consumer<Exception> exceptionHandler) throws Exception {
        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                handler.accept(retryCount);
                return;
            } catch (Exception exception) {
                exceptionHandler.accept(exception);
                log.error("Error when execute function {}", exception.getMessage());
            }
            retryCount++;
            Thread.sleep(retryDelayMillis);
        }
        throw new Exception("Execute failed after " + maxRetries + " retries.");
    }
}
