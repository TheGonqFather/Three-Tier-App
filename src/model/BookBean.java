package model;

import java.util.List;

//import javax.faces.context.FacesContext;
import com.jsf.jpa.crud.db.operations.DatabaseOperationsImplementation;

public class BookBean {

	private Integer bookid;
	private String title;
	private String author;
	private String isbn;
	private DatabaseOperationsImplementation dboi;

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List listAllBooks() {
		return dboi.listAllBooks();
	}

	public String addBook(BookBean bookBean) {
		return dboi.addBook(bookBean.getTitle(), bookBean.getAuthor(), bookBean.getIsbn());
	}

	public String deleteBook(Integer bookId) {
		return dboi.deleteBook(bookId);
	}

//	public String updateBook(BookBean bookBean) {
//		return dboi.updateBook(bookBean.getBookid(), bookBean.getTitle());
//	}

}
