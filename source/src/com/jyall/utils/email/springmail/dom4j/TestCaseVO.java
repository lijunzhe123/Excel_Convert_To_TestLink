package com.jyall.utils.email.springmail.dom4j;

import java.util.List;
import org.dom4j.Element;
import org.dom4j.Node;

public class TestCaseVO
{
    String internalid;
    String node_order;
    String name;
    String externalid;
    String version;
    String summary;
    String preconditions;
    String execution_type;
    String importance;
    List<Step> steps;

    public String getNode_order()
    {
        return this.node_order;
    }

    public void setNode_order(String node_order)
    {
        this.node_order = node_order;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getInternalid()
    {
        return this.internalid;
    }

    public void setInternalid(String internalid)
    {
        this.internalid = internalid;
    }

    public String getExternalid()
    {
        return this.externalid;
    }

    public void setExternalid(String externalid)
    {
        this.externalid = externalid;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSummary()
    {
        return this.summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getPreconditions()
    {
        return this.preconditions;
    }

    public void setPreconditions(String preconditions)
    {
        this.preconditions = preconditions;
    }

    public String getExecution_type()
    {
        return this.execution_type;
    }

    public void setExecution_type(String execution_type)
    {
        this.execution_type = execution_type;
    }

    public String getImportance()
    {
        return this.importance;
    }

    public void setImportance(String importance)
    {
        this.importance = importance;
    }

    public List<Step> getSteps()
    {
        return this.steps;
    }

    public void setSteps(List<Step> steps)
    {
        this.steps = steps;
    }

    public static Node testcasetoxml(Element parentElement, TestCaseVO testcasevo)
    {
        Element testcase = parentElement.addElement("testcase");
        testcase.addAttribute("internalid", testcasevo.getInternalid());
        testcase.addAttribute("name", testcasevo.getName());
        Element node_order = testcase.addElement("node_order");
        node_order.addCDATA(testcasevo.getNode_order());
        Element externalid = testcase.addElement("externalid");
        externalid.addCDATA(testcasevo.getExternalid());
        Element version = testcase.addElement("version");
        version.addCDATA(testcasevo.getVersion());
        Element summary = testcase.addElement("summary");
        summary.addCDATA(testcasevo.getSummary());
        Element preconditions = testcase.addElement("preconditions");
        preconditions.addCDATA(testcasevo.getPreconditions());
        Element execution_type = testcase.addElement("execution_type");
        execution_type.addCDATA(testcasevo.getExecution_type());
        Element importance = testcase.addElement("importance");
        importance.addCDATA(testcasevo.getImportance());
        Element steps = testcase.addElement("steps");
        Element step = steps.addElement("step");

        Element step_number = step.addElement("step_number");
        step_number.addCDATA(((Step)testcasevo.getSteps().get(0)).getStep_number());

        Element actions = step.addElement("actions");
        actions.addCDATA(((Step)testcasevo.getSteps().get(0)).getActions());

        Element expectedresults = step.addElement("expectedresults");
        expectedresults.addCDATA(((Step)testcasevo.getSteps().get(0))
                .getExpectedresults());

        Element execution_type1 = step.addElement("execution_type");
        execution_type1.addCDATA(((Step)testcasevo.getSteps().get(0))
                .getExecution_type());

        return testcase;
    }
}
