package br.upfe.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
	
	//busca dados no XML através do xpath mas utilizando um node
	public Optional<String> buscaNoXML(String string, Node node) throws XPathExpressionException {
		return Optional.of((String)xpath.evaluate(string, node, XPathConstants.STRING));
	}
	

	//Retorna um Stream de node a partir de um caminho do xml
	public Stream<Node> buscaNoXMLList(String string) throws XPathExpressionException {
		NodeList nodeList = (NodeList) xpath.evaluate(string, document, XPathConstants.NODESET);
		Stream<Node> nodeStream = IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item);
		return nodeStream;
	}
	
	//método para baixar arquivos no computador
	public File baixarArquivos(String stringUrl, String pathLocal, String nomeArquivoLocal) {
		String fileName = stringUrl.substring( stringUrl.lastIndexOf('/')+1, stringUrl.length() );
		try (FileOutputStream fos = new FileOutputStream(pathLocal+"/"+fileName)) {
			URL url = new URL(stringUrl);
			//Cria streams de leitura e de escrita
			InputStream is = url.openStream();
			//Le e grava 	
			int umByte = 0;
			while ((umByte = is.read()) != -1){
				fos.write(umByte);
			}
			return new File(pathLocal+"/"+fileName);
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao baixar o arquivo = " +nomeArquivoLocal + "favor tentar novamente");
		}
		return null;
	}

	
	
}
