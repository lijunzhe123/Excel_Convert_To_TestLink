package com.jyall.utils.email.springmail.dom4j;

import org.dom4j.Document;
import org.dom4j.Element;

public class TestSuiteVO
{
    String name;
    String node_order;
    String details;

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNode_order()
    {
        return this.node_order;
    }

    public void setNode_order(String node_order)
    {
        this.node_order = node_order;
    }

    public String getDetails()
    {
        return this.details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public static Document rootTestSuitToXML(Document document, TestSuiteVO testSuite)
    {
        Element root = document.addElement("testsuite");
        root.addAttribute("name", testSuite.getName());
        Element node_order = root.addElement("node_order");
        node_order.addCDATA(testSuite.getNode_order());
        Element details = root.addElement("details");
        details.addCDATA(testSuite.getDetails());
        return document;
    }

    public static Element testSuitToXML(Element parentElement, TestSuiteVO testSuite)
    {
        Element root = parentElement.addElement("testsuite");
        root.addAttribute("name", testSuite.getName());
        Element node_order = root.addElement("node_order");
        node_order.addCDATA(testSuite.getNode_order());
        Element details = root.addElement("details");
        details.addCDATA(testSuite.getDetails());
        return parentElement;
    }
}
