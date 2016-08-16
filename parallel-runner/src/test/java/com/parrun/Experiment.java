package com.parrun;

import com.parrun.exceptions.RunnerExceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c-vladmunt on 10/15/2015.
 */
public class Experiment {


    public static void main(String[] args) throws InterruptedException, RunnerExceptions {

        long startTime = System.currentTimeMillis();
/*---------------------------------------------------------*/

        Experiment experiment = new Experiment();



       /*--- To run methods in simple way uncomment following 3 lines ---*/

         experiment.method_1();
        experiment.method_2("Hello");
        experiment.method_3();


    /*--- To run methods using framework uncomment following lines ---*/

       /* Runner runner = new Runner();
        List<Task> methods = new ArrayList<>();
        Task method_1 = new Task(experiment,"method_1");
        Task method_2 = new Task(experiment,"method_2","Hello");
        Task method_3 = new Task(experiment,"method_3");

        methods.add(method_1);
        methods.add(method_2);
        methods.add(method_3);

        runner.runParallelTasks(methods);*/

/*---------------------------------------------------------*/
        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000;
        System.out.println(("Done in " + duration + " seconds"));


    }


    public void method_1() throws InterruptedException {

        Thread.sleep(8000);
        System.out.println("method_1 executed");

    }

    public void method_2(String message) throws InterruptedException {

        Thread.sleep(10000);
        System.out.println(message);
        System.out.println("method_2 executed");
    }

    public void method_3() throws InterruptedException {

        Thread.sleep(12000);
        System.out.println("method_3 executed");

    }

    public String method_4(String message ) throws InterruptedException {

        Thread.sleep(3000);
        System.out.println("method_2 executed");
        return message;
    }

}
