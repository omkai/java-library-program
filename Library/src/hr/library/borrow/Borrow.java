package hr.library.borrow;

import hr.library.book.Book;
import hr.library.users.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Borrow {

	private User user;
	private Book book;
	private String borrowDate;
	private int borrowTime;
	private String returnDate;
	
	SimpleDateFormat ft = new SimpleDateFormat("E dd.MM.yyyy HH:mm:ss");
	Calendar c = Calendar.getInstance();
	
	public Borrow(User user, Book book, int borrowTime) {
		this.user = user;
		this.book = book;
		this.borrowDate = ft.format(new Date());
		this.borrowTime = borrowTime;
		setReturnDate(borrowTime);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}

	public String getReturnDate() {
		return returnDate;
	}
	
	public int getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(int borrowTime) {
		this.borrowTime = borrowTime;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public void setReturnDate(int time) {
		try {
			c.setTime(ft.parse(borrowDate));
		} catch (ParseException e) {
			System.out.println(e);
		}
		c.add(Calendar.DAY_OF_MONTH, time);
		this.returnDate = new SimpleDateFormat("E dd.MM.yyyy").format(c.getTime());
	}

	@Override
	public String toString() {
		return "Borrowings [user=" + user + ", book=" + book + ", borrowDate=" + borrowDate + ", borrowTime="
				+ borrowTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((borrowDate == null) ? 0 : borrowDate.hashCode());
		result = prime * result + borrowTime;
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result + ((ft == null) ? 0 : ft.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrow other = (Borrow) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (borrowDate == null) {
			if (other.borrowDate != null)
				return false;
		} else if (!borrowDate.equals(other.borrowDate))
			return false;
		if (borrowTime != other.borrowTime)
			return false;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		if (ft == null) {
			if (other.ft != null)
				return false;
		} else if (!ft.equals(other.ft))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}	
}
