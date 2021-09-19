package com.jsf.jpa.crud.db.operations;

import java.util.List;

public interface DatabaseOperations {

	public List listAllBooks();

	public String addBook(String title, String author, String isbn);

	public String deleteBook(Integer bookId);

//	public String updateBook(Integer bookId, String title);

}
