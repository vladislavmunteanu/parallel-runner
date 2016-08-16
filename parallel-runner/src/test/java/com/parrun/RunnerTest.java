package com.parrun;

import com.parrun.exceptions.RunnerExceptions;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by c-vladmunt on 10/7/2015.
 */
public class RunnerTest {

    private static final Logger LOG = Logger.getLogger(RunnerTest.class);
    static List<Task> taskList;
    static Runner runner;

    static Task testInt;
    static List<Object> testIntParameters;
    static int testIntParameter = 3;

    static Task testByte;
    static List<Object> testByteParameters;
    static byte testByteParameter = 3;

    static Task testShort;
    static List<Object> testShortParameters;
    static short testShortParameter = 3;

    static Task testTwoParameters;



    public void testNoParameter() throws InterruptedException {
        Thread.sleep(5000);
        LOG.info("Test method without parameter");}

    public void testOneParameter(RunnerTest runnerTest) throws InterruptedException {
        Thread.sleep(6000);
        LOG.info("Test method without one parameter");}

    public void testTwoParameters(String message_0,String message_1){

        LOG.info("message_0 = " + message_0);
        LOG.info("message_1 = " + message_1);
    }

    public void testInt(int message) throws InterruptedException {
        Thread.sleep(3000);
        LOG.info(String.valueOf(message));
    }

    public void testByte(byte message) throws InterruptedException {
        Thread.sleep(2000);
        LOG.info(String.valueOf(message));
    }

    public void testShort(Short message) throws InterruptedException {
        Thread.sleep(1000);
        LOG.info(String.valueOf(message));
    }

    public String testReturnString(String message){
        LOG.info(message);
        return message;
    }

    public void theSame(String message){
        LOG.info(message);
    }

    public void theSame(int message){
        LOG.info(String.valueOf(message));
    }


    @BeforeClass
    public static void setUp() throws RunnerExceptions {

        runner = new Runner();
        testIntParameters = new ArrayList<>();
        testIntParameters.add(testIntParameter);
        testInt = new Task(new RunnerTest(),"testInt",testIntParameters);

        testByteParameters = new ArrayList<>();
        testByteParameters.add(testByteParameter);
        testByte = new Task(new RunnerTest(),"testByte",testByteParameters);

        testShortParameters = new ArrayList<>();
        testShortParameters.add(testShortParameter);
        testShort = new Task(new RunnerTest(),"testShort",testShortParameters);

    }

    @Test
    public void test_All_Data_Types() throws RunnerExceptions, InterruptedException {


        taskList = new ArrayList<>();
        taskList.add(testInt);
        taskList.add(testByte);
        taskList.add(testShort);

        long startTime = System.currentTimeMillis();

        runner.runParallelTasks(taskList);

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000;
        LOG.info("Done in " + duration + " seconds");

        long startTime1 = System.currentTimeMillis();

        testInt(3);
        testByte((byte) 3);
        testShort((short) 3);

        long endTime1 = System.currentTimeMillis();
        double duration1 = (endTime1 - startTime1) / 1000;
        LOG.info("Done in " + duration1 + " seconds");

        assert duration < duration1;

    }

    @Test
    public void test_Method_No_Parameters() throws RunnerExceptions {

        taskList = new ArrayList<>();
        taskList.add(new Task(new RunnerTest(), "testNoParameter"));
        runner.runParallelTasks(taskList);

    }

    @Test
    public void test_Method_One_Parameter() throws RunnerExceptions {

        List<Object> parameters = new ArrayList<>();
        RunnerTest runnerTest = new RunnerTest();
        parameters.add(runnerTest);
        taskList = new ArrayList<>();
        taskList.add(new Task(new RunnerTest(), "testOneParameter", parameters));
        runner.runParallelTasks(taskList);

    }

    @Test
    public void test_Method_Mixed_Parameters() throws RunnerExceptions, InterruptedException {

        List<Object> parameters = new ArrayList<>();
        RunnerTest runnerTest = new RunnerTest();
        parameters.add(runnerTest);
        taskList = new ArrayList<>();
        taskList.add(new Task(new RunnerTest(), "testOneParameter", parameters));
        taskList.add(new Task(new RunnerTest(), "testNoParameter"));

        runner.runParallelTasks(taskList);

    }

