package com.dajie.crawler.core.demo;

/**
 * User: zhong.huang
 * Date: 13-5-8
 */

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.PrintWriter;

/**
 */
public class TestHTMLDOM {
    public static void main(String[] argv) throws Exception {
        DOMParser parser = new DOMParser();
        System.setProperty("http.proxyHost", "88.148.41.44");
        System.setProperty("http.proxyPort", "51966");
        parser.setFeature("http://xml.org/sax/features/namespaces", false);
        parser.parse("e:/temp/job-001-575-797.html");//也可以是如：http://itindex.net/blog


        //writeDoc(parser.getDocument().getDocumentElement());
        //print(parser.getDocument(), "");
        printDOMTree(parser.getDocument());
        PrintWriter w = new PrintWriter(System.out);
    }

    /**
     * 打印xml/html文档
     */
    public static void printDOMTree(Node node) {
        int type = node.getNodeType();
        switch (type) {
            // print the document element
            case Node.DOCUMENT_NODE: {
                System.out.println("<?xml version=\"1.0\" ?>");
                printDOMTree(((Document) node).getDocumentElement());
                break;
            }

            // print element with attributes
            case Node.ELEMENT_NODE: {
                System.out.print("<");
                System.out.print(node.getNodeName());
                NamedNodeMap attrs = node.getAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    Node attr = attrs.item(i);
                    System.out.print(" " + attr.getNodeName() + "=\""
                        + attr.getNodeValue() + "\"");
                }
                System.out.println(">");

                NodeList children = node.getChildNodes();
                if (children != null) {
                    int len = children.getLength();
                    for (int i = 0; i < len; i++)
                        printDOMTree(children.item(i));
                }

                break;
            }

            // handle entity reference nodes
            case Node.ENTITY_REFERENCE_NODE: {
                System.out.print("&");
                System.out.print(node.getNodeName());
                System.out.print(";");
                break;
            }

            // print cdata sections
            case Node.CDATA_SECTION_NODE: {
                System.out.print("<![CDATA[");
                System.out.print(node.getNodeValue());
                System.out.print("]]>");
                break;
            }

            // print text
            case Node.TEXT_NODE: {
                System.out.print(node.getNodeValue());
                break;
            }

            // print processing instruction
            case Node.PROCESSING_INSTRUCTION_NODE: {
                System.out.print("<?");
                System.out.print(node.getNodeName());
                String data = node.getNodeValue();
                {
                    System.out.print(" ");
                    System.out.print(data);
                }
                System.out.print("?>");
                break;
            }
        }

        if (type == Node.ELEMENT_NODE) {
            System.out.println();
            System.out.print("</");
            System.out.print(node.getNodeName());
            System.out.print(">");
        }
    }

    /**
     * 输出经过修整干净的html文档，fix up and clean html
     *
     * @param node
     */
    private static void writeDoc(Node node) {
        short type = node.getNodeType();
        switch (type) {

            case Node.ELEMENT_NODE: {
                String name = "<" + node.getNodeName();
                NamedNodeMap attrs = node.getAttributes();
                if (attrs != null) {
                    int length = attrs.getLength();
                    for (int i = 0; i < length; i++) {
                        Node attr = attrs.item(i);
                        name += " " + attr.getNodeName();
                        name += "=\"" + attr.getNodeValue() + "\"";
                    }
                }
                name += ">";
                System.out.println(name);

                NodeList children = node.getChildNodes();
                if (children != null) {
                    int length = children.getLength();
                    for (int i = 0; i < length; i++)
                        writeDoc(children.item(i));
                }
                System.out.println("</" + node.getNodeName() + ">");
                break;
            }
            case Node.TEXT_NODE: {
                System.out.println(node.getNodeValue());
                break;
            }
        }
    }

    /**
     * 抽取html文档里的文本Text
     *
     * @param node
     * @param indent
     */
    public static void print(Node node, String indent) {
        // System.out.println(indent+node.getClass().getName());
        if (node.getNodeValue() != null) {
            if ("".equals(node.getNodeValue().trim())) {

            } else {
                System.out.print(indent);
                System.out.println(node.getNodeValue());
            }
        }

        Node child = node.getFirstChild();
        while (child != null) {
            print(child, indent + " ");
            child = child.getNextSibling();
        }
    }
}
