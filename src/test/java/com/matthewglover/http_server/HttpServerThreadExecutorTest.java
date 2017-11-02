package com.matthewglover.http_server;

import com.matthewglover.socket.HttpServerSocketDouble;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class HttpServerThreadExecutorTest {

    @Test
    public void name() {
        ExecutorService executorService = new SynchronousExecutorService();
        HttpServerSocketDouble httpServerSocketDouble = new HttpServerSocketDouble();

        HttpServerThreadExecutor httpServerThreadExecutor = new HttpServerThreadExecutor(httpServerSocketDouble, executorService);

        httpServerThreadExecutor.execute();
        assertEquals(1, httpServerSocketDouble.connectCallCount);
        assertEquals(1, httpServerSocketDouble.runCallCount);
    }


    public class SynchronousExecutorService implements ExecutorService {

        @Override
        public void shutdown() {
        }

        @Override
        public List<Runnable> shutdownNow() {
            return null;
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return null;
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return null;
        }

        @Override
        public Future<?> submit(Runnable task) {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}