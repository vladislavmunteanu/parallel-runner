package com.parrun.exceptions;

/**
 * Created by c-vladmunt on 9/29/2015.
 */
public class RunnerExceptions extends Exception {

    public RunnerExceptions(String message){
        super(message);
    }

    public RunnerExceptions(String message, Throwable cause){
        super(message,cause);
    }

}