    @Test
    public void test_Method_With_Return() throws RunnerExceptions {

        String parameter = "Passed";
        int parameter1 = 3;
        List<Object> parameters = new ArrayList<>();
        parameters.add(parameter);
        List<Object> parameters1 = new ArrayList<>();
        parameters1.add(parameter1);
        taskList = new ArrayList<>();
        taskList.add(new Task(new RunnerTest(), "testReturnString", parameters));
        taskList.add(new Task(new RunnerTest(), "testInt", parameters1));
        List<Object> objectList = runner.runParallelTasksWithReturn(taskList);

        String returnedMessage = (String) objectList.get(0);

        assert Objects.equals(returnedMessage, "Passed");
        assert objectList.get(1) == null;
    }

    @Test
    public void test_TheSame_Method_Different_Parameters() throws RunnerExceptions {

        String parameterString = "Passed";
        int parameterInt = 1;

        List<Object> parametersString = new ArrayList<>();
        parametersString.add(parameterString);

        List<Object> parametersInt = new ArrayList<>();
        parametersInt.add(parameterInt);

        taskList = new ArrayList<>();
        taskList.add(new Task(new RunnerTest(), "theSame", parametersString));
        taskList.add(new Task(new RunnerTest(), "theSame", parametersInt));

        runner.runParallelTasks(taskList);
    }

    @Test
    public void test_doSomeParallelTasksNoWaits() throws RunnerExceptions {

        List<Object> parameters = new ArrayList<>();
        RunnerTest runnerTest = new RunnerTest();
        parameters.add(runnerTest);
        taskList = new ArrayList<>();
        taskList.add(new Task(new RunnerTest(), "testOneParameter", parameters));
        taskList.add(new Task(new RunnerTest(), "testNoParameter"));

        long startTime = System.currentTimeMillis();
        runner.runParallelTasksNoWaits(taskList);
        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000;

        assert duration < 6;

    }

    @Test
    public void test_doSomeParallelTasksWaitForMethod() throws RunnerExceptions {

        List<Object> parameters = new ArrayList<>();
        RunnerTest runnerTest = new RunnerTest();
        parameters.add(runnerTest);
        taskList = new ArrayList<>();
        taskList.add(new Task(new RunnerTest(), "testOneParameter", parameters));
        taskList.add(new Task(new RunnerTest(), "testNoParameter"));

        long startTime = System.currentTimeMillis();
        runner.runParallelTasksWaitFor(taskList, "testNoParameter");
        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000;

        assert (int) duration == 5;

    }

    @Test
    public void test_doSomeParallelTasksWaitForMethods() throws RunnerExceptions {

        List<Object> parameters = new ArrayList<>();
        RunnerTest runnerTest = new RunnerTest();
        parameters.add(runnerTest);
        taskList = new ArrayList<>();
        taskList.add(new Task(new RunnerTest(), "testOneParameter", parameters));
        taskList.add(new Task(new RunnerTest(), "testNoParameter"));

        List<String> methods = new ArrayList<>();
        methods.add("testOneParameter");
        methods.add("testNoParameter");

        long startTime = System.currentTimeMillis();
        runner.runParallelTasksWaitFor(taskList, methods);
        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000;

        assert (int) duration == 6;

    }

    @Test
    public void test_TwoParametersProvided() throws RunnerExceptions {


        testTwoParameters = new Task(new RunnerTest(),"testTwoParameters","message 0","message 1");
        taskList = new ArrayList<>();
        taskList.add(testTwoParameters);
        runner.runParallelTasks(taskList);


    }

    @Test
    public void test_TwoParametersOneProvided(){

        try {
            testTwoParameters = new Task(new RunnerTest(),"testTwoParameters","message 0",null);
            taskList = new ArrayList<>();
            taskList.add(testTwoParameters);
            runner.runParallelTasks(taskList);
            Assert.fail("Exception expected");
        } catch (RunnerExceptions runnerExceptions) {
            LOG.info(runnerExceptions.getMessage()+" is expected");
        }
    }

    @Test
    public void test_RunWithArguments() throws RunnerExceptions {

        runner.runParallelTasks(testInt,testByte);

    }


}