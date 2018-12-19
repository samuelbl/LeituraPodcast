package br.upfe.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Utilitario {
	
	private DocumentBuilderFactory factory; 
	private XPath xpath;
	Document document; 
	
	public Utilitario() {
		factory = DocumentBuilderFactory.newInstance();
		xpath = XPathFactory.newInstance().newXPath();
	}

	public Document baixaDocumentoXML(String url) throws ParserConfigurationException, MalformedURLException, SAXException, IOException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(new URL(url).openStream());
		return document;
	}

	public Optional<String> buscaNoXML(String string) throws XPathExpressionException {
		return Optional.of(xpath.evaluate(string, document));
	}

}
