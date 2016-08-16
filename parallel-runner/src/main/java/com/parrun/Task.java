package com.parrun;

import com.parrun.exceptions.RunnerExceptions;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c-vladmunt on 10/1/2015.
 */
public class Task {

    private Object object;
    private String methodName;
    private List<Object> parameters;
    private static final Logger Log = Logger.getLogger(Task.class);


    /**
     * @param object - Object that contain method
     * @param methodName - Name of the method that should be run
     */
    public Task(Object object, String methodName){
        this.object = object;
        this.methodName = methodName;
        this.parameters = new ArrayList<>();
    }

    /**
     * @param object - Object that contain method
     * @param methodName - Name of the method that should be run
     * @param parameters - Method parameters
     */
    public Task(Object object, String methodName, List<Object> parameters) throws RunnerExceptions {
        this.object = object;
        this.methodName = methodName;

        if(!(parameters == null)){
            this.parameters = new ArrayList<>();
            for (Object parameter : parameters){
                if (!(parameter == null)){
                    this.parameters.add(parameter);
                }else {
                    throw new RunnerExceptions("Failed to build task", new Throwable(String.format("Method %s has null parameter provided",methodName)));
                }
            }
        }else {
            throw new RunnerExceptions("Failed to build task", new Throwable(String.format("Method %s has null parameter provided",methodName)));
        }
    }

    /**
     * @param object - Object that contain method
     * @param methodName - Name of the method that should be run
     * @param parameters - Method parameters
     */
    public Task(Object object, String methodName, Object ... parameters) throws RunnerExceptions {

        this.object = object;
        this.methodName = methodName;

        if(!(parameters == null)){
            this.parameters = new ArrayList<>();
            for (Object parameter : parameters){
                if (!(parameter == null)){
                    this.parameters.add(parameter);
                }else {
                    throw new RunnerExceptions("Failed to build task", new Throwable(String.format("Method %s has null parameter provided",methodName)));
                }
            }
        }else {
            throw new RunnerExceptions("Failed to build task", new Throwable(String.format("Method %s has null parameter provided",methodName)));
        }

    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}
