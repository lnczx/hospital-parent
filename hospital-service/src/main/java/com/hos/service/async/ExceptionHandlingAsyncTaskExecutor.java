package com.hos.service.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.task.AsyncTaskExecutor;

public class ExceptionHandlingAsyncTaskExecutor implements AsyncTaskExecutor {
	private AsyncTaskExecutor executor;

	public ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor executor) {
		this.executor = executor;
	}

	// //用独立的线程来包装，@Async其本质就是如此
	@Override
	public void execute(Runnable task) {
		executor.execute(createWrappedRunnable(task));
	}

	@Override
	public void execute(Runnable task, long startTimeout) {
		// 用独立的线程来包装，@Async其本质就是如此
		executor.execute(createWrappedRunnable(task), startTimeout);
	}

	@Override
	public Future submit(Runnable task) {
		return executor.submit(createWrappedRunnable(task));
		// 用独立的线程来包装，@Async其本质就是如此。
	}

	@Override
	public Future submit(final Callable task) {
		// 用独立的线程来包装，@Async其本质就是如此。
		return executor.submit(createCallable(task));
	}

	private Callable createCallable(final Callable task) {
		return new Callable() {
			@Override
			public T call() throws Exception {
				try {
					return (T) task.call();
				} catch (Exception ex) {
					handle(ex);
					throw ex;
				}
			}
		};
	}

	private Runnable createWrappedRunnable(final Runnable task) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					task.run();
				} catch (Exception ex) {
					handle(ex);
				}
			}
		};
	}

	private void handle(Exception ex) {
		// 具体的异常逻辑处理的地方
		System.err.println("Error during @Async execution: " + ex);
	}
}
