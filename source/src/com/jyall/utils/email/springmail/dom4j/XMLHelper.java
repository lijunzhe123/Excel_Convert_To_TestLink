package com.jyall.utils.email.springmail.dom4j;

import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLHelper
{
    public static Document getDocument(String filePath, String fileName)
    {
        return getDocument(filePath +

                File.separator + fileName);
    }

    public static Document getDocument(InputStream in)
            throws DocumentException
    {
        SAXReader reader = new SAXReader();

        return reader.read(in);
    }

    public static Document getDocument(String fileName)
    {
        SAXReader reader = new SAXReader();

        File file = new File(fileName);
        try
        {
            return reader.read(file);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Element getRootNode(Document document)
    {
        if (document == null) {
            return null;
        }
        Element root = document.getRootElement();
        return root;
    }

    public static Element getRootNode(String xmlPath)
    {
        if ((xmlPath == null) || (xmlPath.trim().equals(""))) {
            return null;
        }
        Document document = getDocument(xmlPath);
        if (document == null) {
            return null;
        }
        return getRootNode(document);
    }

    public static Iterator<Element> getIterator(Element parent)
    {
        if (parent == null) {
            return null;
        }
        Iterator<Element> iterator = parent.elementIterator();
        return iterator;
    }

    public static List<Element> getChildElements(Element parent, String childName)
    {
        childName = childName.trim();
        if (parent == null) {
            return null;
        }
        childName = childName + "//";
        List<Element> childElements = parent.selectNodes(childName);
        return childElements;
    }

    public static List<Element> getChildList(Element node)
    {
        if (node == null) {
            return null;
        }
        Iterator<Element> itr = getIterator(node);
        if (itr == null) {
            return null;
        }
        List<Element> childList = new ArrayList();
        while (itr.hasNext())
        {
            Element kidElement = (Element)itr.next();
            if (kidElement != null) {
                childList.add(kidElement);
            }
        }
        return childList;
    }

    public static Node getSingleNode(Element parent, String nodeNodeName)
    {
        nodeNodeName = nodeNodeName.trim();
        String xpath = "//";
        if (parent == null) {
            return null;
        }
        if ((nodeNodeName == null) || (nodeNodeName.equals(""))) {
            return null;
        }
        xpath = xpath + nodeNodeName;
        Node kid = parent.selectSingleNode(xpath);
        return kid;
    }

    public static Element getChild(Element parent, String childName)
    {
        childName = childName.trim();
        if (parent == null) {
            return null;
        }
        if ((childName == null) || (childName.equals(""))) {
            return null;
        }
        Element e = null;
        Iterator it = getIterator(parent);
        while ((it != null) && (it.hasNext()))
        {
            Element k = (Element)it.next();
            if (k != null) {
                if (k.getName().equalsIgnoreCase(childName))
                {
                    e = k;
                    break;
                }
            }
        }
        return e;
    }

    public static boolean hasChild(Element e)
    {
        if (e == null) {
            return false;
        }
        return e.hasContent();
    }

    public static Iterator<Attribute> getAttrIterator(Element e)
    {
        if (e == null) {
            return null;
        }
        Iterator<Attribute> attrIterator = e.attributeIterator();
        return attrIterator;
    }

    public static List<Attribute> getAttributeList(Element e)
    {
        if (e == null) {
            return null;
        }
        List<Attribute> attributeList = new ArrayList();
        Iterator<Attribute> atrIterator = getAttrIterator(e);
        if (atrIterator == null) {
            return null;
        }
        while (atrIterator.hasNext())
        {
            Attribute attribute = (Attribute)atrIterator.next();
            attributeList.add(attribute);
        }
        return attributeList;
    }

    public static Attribute getAttribute(Element element, String attrName)
    {
        attrName = attrName.trim();
        if (element == null) {
            return null;
        }
        if ((attrName == null) || (attrName.equals(""))) {
            return null;
        }
        Attribute attribute = element.attribute(attrName);
        return attribute;
    }

    public static String attrValue(Element e, String attrName)
    {
        attrName = attrName.trim();
        if (e == null) {
            return null;
        }
        if ((attrName == null) || (attrName.equals(""))) {
            return null;
        }
        return e.attributeValue(attrName);
    }

    public static Map<String, String> getNodeAttrMap(Element e)
    {
        Map<String, String> attrMap = new HashMap();
        if (e == null) {
            return null;
        }
        List<Attribute> attributes = getAttributeList(e);
        if (attributes == null) {
            return null;
        }
        for (Attribute attribute : attributes)
        {
            String attrValueString = attrValue(e, attribute.getName());
            attrMap.put(attribute.getName(), attrValueString);
        }
        return attrMap;
    }

    public static Map<String, String> getSingleNodeText(Element e)
    {
        Map<String, String> map = new HashMap();
        if (e == null) {
            return null;
        }
        List<Element> kids = getChildList(e);
        for (Element e2 : kids) {
            if (e2.getTextTrim() != null) {
                map.put(e2.getName(), e2.getTextTrim());
            }
        }
        return map;
    }

    public static List<Element> ransack(Element element, List<Element> allkidsList)
    {
        if (element == null) {
            return null;
        }
        if (hasChild(element))
        {
            List<Element> kids = getChildList(element);
            for (Element e : kids)
            {
                allkidsList.add(e);
                ransack(e, allkidsList);
            }
        }
        return allkidsList;
    }

    public static List<Element> getNameElement(Element element, String nodeName)
    {
        nodeName = nodeName.trim();
        List<Element> kidsElements = new ArrayList();
        if (element == null) {
            return null;
        }
        if ((nodeName == null) || (nodeName.equals(""))) {
            return null;
        }
        List<Element> allKids = ransack(element, new ArrayList());
        if (allKids == null) {
            return null;
        }
        for (int i = 0; i < allKids.size(); i++)
        {
            Element kid = (Element)allKids.get(i);
            if (nodeName.equals(kid.getName())) {
                kidsElements.add(kid);
            }
        }
        return kidsElements;
    }

    public static int validateSingle(Element element)
    {
        int j = 1;
        if (element == null) {
            return j;
        }
        Element parent = element.getParent();
        List<Element> kids = getChildList(parent);
        for (Element kid : kids) {
            if (element.equals(kid)) {
                j++;
            }
        }
        return j;
    }

    public static Document parseStringToXml(String xml)
    {
        try
        {
            return DocumentHelper.parseText(xml);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Document parseFileToXml(String filepath)
    {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try
        {
            doc = saxReader.read(new File(filepath));
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return doc;
    }

    public static Document parseFIOToXml(String filename)
    {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try
        {
            InputStream in = XMLUtils.class.getResourceAsStream("xml/" +
                    filename);
            doc = saxReader.read(in);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return doc;
    }

    public static Element getElementByXpath(String xpath, Document doc)
    {
        List<Element> parameterList = doc.selectNodes(xpath);
        if ((parameterList != null) && (parameterList.size() > 0)) {
            return (Element)parameterList.get(0);
        }
        return null;
    }

    public static Element getElementById(String id, Document doc)
    {
        return getElementByXpath("//*[@id='" + id + "']", doc);
    }

    public static Document insertElement(String pid, Element newele, Document doc)
    {
        return insertElement(pid, newele, doc, 1);
    }

    public static Document insertElement(String pid, Element newele, Document doc, int i)
    {
        Element element = getElementById(pid, doc);

        List<Element> list = element.getParent().content();

        list.add(list.indexOf(element) + i, newele);

        return doc;
    }

    public static boolean deleteNodes(Document doc, String xpath)
    {
        boolean flag = true;
        try
        {
            List<Node> nlist = doc.selectNodes(xpath);
            for (Node node : nlist) {
                node.detach();
            }
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }

    public static boolean deleteChildren(Element element)
    {
        boolean flag = true;
        try
        {
            List<Node> nlist = element.elements();
            for (Node node : nlist) {
                node.detach();
            }
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }

    public static boolean deleteElement(Element ele)
    {
        List<Element> list = ele.getParent().content();
        list.remove(list.indexOf(ele));
        return true;
    }

    public static boolean saveDocument(String filepath, Document document)
    {
        try
        {
            XMLWriter writer = new XMLWriter(new FileWriter(new File(filepath)));
            writer.write(document);
            writer.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static Element getElementByName(Element e, String name)
    {
        Iterator iterator = e.elementIterator(name);
        if (iterator.hasNext()) {
            return (Element)iterator.next();
        }
        return null;
    }

    public static Element getElementByElementName(Document doc, Element e, String name)
    {
        List list = doc.selectNodes("//*/@name");
        Iterator iter = list.iterator();
        while (iter.hasNext())
        {
            Attribute attribute = (Attribute)iter.next();
            if (attribute.getValue().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public static Element getCurrentElement(Document doc, String eleName)
    {
        Element root = doc.getRootElement();

        List elements = root.elements();
        List element = root.elements("testsuite");
        rightele = null;
        Element cuelement = getElementList(root, "testsuite", "name", eleName);

        return cuelement;
    }

    public static List getElementsByName(Element e, String name)
    {
        return e.elements(name);
    }

    public static Element getElementByNameAndAttribute(Element e, String name, String attribute, String value)
    {
        for (Iterator iterator = e.elementIterator(); iterator.hasNext();)
        {
            Element element = (Element)iterator.next();
            if (value.equals(element.attribute(attribute).getValue())) {
                return element;
            }
        }
        return null;
    }

    public static Element getElementList(Element element, String name, String attribute, String value)
    {
        List elements = element.elements("testsuite");
        Element elem = null;
        if (elements.size() != 0) {
            for (Iterator it = elements.iterator(); it.hasNext();)
            {
                elem = (Element)it.next();
                if (value.equals(elem.attribute(attribute).getValue())) {
                    rightele = elem;
                }
                getElementList(elem, name, attribute, value);
            }
        }
        return rightele;
    }

    public static String getElementValueByName(Element e, String name)
    {
        Iterator iterator = e.elementIterator(name);
        if (iterator.hasNext()) {
            return ((Element)iterator.next()).getText();
        }
        return "";
    }

    public static String getElementAttributeByName(Element e, String name, String attribute)
    {
        Iterator iterator = e.elementIterator(name);
        if (iterator.hasNext()) {
            return ((Element)iterator.next()).attribute(attribute).getValue();
        }
        return "";
    }

    public static String getElementValue(Element eleName, String defaultValue)
    {
        if (Objects.isNull(eleName)) {
            return defaultValue == null ? "" : defaultValue;
        }
        return eleName.getTextTrim();
    }

    public static String getElementValue(String eleName, Element parentElement)
    {
        if (Objects.isNull(parentElement)) {
            return null;
        }
        Element element = parentElement.element(eleName);
        if (!Objects.isNull(element)) {
            return element.getTextTrim();
        }
        try
        {
            throw new Exception("���������������" + eleName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getElementValuesByName(Element e, String name)
    {
        Iterator iterator = e.elementIterator(name);

        StringBuffer buffer = new StringBuffer();
        while (iterator.hasNext()) {
            if ("".equals(buffer.toString())) {
                buffer.append(((Element)iterator.next()).getText());
            } else {
                buffer.append(

                        ((Element)iterator.next()).getText() +

                                ",");
            }
        }
        return StringUtil.stringToArray(buffer.toString(), ",");
    }

    public static Document createDocument()
    {
        Document document = DocumentHelper.createDocument();

        return document;
    }

    public static Element createRootElement(Document document, String rootName)
    {
        if (document != null) {
            return document.addElement(rootName);
        }
        return null;
    }

    public static Element createElement(Element pElement, String elementName, String elmentText)
    {
        if ((pElement != null) && (!StringUtils.isEmpty(elmentText)))
        {
            Element nElement = pElement.addElement(elementName);
            if (!StringUtils.isEmpty(elmentText)) {
                nElement.addText(elmentText);
            }
            return nElement;
        }
        return null;
    }

    public static void saveXml(Document document, OutputStream os, String encoding)
    {
        OutputFormat format = OutputFormat.createPrettyPrint();
        defaultEncoding = encoding;
        format.setEncoding(defaultEncoding);

        XMLWriter output = null;
        try
        {
            output = new XMLWriter(os, format);

            output.write(document);

            output.close();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void saveXml(Document document, OutputStream os)
    {
        saveXml(document, os, defaultEncoding);
    }

    public static void saveXml(Element element, OutputStream os)
    {
        Document document = DocumentHelper.createDocument();

        document.add(element);

        saveXml(document, os, defaultEncoding);
    }

    private static String defaultEncoding = "GBK";
    private static Element rightele;
}
