package br.upfe.modelo;

import java.time.Instant;
import java.util.Date;

public class Item {
	private String title;
	private String description;
	private Instant pubDate;
	private String enclosure;
	private String guid;
	
	
	
	public Item(String title, String description, Instant pubDate, String enclosure, String guid) {
		super();
		this.title = title;
		this.description = description;
		this.pubDate = pubDate;
		this.enclosure = enclosure;
		this.guid = guid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Instant getPubDate() {
		return pubDate;
	}
	public void setPubDate(Instant pubDate) {
		this.pubDate = pubDate;
	}
	public String getEnclosure() {
		return enclosure;
	}
	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
}
