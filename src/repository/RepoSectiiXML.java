package repository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domain.Sectie;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;


public class RepoSectiiXML extends RepoSectii{
	
	public String fName;
	
	public RepoSectiiXML(String fName) {
		
		this.fName=fName;
		loadData();
	}

    public void loadData() {
        try (InputStream input = new FileInputStream(fName)) {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
            readFromXml(reader);
        } catch (IOException | XMLStreamException f) {
        }
    }

    private void readFromXml(XMLStreamReader reader) throws XMLStreamException {
        Sectie st = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamReader.START_ELEMENT:
                    if (reader.getLocalName().equals("sectie")) {
                        st = citesteSectie(reader);
                        super.save(st);
                    }
                    break;
            }
        }

    }

    private Sectie citesteSectie(XMLStreamReader reader) throws XMLStreamException {

        String id = reader.getAttributeValue(null, "id");
        Sectie s = new Sectie();
        s.setId(Integer.parseInt(id));
        String currentPropertyName = "";
        String currentPropertyValue = "";
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamReader.START_ELEMENT:
                    if (reader.getLocalName().equals("property")) {
                        currentPropertyName = reader.getAttributeValue(null, "name");

                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals("sectie")) {
                        return s;
                    } else {
                        if (!currentPropertyValue.equals("")) {
                            if (currentPropertyName.equals("name")) {
                                s.setNume(currentPropertyValue);
                            }
                            if (currentPropertyName.equals("nrLoc")) {
                                s.setNrLoc(Integer.parseInt(currentPropertyValue));
                            }
                        }
                        currentPropertyName = "";
                        currentPropertyValue = "";
                    }
                    break;
                case XMLStreamReader.CHARACTERS:
                    currentPropertyValue = reader.getText().trim();
                    break;
            }
        }
        throw new XMLStreamException("nu s-a citit sectie");

    }
    
    public void saveData() {
    	try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("sectii");
            doc.appendChild(rootElement);
            for (Sectie x:super.all)
            {
                Element sectie = doc.createElement("sectie");
                rootElement.appendChild(sectie);
                sectie.setAttribute("id", x.getId().toString());
                appendSectieElement(doc,"name",x.getNume(),sectie);
                appendSectieElement(doc,"nrLoc",x.getNrLoc().toString(),sectie);
            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }
    
    private static void appendSectieElement(Document doc, String tagName, String textNode, Element sectieNode)
    {
        Element element=doc.createElement("property");
        element.setAttribute("name", tagName);
        element.appendChild(doc.createTextNode(textNode));
        sectieNode.appendChild(element);
    }
    
    void saveDocument(Document doc) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fName));

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}

