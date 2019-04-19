package com.jyall.utils.email.springmail.dom4j;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class CreatXML
{
    private static Step step = new Step();
    private static List<Step> steps = new ArrayList();
    private static Node txt;
    private static TestCaseVO vo = new TestCaseVO();
    private String level = "1";
    private static String firstModel = null;
    private static String secModel = null;
    private static String thirdModel = null;
    private static String fourModel = null;
    private static String internalid = "";
    private static Element root;
    private static Element rootgen;
    private static Element secNode;
    private static Element thirdNode;
    private static Element fourNode;
    private static Element testsuite2;
    private static Element testsuite3;
    private static Element testsuite4;
    private static Document doc;
    private static boolean firstisEmpty;
    private static boolean secisEmpty;
    private static boolean thirdisEmpty;
    private static boolean fourisEmpty;
    private static int collenght = 8;
    private static Element currentEle;
    private static Map<String, Element> findelement = new HashMap();

    public static Document createDocument(String filename, String sheetName)
    {
        Document document = DocumentHelper.createDocument();
        document = createRootSuite(document);

        try
        {
            Calendar calendar = Calendar.getInstance();
            internalid=new SimpleDateFormat("yyyyMMddHHmmss").format(calendar
                    .getTime());
            Object[][] a = ExcelUtils.getTableArray(filename, sheetName);

            vo.setNode_order("0");
            vo.setVersion("1");

            vo.setExecution_type("1");

            System.out.println("总共需要转换:{ " + a.length + " 行}");
            for (int i = 0; i < a.length; i++)
            {
//                vo.setExternalid(RadomUtils.getrandomLengthString(4));
                vo.setInternalid(internalid + i);
                System.out.println("正在转换第:{ " + (i + 1) + " 行}");
                if (i == 1000) {
                    System.out.println(i);
                }
                for (int j = 0; j < collenght; j++)
                {
                    if (j == 0) {
                        firstModel = a[i][j].toString();
//                        System.out.println(firstModel);
                    }
                    if (j == 1) {
                        secModel = a[i][j].toString();
//                        System.out.println(secModel);
                    }
                    if (j == 2) {
                        thirdModel = a[i][j].toString();
                    }
                    if (j == 3) {
                        fourModel = a[i][j].toString();
                    }
                    if (j == 4) {
                        vo.setName(a[i][j].toString());
                    }
                    if (j == 5) {
                        vo.setPreconditions(a[i][j].toString());
                    }
                    if (j == 6)
                    {
                        step.setStep_number("1");
                        step.setActions(a[i][j].toString());
                    }
                    if (j == 7)
                    {
                        step.setExpectedresults(a[i][j].toString());
                        steps.add(step);
                        vo.setSteps(steps);
                    }
                    if (j == 8)
                    {
                        if (a[i][j].toString().trim().equals("高")) {
                            vo.setImportance("3");
                        }
                        if (a[i][j].toString().trim().equals("中")) {
                            vo.setImportance("2");
                        }
                        if (a[i][j].toString().trim().equals("低")) {
                            vo.setImportance("1");
                        }
                    }
                    if (j == 9) {
                        vo.setSummary(a[i][j].toString());
                    }
                }
                firstisEmpty = firstModel.isEmpty();
                secisEmpty = secModel.isEmpty();
                thirdisEmpty = thirdModel.isEmpty();
                fourisEmpty = fourModel.isEmpty();
                if (firstisEmpty)
                {
                    System.err.println("大侠，保证第[ "+(i + 1) +" ]行的  【一级模块】有内容");
                    Thread.sleep(6000);
                    System.exit(-1);
                }
                if (((!firstisEmpty) && (secisEmpty) && (!thirdisEmpty)) ||
                        ((!firstisEmpty) && (secisEmpty) && (!thirdisEmpty) &&
                                (!fourisEmpty)) || ((!firstisEmpty) && (secisEmpty) &&
                        (thirdisEmpty) && (!fourisEmpty)))
                {
                    System.err.println("大侠，保证第[ "+(i + 1) +" ]行的  【二级模块】有内容");
                    Thread.sleep(6000);
                    System.exit(-1);
                }
                if ((!firstisEmpty) && (!secisEmpty) && (thirdisEmpty) &&
                        (!fourisEmpty))
                {
                    System.err.println("大侠，请保证第[ "+(i + 1) +" ]行的  【三级模块】有内容");
                    Thread.sleep(6000);
                    System.exit(-1);
                }
                String mapKey = firstModel + secModel + thirdModel + fourModel;
                if ((!firstisEmpty) && (secisEmpty) && (thirdisEmpty) && (fourisEmpty))
                {
                    if (XMLHelper.getCurrentElement(document, firstModel) == null)
                    {
                        document = createFirstSuite(document, root);
                        doc = document;
                    }
                    currentEle = (Element)findelement.get(mapKey);
                    if (currentEle == null)
                    {
                        currentEle = XMLHelper.getCurrentElement(document,
                                firstModel);
                        findelement.put(mapKey, currentEle);
                    }
                    TestCaseVO.testcasetoxml(currentEle, vo);
                }
                else if ((!firstisEmpty) && (!secisEmpty) && (thirdisEmpty) &&
                        (fourisEmpty))
                {
                    doc = createSecSuite(document, root);
                    currentEle = (Element)findelement.get(mapKey);
                    if (currentEle == null)
                    {
                        currentEle = XMLHelper.getCurrentElement(document,
                                secModel);
                        findelement.put(mapKey, currentEle);
                    }
                    TestCaseVO.testcasetoxml(currentEle, vo);
                }
                else if ((!firstisEmpty) && (!secisEmpty) && (!thirdisEmpty) &&
                        (fourisEmpty))
                {
                    doc = createThirdSuite(document, root);

                    currentEle = (Element)findelement.get(mapKey);
                    if (currentEle == null)
                    {
                        currentEle = XMLHelper.getCurrentElement(document,
                                thirdModel);
                        findelement.put(mapKey, currentEle);
                    }
                    TestCaseVO.testcasetoxml(currentEle, vo);
                }
                else
                {
                    doc = createFourSuite(document, root);

                    currentEle = (Element)findelement.get(mapKey);
                    if (currentEle == null)
                    {
                        currentEle =
                                XMLHelper.getCurrentElement(doc, fourModel);
                        findelement.put(mapKey, currentEle);
                    }
                    TestCaseVO.testcasetoxml(currentEle, vo);
                }
                firstModel = null;
                secModel = null;
                thirdModel = null;
                fourModel = null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return document;
    }

    public static Document createRootSuite(Document doc)
    {
        if (rootgen == null) {
            rootgen = doc.addElement("testsuite");
        }
        return doc;
    }

    public static Document createFirstSuite(Document doc, Element root)
    {
        if (root == null)
        {
            root = doc.getRootElement().addElement("testsuite");
            root.addAttribute("name", firstModel);
            Element node_order = root.addElement("node_order");
            node_order.addCDATA("10");
            Element details = root.addElement("details");
            details.addCDATA("");
        }
        if (XMLHelper.getElementByElementName(doc, rootgen, firstModel) == null)
        {
            root = rootgen.addElement("testsuite");
            root.addAttribute("name", firstModel);
            Element node_order = root.addElement("node_order");
            node_order.addCDATA("10");
            Element details = root.addElement("details");
            details.addCDATA("");
        }
        return doc;
    }

    public static Document createSecSuite(Document doc, Element root)
    {
        if (root == null)
        {
            root = doc.getRootElement().addElement("testsuite");
            root.addAttribute("name", firstModel);
            Element node_order = root.addElement("node_order");
            node_order.addCDATA("10");
            Element details = root.addElement("details");
            details.addCDATA("");
        }
        if (XMLHelper.getElementByElementName(doc, rootgen, firstModel) == null)
        {
            root.addAttribute("name", firstModel);
            Element node_order = root.addElement("node_order");
            node_order.addCDATA("10");
            Element details = root.addElement("details");
            details.addCDATA("");
        }
        if (XMLHelper.getElementByElementName(doc, rootgen, firstModel) == null)
        {
            root.addAttribute("name", firstModel);
            Element node_order = root.addElement("node_order");
            node_order.addCDATA("10");
            Element details = root.addElement("details");
            details.addCDATA("");
        }
        if (XMLHelper.getElementByElementName(doc, rootgen, secModel) == null)
        {
            testsuite2 = root.addElement("testsuite");
            testsuite2.addAttribute("name", secModel);
            Element node_order2 = testsuite2.addElement("node_order");
            node_order2.addCDATA("10");
            Element details2 = testsuite2.addElement("details");
            details2.addCDATA("");
        }
        return doc;
    }

    public static Document createThirdSuite(Document doc, Element root)
    {
        if (XMLHelper.getCurrentElement(doc, firstModel) == null) {
            createFirstSuite(doc, root);
        }
        if (XMLHelper.getCurrentElement(doc, secModel) == null) {
            createSecSuite(doc, root);
        }
        if (XMLHelper.getCurrentElement(doc, thirdModel) == null)
        {
            testsuite3 = testsuite2.addElement("testsuite");
            testsuite3.addAttribute("name", thirdModel);
            Element node_order3 = testsuite3.addElement("node_order");
            node_order3.addCDATA("10");
            Element details3 = testsuite3.addElement("details");
            details3.addCDATA("");
        }
        return doc;
    }

    public static Document createFourSuite(Document doc, Element root)
    {
        if (root == null)
        {
            root = doc.getRootElement().addElement("testsuite");
            root.addAttribute("name", firstModel);
            Element node_order = root.addElement("node_order");
            node_order.addCDATA("10");
            Element details = root.addElement("details");
            details.addCDATA("");
        }
        if (XMLHelper.getElementByElementName(doc, rootgen, firstModel) == null)
        {
            root.addAttribute("name", firstModel);
            Element node_order = root.addElement("node_order");
            node_order.addCDATA("10");
            Element details = root.addElement("details");
            details.addCDATA("");
        }
        if (XMLHelper.getElementByElementName(doc, rootgen, secModel) == null)
        {
            testsuite2 = root.addElement("testsuite");
            testsuite2.addAttribute("name", secModel);
            Element node_order2 = testsuite2.addElement("node_order");
            node_order2.addCDATA("10");
            Element details2 = testsuite2.addElement("details");
            details2.addCDATA("");
        }
        if (XMLHelper.getElementByElementName(doc, rootgen, thirdModel) == null)
        {
            testsuite3 = testsuite2.addElement("testsuite");
            testsuite3.addAttribute("name", thirdModel);
            Element node_order3 = testsuite3.addElement("node_order");
            node_order3.addCDATA("10");
            Element details3 = testsuite3.addElement("details");
            details3.addCDATA("");
        }
        if (XMLHelper.getElementByElementName(doc, rootgen, fourModel) == null)
        {
            testsuite4 = testsuite3.addElement("testsuite");
            testsuite4.addAttribute("name", fourModel);
            Element node_order4 = testsuite4.addElement("node_order");
            node_order4.addCDATA("10");
            Element details4 = testsuite4.addElement("details");
            details4.addCDATA("");
        }
        return doc;
    }

    public static void writeDocument(Document document, String outFile)
    {
        FileWriter fileWriter = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timefix = sdf.format(date);
        try
        {
            File tmpfile = new File(outFile);
            if (tmpfile.exists())
            {
                String tmpname = tmpfile.getName();
                tmpname = tmpname.substring(0, tmpname.indexOf('.')) + "_" +
                        timefix + ".xml";
                System.out.println("New_File_Name: " + tmpname);
                String fullname = tmpfile.getAbsolutePath()
                        .substring(0, tmpfile.getAbsolutePath().lastIndexOf(File.separator)) +
                        File.separator + tmpname;
                System.out.println("New_File_Full_Path_Name: " + fullname);

                tmpfile = new File(fullname);
            }
            else
            {
                System.err.println("check xls is exit...............");
            }
            fileWriter = new FileWriter(tmpfile);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        OutputFormat xmlFormat = new OutputFormat();
        xmlFormat.setEncoding("UTF-8");

        xmlFormat.setNewlines(true);

        xmlFormat.setIndent(true);

        xmlFormat.setIndent("    ");

        XMLWriter xmlWriter = new XMLWriter(fileWriter, xmlFormat);
        try
        {
            xmlWriter.write(document);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            xmlWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {}
}
