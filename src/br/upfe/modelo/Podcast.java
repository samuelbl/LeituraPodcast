package br.upfe.modelo;

import java.util.Date;
import java.util.List;

public class Podcast {
	private String title;
	private String link;
	private Image image;
	private String language;
	private String copyright;
	private String docs;
	private Date lastBuildDate;
	private String generator;
	private String webMaster;
	private String atom;
	private List<Item> itens;
	
	
	
	public Podcast(String title, String link, Image image, String language, String copyright, String docs,
			Date lastBuildDate, String generator, String webMaster, String atom, List<Item> itens) {
		super();
		this.title = title;
		this.link = link;
		this.image = image;
		this.language = language;
		this.copyright = copyright;
		this.docs = docs;
		this.lastBuildDate = lastBuildDate;
		this.generator = generator;
		this.webMaster = webMaster;
		this.atom = atom;
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
	public Date getLastBuildDate() {
		return lastBuildDate;
	}
	public void setLastBuildDate(Date lastBuildDate) {
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
	public String getAtom() {
		return atom;
	}
	public void setAtom(String atom) {
		this.atom = atom;
	}
	public List<Item> getItens() {
		return itens;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
}
