package br.upfe.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.management.InstanceNotFoundException;
import javax.swing.text.DateFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import br.upfe.modelo.Image;
import br.upfe.modelo.Item;
import br.upfe.modelo.Podcast;
import br.upfe.util.Utilitario;

/**
 * Classe main, responsável pela execução do programa
 * @author samuel, eliandro
 *
 */
public class Principal {

	public static void main(String[] args) {
		//Iniciar o scanner e solicita endereço do PodCast
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite um endereço feed de podcast:");
		String url = sc.next();
		
		//Instancia da classe utilitário para parse no XML
		Utilitario util = new Utilitario();
		
		//Efetua download do XML
		try {
			util.baixaDocumentoXML(url);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		//Inicia variáveis optional para parse do XML
		Optional<String> title = null;
		Optional<String> link = null;
		Optional<String> description = null;
		Optional<String> imageUrl = null;
		Optional<String> imageTitle = null;
		Optional<String> imageLink = null;
		Optional<String> language = null;
		Optional<String> copyright = null;
		Optional<String> docs = null;
		Optional<String> lastBuildDateString = null;
		Instant lastBuildDate = null;
		Optional<String> generator = null;
		Optional<String> webMaster = null;
		Optional<String> atom = null;
		Optional<String> itemTitle = null;
		Optional<String> itemDescription = null; 
		Optional<String> itemPubDate = null;
		Optional<String> itemEnclosure = null;
		Optional<String> itemGuid = null;
		
		List<Item> itensPod = new ArrayList<Item>();
		

		//Busca dados conforme xpath
		try {
			title = util.buscaNoXML("rss/channel/title");
			link = util.buscaNoXML("rss/channel/link");
			description = util.buscaNoXML("rss/channel/description");
			imageUrl = util.buscaNoXML("rss/channel/image/url");
			imageTitle = util.buscaNoXML("rss/channel/image/title");
			imageLink = util.buscaNoXML("rss/channel/image/link");
			language = util.buscaNoXML("rss/channel/language");
			copyright = util.buscaNoXML("rss/channel/copyright");
			docs = util.buscaNoXML("rss/channel/docs");
			lastBuildDateString = util.buscaNoXML("rss/channel/lastBuildDate");
			//Converte data utilizando lambda
		    lastBuildDate = converteStringToInstant(lastBuildDateString);
			generator = util.buscaNoXML("rss/channel/generator");
			webMaster = util.buscaNoXML("rss/channel/webMaster");
			
			//Chama método para obter um Stream do Node de itens
			Stream<Node> nodeStream =  util.buscaNoXMLList("//item");
			//foreach do nodeStream, instanciando um novo objeto para cada item e adicionando no ArrayList
			nodeStream.forEach((node)-> {
				try {
					Item item = new Item(util.buscaNoXML("title", node).orElse("NULO"), util.buscaNoXML("description", node).orElse("NULO"), converteStringToInstant(util.buscaNoXML("pubDate", node)), util.buscaNoXML("enclosure", node).orElse("NULO"), util.buscaNoXML("guid", node).orElse("NULO"));
					itensPod.add(item);
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}
			});
			

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		//instancia classes de modelos e armazena variáveis fazendo o parse do XML para um Objeto.
		
		
		//http://leopoldomt.com/if710/fronteirasdaciencia.xml

		Image image = new Image(imageUrl.orElse("NULO"), imageTitle.orElse("NULO"), imageLink.orElse("NULO"));
		
		Podcast podcast = new Podcast(title.orElse("NULO"), link.orElse("NULO"), description.orElse("NULO"), image, language.orElse("NULO"), copyright.orElse("NULO"), docs.orElse("NULO"), lastBuildDate, generator.orElse("NULO"), webMaster.orElse("NULO"), itensPod);
		
		System.out.println(podcast);
		System.out.println(podcast.getImage());
		for (Item item : podcast.getItens()) {
			System.out.println(item);
		}
		
		
		
		

	}

	/**
	 * Método utilizado para converter Optional<String> para Instant
	 * @param dataString
	 * @return Instant
	 */
	private static Instant converteStringToInstant(Optional<String> dataString) {
		return DateTimeFormatter.RFC_1123_DATE_TIME.parse(dataString.orElse("Sun, 01 Jan 0000 00:00:00 GMT"),Instant::from);
	}

}
