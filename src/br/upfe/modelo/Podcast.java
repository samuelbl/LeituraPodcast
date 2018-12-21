package br.upfe.modelo;


/**
 * Classe de modelo com os dados do podcast
 * @author samuel, eliandro
 *
 */

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Podcast {
	private String title;
	private String link;
	private String description;
	private Image image;
	private String language;
	private String copyright;
	private String docs;
	private Instant lastBuildDate;
	private String generator;
	private String webMaster;
	private List<Item> itens;
	
	
	
	public Podcast(String title, String link, String description, Image image, String language, String copyright, String docs,
			Instant lastBuildDate, String generator, String webMaster, List<Item> itens) {
		super();
		this.title = title;
		this.link = link;
		this.description = description;
		this.image = image;
		this.language = language;
		this.copyright = copyright;
		this.docs = docs;
		this.lastBuildDate = lastBuildDate;
		this.generator = generator;
		this.webMaster = webMaster;
		this.itens = itens;
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
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getDocs() {
		return docs;
	}
	public void setDocs(String docs) {
		this.docs = docs;
	}
	public Instant getLastBuildDate() {
		return lastBuildDate;
	}
	public void setLastBuildDate(Instant lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getWebMaster() {
		return webMaster;
	}
	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}
	public List<Item> getItens() {
		return itens;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Podcast [title=" + title + ", link=" + link + ", description=" + description + ", image=" + image
				+ ", language=" + language + ", copyright=" + copyright + ", docs=" + docs + ", lastBuildDate="
				+ lastBuildDate + ", generator=" + generator + ", webMaster=" + webMaster
				+ ", itens=" + itens + "]";
	}
	
	
	
}
