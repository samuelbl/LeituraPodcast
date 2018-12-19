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


/**
 * Classe responsável por métodos utilitários inerentes a leitura do XML
 * @author samuel, eliandro
 *
 */

public class Utilitario {
	
	private DocumentBuilderFactory factory; 
	private XPath xpath;
	Document document; 
	
	public Utilitario() {
		//inicia variáveis no construtor
		factory = DocumentBuilderFactory.newInstance();
		xpath = XPathFactory.newInstance().newXPath();
	}

	//realiza o dowload do documento conforme url passada e retorna um document
	public Document baixaDocumentoXML(String url) throws ParserConfigurationException, MalformedURLException, SAXException, IOException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(new URL(url).openStream());
		return document;
	}

	//busca dados no XML através do xpath
	public Optional<String> buscaNoXML(String string) throws XPathExpressionException {
		return Optional.of(xpath.evaluate(string, document));
	}
	
	public Optional<String> buscaNoXMLList(String string) throws XPathExpressionException {
		return Optional.of(xpath.evaluate(string, document));
	}
	
}
