package com.jsf.jpa.crud.db.operations;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.BookBean;
import model.Book;

public class DatabaseOperations {

	private static final String PERSISTENCE_UNIT_NAME = "ThreeTierApp";
	private static EntityManager em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
			.createEntityManager();
	private static EntityTransaction et = em.getTransaction();

	public static List listAllBooks() {
		Query query = em.createQuery("SELECT b FROM Book b");
		List bookList = query.getResultList();
		if (bookList != null && bookList.size() > 0) {
			return bookList;
		} else {
			return null;
		}
	}

	public static String addBook(String title, String author, String isbn) {
		if (!et.isActive()) {
			et.begin();
		}

		Book newBook = new Book();
//		newBook.setBookid(getMaxBookId());
		newBook.setTitle(title);
		newBook.setAuthor(author);
		newBook.setIsbn(isbn);

		em.persist(newBook);
		et.commit();
		return "homePage.xhtml?faces-redirect=true";
	}

	public static String deleteBook(String isbn) {
		if (!et.isActive()) {
			et.begin();
		}

		Book deleteBook = new Book();
		if (isBookIdPresent(isbn)) {
			deleteBook.setIsbn(isbn);
			em.remove(em.merge(deleteBook));
		}
		et.commit();
		return "homePage.xhtml?faces-redirect=true";
	}

	public static String updateBook(String isbn, String title) {
		if (!et.isActive()) {
			et.begin();
		}

		if (isBookIdPresent(isbn)) {
			Query query = em.createQuery("UPDATE Book b SET b.title=:name WHERE b.isbn= :isbn");
			query.setParameter("isbn", isbn);
			query.setParameter("title", title);
			int updateCount = query.executeUpdate();
			if (updateCount > 0) {
				System.out.println("Record For BookId: " + isbn + " Is Updated");
			}
		}
		et.commit();
		FacesContext.getCurrentInstance().addMessage("editBookForm: bookId",
				new FacesMessage("Book Record #" + isbn + " Is Successfully Updated In Db"));
		return "booksList.xhtml";
	}

//	private static int getMaxBookId() {
//		int maxBookId = 1;
//		Query query = em.createQuery("SELECT MAX(b.bookId)+1 FROM Book b");
//		if (query.getSingleResult() != null) {
//			maxBookId = (Integer) query.getSingleResult();
//		}
//		return maxBookId;
//	}

	private static boolean isBookIdPresent(String isbn) {
		boolean isbnResult = false;
		Query query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn");
		query.setParameter("isbn", isbn);
		Book selectedBookIsbn = (Book) query.getSingleResult();
		if (selectedBookIsbn != null) {
			isbnResult = true;
		}
		return isbnResult;
	}

}
