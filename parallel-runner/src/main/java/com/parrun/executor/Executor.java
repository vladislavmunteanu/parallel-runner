package com.parrun.executor;

import com.parrun.exceptions.RunnerExceptions;
import com.parrun.Task;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;


/**
 * Created by c-vladmunt on 10/7/2015.
 */
public class Executor implements IExecutor {

    private static final Logger Log = Logger.getLogger(Executor.class);

    private Task task;

    public Executor(Task task){
        this.task = task;
    }

    @Override
    public Void call() throws RunnerExceptions {

        Method method;

        if (task.getParameters() == null || task.getParameters().isEmpty()){
            try {
                method = task.getObject().getClass().getMethod(task.getMethodName());
                Log.info(String.format("%s execute method %s", Thread.currentThread().getName(), method.getName()));
                method.invoke(task.getObject());
                Log.info(String.format("%s finished method %s", Thread.currentThread().getName(), method.getName()));
            } catch (NoSuchMethodException e) {
                throw new RunnerExceptions(String.format("Could not find %s", task.getMethodName()),e);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RunnerExceptions(String.format("Could not execute %s", task.getMethodName()),e);
            }
        }else {
            method = getMethod(task);
            if (method != null) {
                try {
                    Log.info(String.format("%s execute method %s", Thread.currentThread().getName(), method.getName()));
                    method.invoke(task.getObject(), task.getParameters().toArray());
                    Log.info(String.format("%s finished method %s", Thread.currentThread().getName(), method.getName()));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RunnerExceptions(String.format("Could not execute %s", task.getMethodName()),e);
                }
            }else {
                throw new RunnerExceptions(String.format("Could not find %s", task.getMethodName()));
            }
        }
        return null;
    }

    @Override
    public Method getMethod(Task task) {

        Method[] methods = task.getObject().getClass().getMethods();

        Method methodToReturn = null;

        for (Method method : methods) {
            if (Objects.equals(method.getName(), task.getMethodName()) && method.getParameters().length == task.getParameters().size()) {
                Parameter[] parameters = method.getParameters();
                Object[] objectParameters = task.getParameters().toArray();
                int check = 0;
                int i = 0;
                while (i < objectParameters.length) {
                    if (Objects.equals((getParameterType(parameters[i])), objectParameters[i].getClass().getTypeName())) {
                        check++;
                    }
                    i++;
                }

                if (check == objectParameters.length) {
                    methodToReturn = method;
                }
            }
        }
        return methodToReturn;
    }

    @Override
    public String getParameterType(Parameter parameter){

        switch (parameter.getType().getName()){
            case "byte":
                return "java.lang.Byte";
            case "int":
                return "java.lang.Integer";
            case "short":
                return "java.lang.Short";
            case "char":
                return "java.lang.Character";
            case "double":
                return "java.lang.Double";
            case "long":
                return "java.lang.Long";
            case "float":
                return "java.lang.Float";
            case "boolean":
                return "java.lang.Boolean";
        }
        return parameter.getType().getTypeName();
    }

}
