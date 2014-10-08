/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.experimental.engine;

import java.util.List;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public abstract class Stage {
    
    private String stageName;
    
    private List<FlowUnit> input;
    private List<FlowUnit> output;
    
    private List<Command> commands;

    public Stage() {
    }

    public String getStageName() {
        return stageName;
    }
    
    public List<Command> getCommands() {
        return commands;
    }
    
    public void executeCommand(Command command) throws Exception
    {
        command.exectute(input, output);
    }    
                
    public abstract void StartStage(List<FlowUnit> input);    
    public abstract List<FlowUnit> StopStage();    

    public abstract void validateInput() throws Exception;
    public abstract void validateOutput() throws Exception;
}
