package com.parrun;

import com.parrun.exceptions.RunnerExceptions;
import com.parrun.executor.Executor;
import com.parrun.executor.ExecutorReturn;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by c-vladmunt on 9/29/2015.
 */
public class Runner {

    private static final Logger Log = Logger.getLogger(Runner.class);


    /**
     * @param tasks - methods to be executed
     * @throws RunnerExceptions
     */
    public void runParallelTasks(Task...tasks) throws RunnerExceptions {

        final ExecutorService executorService = Executors.newFixedThreadPool(tasks.length);
        List<Future<Void>> taskFutures = new ArrayList<>();

        for (Task task : tasks){
            taskFutures.add(executorService.submit(new Executor(task)));

        }
        runTasksFuture(taskFutures);
        executorService.shutdown();
    }

    /**
     * @param tasks - methods to be executed
     * @throws RunnerExceptions
     */
    public void runParallelTasks(List<Task> tasks) throws RunnerExceptions {

        final ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());
        List<Future<Void>> taskFutures = new ArrayList<>();

        for (Task task : tasks){
            taskFutures.add(executorService.submit(new Executor(task)));

        }
        runTasksFuture(taskFutures);
        executorService.shutdown();
    }

    /**
     * @param tasks - methods to be executed
     * @return - will return a list of objects which are returned by the methods
     * @throws RunnerExceptions
     */
    public List<Object> runParallelTasksWithReturn(List<Task> tasks) throws RunnerExceptions {

        List<Object> objectList = new ArrayList<>();

        final ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());
        List<Future<?>> taskFutures = new ArrayList<>();

        for (Task task : tasks){
            taskFutures.add(executorService.submit(new ExecutorReturn(task)));
        }

        for (Future<?> taskFuture : taskFutures) {
            try {
                objectList.add(taskFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                Log.error("Failed to run task", e);
                throw new RunnerExceptions("Failed to run task", e);
            }
        }
        executorService.shutdown();
        return objectList;
    }

    /**
     * @param tasks - methods to be executed
     * @throws RunnerExceptions
     */
    public void runParallelTasksNoWaits(List<Task> tasks) throws RunnerExceptions {

        final ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());

        for (Task task : tasks){
            executorService.submit(new Executor(task));
        }

        executorService.shutdown();

    }

    /**
     * @param tasks - methods to be executed
     * @throws RunnerExceptions
     */
    public void runParallelTasksNoWaits(Task...tasks) throws RunnerExceptions {

        final ExecutorService executorService = Executors.newFixedThreadPool(tasks.length);

        for (Task task : tasks){
            executorService.submit(new Executor(task));
        }

        executorService.shutdown();

    }

    /**
     * @param tasks - methods to be executed
     * @param methodName - method to wait for
     * @throws RunnerExceptions
     */
    public void runParallelTasksWaitFor(List<Task> tasks, String methodName) throws RunnerExceptions {

        if (methodName == null || Objects.equals(methodName, "")) {
            throw new RunnerExceptions("Use other way to run tasks", new Throwable("There are no methods to wait for"));
        }

        final ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());
        List<Future<Void>> taskFutures = new ArrayList<>();

        for (Task task : tasks) {

            if (Objects.equals(task.getMethodName(), methodName)) {
                taskFutures.add(executorService.submit(new Executor(task)));
            } else {
                executorService.submit(new Executor(task));
            }
        }

        runTasksFuture(taskFutures);
        executorService.shutdown();
    }

    /**
     * @param tasks - methods to be executed
     * @param methodsName - methods to wait for
     * @throws RunnerExceptions
     */
    public void runParallelTasksWaitFor(List<Task> tasks, List<String> methodsName) throws RunnerExceptions {

        if (methodsName == null) {
            throw new RunnerExceptions("Use other way to run tasks", new Throwable("There are no methods to wait for"));
        }

        final ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());
        List<Future<Void>> taskFutures = new ArrayList<>();

        for (Task task : tasks) {

            if (methodsName.contains(task.getMethodName())) {
                taskFutures.add(executorService.submit(new Executor(task)));
            } else {
                executorService.submit(new Executor(task));
            }
        }

        runTasksFuture(taskFutures);
        executorService.shutdown();
    }

    private void runTasksFuture(List<Future<Void>> taskFutures) throws RunnerExceptions{
        for (Future<Void> taskFuture : taskFutures) {
            try {
                taskFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                Log.error("Failed to run task", e);
                throw new RunnerExceptions("Failed to run task", e);
            }
        }
    }


}



