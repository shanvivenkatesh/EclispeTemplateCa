package com.listeners;

import java.io.IOException;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.Tests.App;

import calas.CmmonFunction.CmmonFunction;

public class SuiteListener extends CmmonFunction implements ISuiteListener
{
    private float startTime;
    
    @Override
    public void onStart(ISuite suite) 
    {
        startTime=System.currentTimeMillis();

 

        try 
        {
            String commandForDeletingNetworkDrive = "net use Z: /delete /y";
            String commandForMappingToNetworkDrive = "net use Z: \\\\"+App.endp+ " /user:"+App.dbEnv+"\\calaqa LongCalamar1";

 

            Process process = Runtime.getRuntime().exec(commandForMappingToNetworkDrive);
            process.waitFor();
            
            process= Runtime.getRuntime().exec(commandForDeletingNetworkDrive);
            process.waitFor();
            
            process= Runtime.getRuntime().exec(commandForMappingToNetworkDrive);
            process.waitFor();    
            
            App.logger.info(commandForDeletingNetworkDrive);
            App.logger.info(commandForMappingToNetworkDrive);
        } 
        catch (IOException | InterruptedException e) 
        {
            App.logger.info(e.getMessage());
        }
    }
 
    @Override
    public void onFinish(ISuite suite) 
    {
        float finishTime = System.currentTimeMillis();
        float timeTakenToComplete = finishTime-startTime;
        int seconds = (int) ((timeTakenToComplete / 1000) % 60);
        int minutes = (int) ((timeTakenToComplete / 1000) / 60);
        App.logger.info("Suite EXECUTIION TIME: "+ minutes + " minutes and "+ seconds + " seconds");
        quitBrowser();
    }
}