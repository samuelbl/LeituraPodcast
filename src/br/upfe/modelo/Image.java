package br.upfe.modelo;

/**
 * Classe de modelo para dados da tag Image
 * @author samuel, eliandro
 *
 */

public class Image {
	private String url;
	private String title;
	private String link;
	
	
	
	public Image(String url, String title, String link) {
		super();
		this.url = url;
		this.title = title;
		this.link = link;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "Image [url=" + url + ", title=" + title + ", link=" + link + "]";
	}
	
	
}
