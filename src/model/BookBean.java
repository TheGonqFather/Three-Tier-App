package model;

import java.util.List;

//import javax.faces.context.FacesContext;
import com.jsf.jpa.crud.db.operations.DatabaseOperations;

public class BookBean {

	private Integer bookid;
	private String title;
	private String author;
	private String isbn;

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
		return DatabaseOperations.listAllBooks();
	}

	public String addBook(BookBean bookBean) {
		return DatabaseOperations.addBook(bookBean.getTitle(), bookBean.getAuthor(), bookBean.getIsbn());
	}

	public String deleteBook(Integer bookId) {
		return DatabaseOperations.deleteBook(bookId);
	}

//	public String updateBook(BookBean bookBean) {
//		return DatabaseOperations.updateBook(bookBean.getTitle(), bookBean.getIsbn());
//	}

}
