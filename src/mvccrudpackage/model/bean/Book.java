package mvccrudpackage.model.bean;

import java.sql.Timestamp;

public class Book {
	
	// Attributes from Book table
	protected int bid;
	protected int cid;
	protected String booktitle;
	protected String description;
	protected String author;
	protected Timestamp publisheddate;
	protected String isbn;
	protected double price;
	protected int noofpages;
	
	// Attribute from Book-Category table
	protected String category;

	/* Constructor */
	public Book() {

	}

	public Book(int bid, String booktitle, String author) {
		this.setBid(bid);
		this.setBooktitle(booktitle);
		this.setAuthor(author);
	}
	
	public Book(int bid, int cid, String booktitle, String author, String isbn) {
		this.setBid(bid);
		this.setCid(cid);
		this.setBooktitle(booktitle);
		this.setAuthor(author);
		this.setIsbn(isbn);
	}
	
	public Book(int cid, String booktitle, String author,  String isbn) {
		this.setCid(cid);
		this.setBooktitle(booktitle);
		this.setAuthor(author);
		this.setIsbn(isbn);
	}
	
	public Book(int cid, String booktitle, String author,  String isbn, String description) {
		this.setCid(cid);
		this.setBooktitle(booktitle);
		this.setAuthor(author);
		this.setIsbn(isbn);
		this.setDescription(description);
	}
	
	public Book(int bid, int cid, String booktitle, String author, String isbn, String description) {
		this.setBid(bid);
		this.setCid(cid);
		this.setBooktitle(booktitle);
		this.setAuthor(author);
		this.setIsbn(isbn);
		this.setDescription(description);
	}
	
	/* Setter and Getter */
	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getBooktitle() {
		return booktitle;
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getPublisheddate() {
		return publisheddate;
	}

	public void setPublisheddate(Timestamp publisheddate) {
		this.publisheddate = publisheddate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNoofpages() {
		return noofpages;
	}

	public void setNoofpages(int noofpages) {
		this.noofpages = noofpages;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
