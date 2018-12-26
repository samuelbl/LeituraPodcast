package br.upfe.main;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.plaf.synth.SynthSeparatorUI;
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
	//instância da classe Scanner
	private static Scanner sc = new Scanner(System.in);
	//instância da classe utilitário para parse no XML e outros métodos utilitários
	private static Utilitario util = Utilitario.getUtil();
	
	public static void main(String[] args) {
		//Solicita endereço do PodCast
		System.out.println("Digite um endereço feed de podcast: Ex: http://leopoldomt.com/if710/fronteirasdaciencia.xml");
		String url = sc.next();
		
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
		    lastBuildDate = util.converteStringToInstant(lastBuildDateString);
			generator = util.buscaNoXML("rss/channel/generator");
			webMaster = util.buscaNoXML("rss/channel/webMaster");
			
			//Chama método para obter um Stream do Node de itens
			Stream<Node> nodeStream =  util.buscaNoXMLList("//item");
			
			//Foreach do nodeStream, instanciando um novo objeto para cada item, adicionando no ArrayList e fazendo validações necessárias
			nodeStream.forEach((node)-> {
				try {
					Item item = new Item(util.buscaNoXML("title", node).orElse("NULO"), util.buscaNoXML("description", node).orElse("NULO"), util.converteStringToInstant(util.buscaNoXML("pubDate", node)), util.buscaNoXML("enclosure/@url", node).orElse("NULO"), util.buscaNoXML("guid", node).orElse("NULO"));
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
		Boolean opc = true;
		//Entra no menu principal
		while (opc) {
			menuPrincipal(podcast);
			System.out.println("O sistema irá retornar ao menu prinicipal, deseja sair (s/N)");
			String opcTexto = sc.next();
			//Efetua validação da opção selecionada, deixando que qualquer digito diferente de S seja considerado o padrão que é N
			if (opcTexto.toUpperCase().equals("S")) {
				opc = false;
			}
		}
	}

	/**
	 * Método reponsável pelo menu da aplicação
	 * @param podcast
	 */
	private static void menuPrincipal(Podcast podcast) {
		//sc = new Scanner(System.in);
		//util = Utilitario.getUtil();
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("---------------------Menu Principal------------------------------------------");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Para download de episódios, digite 'D'");
		System.out.print("ou 'B' para buscar episódios: ");
		String opc = sc.next().toUpperCase();
		while (!opc.equals("D") && !opc.equals("B")){
			System.out.println("Favor informar somente 'B' ou 'D'");
			opc = sc.next().toUpperCase();
		}
		if (opc.equals("D")) {
			menuDownloadEpisodios(podcast);
		}
		if (opc.equals("B")) {
			menuBuscaEpisodios(podcast);
		}
	}

	/**
	 * Método reponsável pelo menu e operações de downloads da aplicação
	 * @param podcast
	 */
	private static void menuBuscaEpisodios(Podcast podcast) {
		//sc = new Scanner(System.in);
		//util = Utilitario.getUtil();
		System.out.println("Qual a forma de busca? ");
		System.out.println("Strings - digite 'S'");
		System.out.println("Datas   - digite 'D'");
		String opc = sc.next().toUpperCase();
		while (!opc.equals("D") && !opc.equals("S")){
			System.out.println("Favor informar somente 'S' ou 'D'");
			opc = sc.next().toUpperCase();
		}
		buscaEpisodios(podcast, opc);
		
	}

	/**
	 * Método reponsável por buscar episódios
	 * @param podcast
	 */
	private static void buscaEpisodios(Podcast podcast, String opc) {
		util = new Utilitario();
		//sc = new Scanner(System.in);
		Stream<Item> streamItens = podcast.getItens().stream();
		
		//busca por String
		if (opc.equals("S")){
			System.out.print("Digite o episódio ou parte dele para efetuar a busca: ");
			String busca = sc.next().toUpperCase();
			//Utiliza o filter para buscar episódios que contenham a string informada
			streamItens.filter(b -> b.getTitle().toUpperCase().contains(busca) 
					|| b.getDescription().toUpperCase().contains(busca)).forEach(System.out::println);
		}
		
		//busca por Data
		else{			
			String dtInicial = null;
			String dtFinal = null;
			while(!util.isValidFormat(dtInicial)){
				System.out.print("Informa a data inicial para busca: dd/mm/yyyy ");
				dtInicial = sc.next().toUpperCase();
			}
			while(!util.isValidFormat(dtFinal)){
				System.out.print("Informa a data final para busca: dd/mm/yyyy");
				dtFinal = sc.next().toUpperCase();
			}
			//converte as strings em instant
			final Instant dtInicialFiltro = util.convertDateToInstant(dtInicial, true);
			final Instant dtFinalFiltro = util.convertDateToInstant(dtFinal, false);
				
			//faz a procura pelo período dentro da stream de itens
			streamItens.filter(b -> b.getPubDate().isAfter(dtInicialFiltro) 
					&& b.getPubDate().isBefore(dtFinalFiltro))
					.forEach(System.out::println);
		}
		
	}


	/**
	 * Método reponsável pelo menu e operações de downloads da aplicação
	 * @param podcast
	 */
	private static void menuDownloadEpisodios(Podcast podcast) {
		//sc = new Scanner(System.in);
		util = new Utilitario();
		// variável de controle para o while, com objetivo de repetir a
		// pergunta em caso de exceção
		boolean controle = false;
		int qtEpisodiosDownload = 0;
		// Questinamento da quantidade de arquivos e tratamentos
		Scanner scanner;
		while (!controle) {
			scanner = new Scanner(System.in);
			try {
				System.out.println("Quantos episódios deseja baixar: ");
				qtEpisodiosDownload = scanner.nextInt();
				if (qtEpisodiosDownload > podcast.getItens().size()) {
					throw new ValorMaiorException(podcast.getItens().size());
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
