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
import domain.Inscriere;
import domain.Sectie;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;

public class RepoInscrieriXML extends RepoInscrieri{
private String fName;
	
	public RepoInscrieriXML(String fName) {
		
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
        Inscriere st = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamReader.START_ELEMENT:
                    if (reader.getLocalName().equals("Inscriere")) {
                        st = citesteInscriere(reader);
                        super.save(st);
                    }
                    break;
            }
        }

    }

    private Inscriere citesteInscriere(XMLStreamReader reader) throws XMLStreamException {

        String id = reader.getAttributeValue(null, "id");
        Inscriere i = new Inscriere();
        Candidat c = new Candidat();
        Sectie s = new Sectie();
        i.setId(Integer.parseInt(id));
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
                    if (reader.getLocalName().equals("Inscriere")) {
                    	i.set_candidat(c);
                    	i.set_sectie(s);
                        return i;
                    } else {
                        if (!currentPropertyValue.equals("")) {
                        	if (currentPropertyName.equals("idC")) {
                                c.setId(Integer.parseInt(currentPropertyValue));
                            }
                            if (currentPropertyName.equals("nameC")) {
                                c.setNume(currentPropertyValue);
                            }
                            if (currentPropertyName.equals("telC")) {
                                c.setTel(currentPropertyValue);
                            }
                            if (currentPropertyName.equals("adresaC")) {
                                c.setAdresa(currentPropertyValue);
                            }
                            if (currentPropertyName.equals("varstaC")) {
                                c.setVarsta(Integer.parseInt(currentPropertyValue));
                            }
                            if (currentPropertyName.equals("idS")) {
                                s.setId(Integer.parseInt(currentPropertyValue));
                            }
                            if (currentPropertyName.equals("nameS")) {
                                s.setNume(currentPropertyValue);
                            }
                            if (currentPropertyName.equals("nrLocS")) {
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
        throw new XMLStreamException("Nu s-a citit nici o Inscriere");

    }
    
    public void saveData() {
    	try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Inscrieri");
            doc.appendChild(rootElement);
            for (Inscriere x:super.all)
            {
                Element Inscriere = doc.createElement("Inscriere");
                rootElement.appendChild(Inscriere);
                Inscriere.setAttribute("id", x.getId().toString());
                appendInscriereElement(doc,"idC",x.get_candidat().getId().toString(),Inscriere);
                appendInscriereElement(doc,"nameC",x.get_candidat().getNume(),Inscriere);
                appendInscriereElement(doc,"telC",x.get_candidat().getTel(),Inscriere);
                appendInscriereElement(doc,"adresaC",x.get_candidat().getAdresa(),Inscriere);
                appendInscriereElement(doc,"varstaC",x.get_candidat().getVarsta().toString(),Inscriere);
                appendInscriereElement(doc,"idS",x.get_sectie().getId().toString(),Inscriere);
                appendInscriereElement(doc,"nameS",x.get_sectie().getNume(),Inscriere);
                appendInscriereElement(doc,"nrLocS",x.get_sectie().getNrLoc().toString(),Inscriere);
            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }
    
    private static void appendInscriereElement(Document doc, String tagName, String textNode, Element InscriereNode)
    {
        Element element=doc.createElement("property");
        element.setAttribute("name", tagName);
        element.appendChild(doc.createTextNode(textNode));
        InscriereNode.appendChild(element);
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

