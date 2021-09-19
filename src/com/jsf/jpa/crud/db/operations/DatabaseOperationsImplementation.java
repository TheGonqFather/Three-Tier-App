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

public class DatabaseOperationsImplementation implements DatabaseOperations{

	private static final String PERSISTENCE_UNIT_NAME = "ThreeTierApp";
	private static EntityManager em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
			.createEntityManager();
	private static EntityTransaction et = em.getTransaction();

	public List listAllBooks() {
		Query query = em.createQuery("SELECT b FROM Book b");
		List bookList = query.getResultList();
		if (bookList != null && bookList.size() > 0) {
			return bookList;
		} else {
			return null;
		}
	}

	public String addBook(String title, String author, String isbn) {
		if (!et.isActive()) {
			et.begin();
		}

		Book newBook = new Book();
		newBook.setTitle(title);
		newBook.setAuthor(author);
		newBook.setIsbn(isbn);

		em.persist(newBook);
		et.commit();
		return "booksList.xhtml?faces-redirect=true";
	}

	public String deleteBook(Integer bookId) {
		if (!et.isActive()) {
			et.begin();
		}

		Book deleteBook = new Book();
		if (isBookIdPresent(bookId)) {
			deleteBook.setBookid(bookId);
			em.remove(em.merge(deleteBook));
		}
		et.commit();
		return "booksList.xhtml?faces-redirect=true";
	}

	public String updateBook(Integer bookId, String title) {
		if (!et.isActive()) {
			et.begin();
		}

		if (isBookIdPresent(bookId)) {
			Query query = em.createQuery("UPDATE Book b SET b.title=:title WHERE b.bookid= :bookid");
			query.setParameter("bookid", bookId);
			query.setParameter("title", title);
			int updateCount = query.executeUpdate();
			if (updateCount > 0) {
				System.out.println("Record For BookId: " + bookId + " Is Updated");
			}
		}
		et.commit();
		FacesContext.getCurrentInstance().addMessage("editBookForm: bookId",
				new FacesMessage("Book Record #" + bookId + " Is Successfully Updated In Db"));
		return "booksList.xhtml";
	}

	private static boolean isBookIdPresent(Integer bookId) {
		boolean idResult = false;
		Query query = em.createQuery("SELECT b FROM Book b WHERE b.bookid = :id");
		query.setParameter("id", bookId);
		Book selectedBookId = (Book) query.getSingleResult();
		if (selectedBookId != null) {
			idResult = true;
		}
		return idResult;
	}
}
