package com.parrun.executor;

import com.parrun.Task;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.Callable;


/**
 * Created by c-vladmunt on 10/15/2015.
 */

public interface IExecutor extends Callable {

    Method getMethod(Task task);

    String getParameterType(Parameter parameter);
}
