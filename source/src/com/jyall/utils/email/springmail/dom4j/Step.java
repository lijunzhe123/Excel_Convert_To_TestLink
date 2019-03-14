package com.jyall.utils.email.springmail.dom4j;

public class Step
{
    String step_number;
    String actions;
    String expectedresults;
    String execution_type;

    public String getStep_number()
    {
        return this.step_number;
    }

    public void setStep_number(String step_number)
    {
        this.step_number = step_number;
    }

    public String getActions()
    {
        return this.actions;
    }

    public void setActions(String actions)
    {
        this.actions = actions;
    }

    public String getExpectedresults()
    {
        return this.expectedresults;
    }

    public void setExpectedresults(String expectedresults)
    {
        this.expectedresults = expectedresults;
    }

    public String getExecution_type()
    {
        return this.execution_type;
    }

    public void setExecution_type(String execution_type)
    {
        this.execution_type = execution_type;
    }
}
