package br.upfe.main;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import br.upfe.exception.NaoEncontradoException;
import br.upfe.exception.ValorMaiorException;
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
	
	//Mensagens de erros a serem utilizadas pelo main
	static final String NAO_ENCONTRADO = "Programa não encontrado";
	static final String DOWNLOAD = "Falha no download";
	
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
			System.out.println(DOWNLOAD);
			main(null);
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
			
			//Foreach do nodeStream, instanciando um novo objeto para cada item e adicionando no ArrayList
			nodeStream.forEach((node)-> {
				try {
					Item item = new Item(util.buscaNoXML("title", node).orElse("NULO"), util.buscaNoXML("description", node).orElse("NULO"), converteStringToInstant(util.buscaNoXML("pubDate", node)), util.buscaNoXML("enclosure/@url", node).orElse("NULO"), util.buscaNoXML("guid", node).orElse("NULO"));
					itensPod.add(item);
				} catch (XPathExpressionException e) {
					e.printStackTrace();
					System.out.println("Erro ao ler o XML dos itens, favor tentar novamente");
					main(null);
				}
			});
			

		} catch (XPathExpressionException e) {
			System.out.println("Erro ao ler o XML, favor tentar novamente");
			main(null);
		}
		
		//inicia classes de modelos e armazena variáveis fazendo o parse do XML para o objeto podcast.
		
		
		//http://leopoldomt.com/if710/fronteirasdaciencia.xml

		Image image = new Image(imageUrl.orElse("NULO"), imageTitle.orElse("NULO"), imageLink.orElse("NULO"));
		Podcast podcast = new Podcast(title.orElse("NULO"), link.orElse("NULO"), description.orElse("NULO"), image, language.orElse("NULO"), copyright.orElse("NULO"), docs.orElse("NULO"), lastBuildDate, generator.orElse("NULO"), webMaster.orElse("NULO"), itensPod);
		int sizeList = podcast.getItens().size();
		
		//Busca ultimo programa utilizando Option para evitar nullpointerException
		Optional<Item> ultimoItem = Optional.of(podcast.getItens().get(sizeList-1));
		Item ultimoPrograma = new Item();
		try {
			ultimoPrograma = ultimoItem.orElseThrow(() -> new NaoEncontradoException());
		} catch (NaoEncontradoException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Título último programa: " + ultimoPrograma.getTitle()) ;
		System.out.println("Data último programa: " + ultimoPrograma.getPubDate());
		System.out.println("Link: " + ultimoPrograma.getEnclosure());
		System.out.println("---------------------------------------------------------------");
		
		System.out.println("Para download de episódios, digite 'D'");
		System.out.print("ou 'B' para buscar episódios: ");
		String opc = sc.next().toUpperCase();
		while (!opc.equals("D") && !opc.equals("B")){
			System.out.println("Favor informar somente 'B' ou 'D'");
			opc = sc.next().toUpperCase();
		}
		if (opc.equals("D")){
			//variável de controle para o while, com objetivo de repetir a pergunta em caso de exceção
			boolean controle = false;
			int qtEpisodiosDownload = 0;
			//Questinamento da quantidade de arquivos e tratamentos
			while (!controle) {
				Scanner scanner = new Scanner(System.in);
				try {
					System.out.println("Quantos episódios deseja baixar: ");
					qtEpisodiosDownload = scanner.nextInt();
					if (qtEpisodiosDownload > sizeList) {
						throw new ValorMaiorException(sizeList);
					}
					controle = true;
				} catch (InputMismatchException e) {
					System.out.println("Deve ser informado apenas números");
				} catch (ValorMaiorException f) {
					System.out.println(f.getMessage());
				}
				
				
			}

			System.out.println("Qual diretório deseja efetuar o download? Ex: /tmp");
			String path = sc.next();
			for (int i = 0; i < qtEpisodiosDownload; i++) {
				Item prog = podcast.getItens().get(i);
				new Thread(() -> util.baixarArquivos(prog.getEnclosure(), path, prog.getTitle())).start();
			}
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
