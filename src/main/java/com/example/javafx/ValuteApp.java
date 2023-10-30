package com.example.javafx;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public class ValuteApp {
    public static HashMap<String, Valute> currencies = new HashMap<String, Valute>();
    public static String basic;
    public static String target;
    public static double course;
    public void setCourse(){
        course = currencies.get(basic).getValue()/currencies.get(target).getValue();
    }

    public static void setBasic(String basic) {
        ValuteApp.basic = basic;
    }

    public static void setTarget(String target) {
        ValuteApp.target = target;
    }

    public static String getBasic() {
        return basic;
    }

    public static String getTarget() {
        return target;
    }

    public static double getCourse() {
        return course;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("https://www.cbr-xml-daily.ru/daily.xml");
        NodeList Elements = document.getDocumentElement().getElementsByTagName("Valute");
        for (int i = 0; i < Elements.getLength(); i++) {
            Node node = Elements.item(i);
            NodeList childNodes = node.getChildNodes();
            int numCode = 0;
            String charCode = null;
            int Nominal = 0;
            String name = null;
            double value = 0;

            for (int j = 0; j < childNodes.getLength(); j++) {
                Node childNode = childNodes.item(j);
                if (childNode.getNodeName().equals("NumCode")){
                    numCode = Integer.parseInt(childNode.getTextContent());
                }
                else if(childNode.getNodeName().equals("CharCode")){
                    charCode = childNode.getTextContent();
                }
                else if(childNode.getNodeName().equals("Nominal")){
                    Nominal = Integer.parseInt(childNode.getTextContent());
                }
                else if(childNode.getNodeName().equals("Name")){
                    name = childNode.getTextContent();
                }
                else if(childNode.getNodeName().equals("Value")){
                    value = Double.parseDouble(childNode.getTextContent().replace(',','.'));
                }
            }
            currencies.put(charCode, new Valute(numCode, charCode, Nominal, name, value, 0));
        }
        for (Map.Entry<String, Valute> valutes: currencies.entrySet()
             ) {
            System.out.println(valutes.getValue().toString());
        }

    }
}
