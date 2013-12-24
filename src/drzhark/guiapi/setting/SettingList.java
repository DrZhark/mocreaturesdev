package drzhark.guiapi.setting;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import drzhark.guiapi.ModSettings;



public class SettingList extends Setting<ArrayList<String>> {

    public SettingList(String title) {
        this(title, new ArrayList<String>());
    }

    public SettingList(String title, ArrayList<String> defaultvalue) {
        backendName = title;
        defaultValue = defaultvalue;
        values.put("", defaultvalue);
    }

    @Override
    public void fromString(String s, String context) {
        ArrayList<String> list = new ArrayList<String>();
        try {

            DocumentBuilderFactory builderFact = DocumentBuilderFactory
                    .newInstance();
            builderFact.setIgnoringElementContentWhitespace(true);
            builderFact.setValidating(true);
            builderFact.setCoalescing(true);
            builderFact.setIgnoringComments(true);
            DocumentBuilder docBuilder = builderFact.newDocumentBuilder();
            Document doc = docBuilder.parse(s);

            Element localElement = (Element) doc.getChildNodes().item(1);

            NodeList localNodeList = localElement.getChildNodes();

            for (int i = 0; i < localNodeList.getLength(); i++) {
                String val = localNodeList.item(i).getNodeValue();
                list.add(val);
            }
            values.put(context, list);
            if (displayWidget != null) {
                displayWidget.update();
            }
        } catch (Throwable e) {

        }
    }

    @Override
    public ArrayList<String> get(String context) {
        if (values.get(context) != null) {
            return values.get(context);
        } else if (values.get("") != null) {
            return values.get("");
        } else {
            return defaultValue;
        }
    }

    @Override
    public void set(ArrayList<String> v, String context) {
        values.put(context, v);
        if (parent != null) {
            parent.save(context);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    @Override
    public String toString(String context) {
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element baseElement = (Element) doc.appendChild(doc
                    .createElement("list"));
            ArrayList<String> prop = get(context);
            synchronized (prop) {
                Iterator<String> localIterator = prop.iterator();
                while (localIterator.hasNext()) {
                    String str = localIterator.next();
                    baseElement.appendChild(doc.createTextNode(str));
                }
            }

            TransformerFactory localTransformerFactory = TransformerFactory
                    .newInstance();
            Transformer localTransformer = null;
            localTransformer = localTransformerFactory.newTransformer();
            localTransformer.setOutputProperty("method", "xml");
            localTransformer.setOutputProperty("encoding", "UTF8");
            DOMSource localDOMSource = new DOMSource(doc);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            StreamResult localStreamResult = new StreamResult(output);
            localTransformer.transform(localDOMSource, localStreamResult);

            return output.toString("UTF-8");
        } catch (Throwable e) {
            ModSettings.dbgout("Error writing SettingList from context '"
                    + context + "': " + e);
            return "";
        }
    }
}
