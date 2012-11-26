package xml;

import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.saxon.Configuration;
import net.sf.saxon.dom.DocumentWrapper;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;

import org.w3c.dom.Document;
import org.w3c.dom.xpath.XPathException;
import org.xml.sax.SAXException;

public class XQueryTester {
    static String fileName = "d:/test.xml";

    public static void main(String[] args) {
        Document document = getDocument(fileName);
        Configuration configuration = new Configuration();
        StaticQueryContext context = new StaticQueryContext(configuration, false);
        StringBuilder query = new StringBuilder();

        query.append("  for $students in //root/students");
        query.append("  for $student in $students/student");
        query.append("  where $student/name/text() != 'Jim' and $students/student = $student");
        query.append("  return $student");
        XQueryExpression expression = null;
        try {
            expression = context.compileQuery(query.toString());
            DynamicQueryContext context2 = new DynamicQueryContext(configuration);
            context2.setContextItem(new DocumentWrapper(document, null, configuration));

            final Properties props = new Properties();
            props.setProperty(OutputKeys.METHOD, "xml");
            props.setProperty(OutputKeys.INDENT, "yes");
            // 执行查询，并输出查询结果
            expression.run(context2, new StreamResult(System.out), props);
        } catch (XPathException e) {
            e.printStackTrace();
        } catch (net.sf.saxon.trans.XPathException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行查询
     */
    public static void select() {
        // 文件
        String fileString = "src/study/xquery/cd_catalog.xml";
        // 查询语句
        String query = " for $s in //CD/TITLE " + " return $s";

        Document document = getDocument(fileString); // 生产文档对象
        Configuration configuration = new Configuration();
        StaticQueryContext context = new StaticQueryContext(configuration, false);
        // 查询表达式对象
        XQueryExpression expression = null;
        try {
            expression = context.compileQuery(query);
            DynamicQueryContext context2 = new DynamicQueryContext(configuration);
            context2.setContextItem(new DocumentWrapper(document, null, configuration));

            final Properties props = new Properties();
            props.setProperty(OutputKeys.METHOD, "xml");
            props.setProperty(OutputKeys.INDENT, "yes");
            // 执行查询，并输出查询结果
            expression.run(context2, new StreamResult(System.out), props);
        } catch (XPathException e) {
            e.printStackTrace();
        } catch (net.sf.saxon.trans.XPathException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生产文档对象
     * 
     * @param xml
     *            文件名
     * @return
     */
    public static Document getDocument(String xml) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            document = builder.parse(xml);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.normalize();
        return document;
    }

    /**
     * 输入生成的文档内容
     * 
     * @param doc
     */
    public static void output(Document doc) {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer();
            Properties properties = transformer.getOutputProperties();
            properties.setProperty(OutputKeys.INDENT, "yes");
            properties.setProperty(OutputKeys.ENCODING, "GB2312");
            properties.setProperty(OutputKeys.METHOD, "xml");
            properties.setProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperties(properties);

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
