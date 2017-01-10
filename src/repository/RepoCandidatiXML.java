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

import domain.Candidat;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;

public class RepoCandidatiXML extends RepoCandidati{
private String fName;
	
	public RepoCandidatiXML(String fName) {
		
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
        Candidat st = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamReader.START_ELEMENT:
                    if (reader.getLocalName().equals("Candidat")) {
                        st = citesteCandidat(reader);
                        super.save(st);
                    }
                    break;
            }
        }

    }

    private Candidat citesteCandidat(XMLStreamReader reader) throws XMLStreamException {

        String id = reader.getAttributeValue(null, "id");
        Candidat c = new Candidat();
        c.setId(Integer.parseInt(id));
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
                    if (reader.getLocalName().equals("Candidat")) {
                        return c;
                    } else {
                        if (!currentPropertyValue.equals("")) {
                            if (currentPropertyName.equals("name")) {
                                c.setNume(currentPropertyValue);
                            }
                            if (currentPropertyName.equals("tel")) {
                                c.setTel(currentPropertyValue);
                            }
                            if (currentPropertyName.equals("adresa")) {
                                c.setAdresa(currentPropertyValue);
                            }
                            if (currentPropertyName.equals("varsta")) {
                                c.setVarsta(Integer.parseInt(currentPropertyValue));
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
        throw new XMLStreamException("Nu s-a citit nici un Candidat");

    }
    
    public void saveData() {
    	try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Candidati");
            doc.appendChild(rootElement);
            for (Candidat x:super.all)
            {
                Element Candidat = doc.createElement("Candidat");
                rootElement.appendChild(Candidat);
                Candidat.setAttribute("id", x.getId().toString());
                appendCandidatElement(doc,"name",x.getNume(),Candidat);
                appendCandidatElement(doc,"tel",x.getTel(),Candidat);
                appendCandidatElement(doc,"adresa",x.getAdresa(),Candidat);
                appendCandidatElement(doc,"varsta",x.getVarsta().toString(),Candidat);
            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }
    
    private static void appendCandidatElement(Document doc, String tagName, String textNode, Element CandidatNode)
    {
        Element element=doc.createElement("property");
        element.setAttribute("name", tagName);
        element.appendChild(doc.createTextNode(textNode));
        CandidatNode.appendChild(element);
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

