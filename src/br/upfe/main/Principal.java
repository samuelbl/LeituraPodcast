package br.upfe.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.upfe.modelo.Image;
import br.upfe.modelo.Item;
import br.upfe.util.Utilitario;

public class Principal {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite um endereço feed de podcast:");
		String url = sc.next();
		Utilitario util = new Utilitario();
		try {
			util.baixaDocumentoXML(url);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		Optional<String> title;
		Optional<String> link;
		Optional<String> image;
		Optional<String> language;
		Optional<String> copyright;
		Optional<String> docs;
		Optional<String> lastBuildDate;
		Optional<String> generator;
		Optional<String> webMaster;
		Optional<String> atom;
		Optional<List<Item>> itens;
		
		
		
		try {
			util.buscaNoXML("rss/channel/title").orElse("");
			link = util.buscaNoXML("rss/channel/title");
			link = util.buscaNoXML("rss/channel/title");
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		System.out.println(link.orElse("Não Encontrado"));
		
		
		
		
		
		

	}

}
