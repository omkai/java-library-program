package hr.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import hr.library.Borrowings.Borrowings;
import hr.library.book.Book;
import hr.library.book.Genre;
import hr.library.librarians.Librarian;
import hr.library.users.User;

public class Database {

	//***CONNECTION ESTABLISHMENT***//
	public static Connection getConnection(String db) {

		try {
			//			Class.forName("com.mysql.cj.jdbc.Driver"); not needed
			Connection c = null;
			c = DriverManager.getConnection("jdbc:mysql://localhost/" + db, "root", "1234");
			System.out.println("Connected");
			return c;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	//Creates database with name library
	public static void createDatabase() {
		Connection con = getConnection(""); //-> empty because there is no database yet

		try {
			PreparedStatement createDb = con.prepareStatement("CREATE DATABASE IF NOT EXISTS library");
			createDb.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	//***TABLE CREATION***//
	public static void createTables() {

		try {
			Connection con = getConnection("library");

			PreparedStatement createBooks = con.prepareStatement("CREATE TABLE IF NOT EXISTS books"
					+ "("
					+ " bookId INT,"
					+ " bookName VARCHAR(100),"
					+ " bookAuthor VARCHAR(50),"
					+ " bookEdition SMALLINT,"
					+ " bookGenre VARCHAR(255),"
					+ " bookBorrowed VARCHAR(5), "
					+ " PRIMARY KEY(bookId))"
					);
			createBooks.executeUpdate();
			System.out.println("Book table created");

			PreparedStatement createUsers = con.prepareStatement("CREATE TABLE IF NOT EXISTS users"
					+ "("
					+ " userId BIGINT NOT NULL, "
					+ " userName VARCHAR(40), "
					+ " userSurname VARCHAR(40), "
					+ " userNoOfBooks TINYINT, "
					+ " PRIMARY KEY(userId))"
					);
			createUsers.executeUpdate();
			System.out.println("User table created");

			PreparedStatement createLibrarians = con.prepareStatement("CREATE TABLE IF NOT EXISTS librarians"
					+ "("
					+ " librarianId BIGINT NOT NULL, "
					+ " librarianName VARCHAR(40), "
					+ " librarianSurname VARCHAR(40), "
					+ " librarianPassword VARCHAR(20), "
					+ " PRIMARY KEY(librarianId))"
					);
			createLibrarians.executeUpdate();
			System.out.println("Librarian table created");

			PreparedStatement createBorrowings = con.prepareStatement("CREATE TABLE IF NOT EXISTS borrowings"
					+ "("
					+ " userId BIGINT,"
					+ " bookId INT,"
					+ " borrowDate VARCHAR(30),"
					+ " borrowTime VARCHAR(5),"
					+ " returnDate VARCHAR(30),"
					+ " PRIMARY KEY(borrowDate))"
					);
			createBorrowings.executeUpdate();
			System.out.println("Borrowings table created");

		} catch (Exception e) {
			System.out.println(e);
		}
	}


	//***LIBRARIAN METHODS***//
	public static void insertLibrarian (Librarian librarian) {
		String name = librarian.getName();
		String surname = librarian.getSurname();
		int id = librarian.getId();
		char[] password = librarian.getPassword();

		try {
			Connection con = getConnection("library");
			PreparedStatement insert = con.prepareStatement("INSERT INTO librarians(librarianId, librarianName, librarianSurname, librarianPassword) VALUES ('"+id+"', '"+name+"', '"+surname+"', '"+Arrays.toString(password)+"')");
			insert.executeUpdate();
			System.out.println("Insert complete");

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}


	public static Librarian getLibrarian(int id) {

		try {
			Connection con = getConnection("library");
			PreparedStatement get = con.prepareStatement("SELECT librarianId, librarianName, librarianSurname, librarianPassword FROM librarians");

			ResultSet result = get.executeQuery();

			while(result.next()) {
				if(result.getInt("librarianId") == id) {
					System.out.println("Librarian fetched");
					return new Librarian(result.getString("librarianName"),
							result.getString("librarianSurname"),
							id,
							result.getString("librarianPassword").toCharArray());
				}
			}
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("No such librarian");
		return null;
	}
	
	
	public static boolean isLibrarian(String name, String surname, int id, char[] password) {

		try {
			Connection con = getConnection("library");
			PreparedStatement get = con.prepareStatement("SELECT librarianId, librarianName, librarianSurname, librarianPassword FROM librarians");

			ResultSet result = get.executeQuery();

			while(result.next()) {
				if(result.getInt("librarianId") == id &&
						result.getString("librarianName").equals(name) &&
						result.getString("librarianSurname").equals(surname) &&
						result.getString("librarianPassword").equals(Arrays.toString(password))) {
					System.out.println("Librarian fetched");
					return true;
				}
			}
			System.out.println("No such librarian");
			return false;
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
	}
	
	public static Integer countLibrarians() {
		
		Connection con = getConnection("library");
		
		try {
			PreparedStatement count = con.prepareStatement("SELECT COUNT(*) FROM librarians");
			ResultSet result = count.executeQuery();
			
			int rowCount = 0;
			
			while(result.next()) {
				rowCount = result.getInt("COUNT(*)");
			}
			return rowCount;
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Could not count librarians");
			return null;
		}
	}
	
	public static void deleteLibrarian(int id) {
		
		Connection con = getConnection("library");
		
		try {
			PreparedStatement delete = con.prepareStatement("DELETE FROM librarians WHERE librarianId = '"+id+"'");
			delete.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't delete a librarian");
		}
	}


	//***BOOK METHODS***//
	public static void insertBook (Book book) {
		String name = book.getName();
		String author = book.getAuthor();
		String genre = book.getGenre().toString();
		int edition = book.getEdition();
		int bookId = book.getId();
		boolean status = book.isBorrowed();

		try {
			Connection con = getConnection("library");
			PreparedStatement insert = con.prepareStatement("INSERT INTO Books(bookId, bookName, bookAuthor, bookGenre, bookEdition, bookBorrowed) VALUES('"+bookId+"', '"+name+"', '"+author+"', '"+genre+"', '"+edition+"', '"+status+"')");
			insert.executeUpdate();
			System.out.println("Insert complete");
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}


	public static Book getBook(int id) {

		try {
			Connection con = getConnection("library");
			PreparedStatement get = con.prepareStatement("SELECT bookId, bookName, bookAuthor, bookEdition, bookGenre, bookBorrowed FROM Books");

			ResultSet result = get.executeQuery();

			while(result.next()) {
				if(result.getInt("bookId") == id) {
					return new Book(result.getString("bookName"), result.getString("bookAuthor"), Genre.valueOf(result.getString("bookGenre")), result.getInt("bookEdition"), result.getInt("bookId"), result.getBoolean("bookBorrowed"));
				}
			}
			System.out.println("Book fetched");
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("No such book");
		return null;
	}

	public static void bookStatusChange(int id, boolean changed) {

		try {
			Connection con = getConnection("library");
			PreparedStatement change = con.prepareStatement("UPDATE books SET bookBorrowed = '"+changed+"' WHERE bookId = '"+id+"'");
			change.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public static void updateBook(int id, String title, String author, int edition, Genre genre) {

		try {
			Connection con = getConnection("library");

			Book book = Database.getBook(id);

			if(!book.getName().equals(title)) {
				PreparedStatement updateTitle = con.prepareStatement("UPDATE books SET bookName = '"+title+"' WHERE bookId = '"+id+"'");
				updateTitle.executeUpdate();
			}
			if(!book.getAuthor().equals(author)) {
			 	PreparedStatement updateAuthor = con.prepareStatement("UPDATE books SET bookAuthor = '"+author+"' WHERE bookId = '"+id+"'");
			 	updateAuthor.executeUpdate();
			}
			if(!book.getGenre().equals(genre)) {
				PreparedStatement updateGenre = con.prepareStatement("UPDATE books SET bookGenre = '"+genre+"' WHERE bookId = '"+id+"'");
				updateGenre.executeUpdate();
			}
			if(book.getEdition() != edition) {
				PreparedStatement updateEdition = con.prepareStatement("UPDATE books SET bookEdition = '"+edition+"' WHERE bookId = '"+id+"'");
				updateEdition.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String[][] fetchBooks() {

		try {
			Connection con = getConnection("library");
			PreparedStatement fill = con.prepareStatement("SELECT bookId, bookName, bookAuthor, bookEdition, bookGenre, bookBorrowed FROM books");

			ResultSet result = fill.executeQuery();

			PreparedStatement rowCounter = con.prepareStatement("SELECT COUNT(*) FROM books");
			ResultSet rows = rowCounter.executeQuery();

			int rowCount = 0;

			if(rows.next()) {
				rowCount = rows.getInt("COUNT(*)");
			}

			String id = null;
			String title = null;
			String author = null;
			String edition = null;
			String genre = null;
			String isBorrowed = null;
			String[][] returning = new String[rowCount][6];

			int i = 0;

			while(result.next()) {
				id = result.getString("bookId");
				title = result.getString("bookName");
				author = result.getString("bookAuthor");
				edition = result.getString("bookEdition");
				genre = result.getString("bookGenre");
				isBorrowed = result.getString("bookBorrowed");

				for(int j = 0; j < 6; j++) {
					switch(j) {
					case 0:
						returning[i][j] = id;
						break;
					case 1:
						returning[i][j] = title;
						break;
					case 2:
						returning[i][j] = author;
						break;
					case 3:
						returning[i][j] = edition;
						break;
					case 4:
						returning[i][j] = genre;
						break;
					case 5:
						returning[i][j] = isBorrowed;
						break;
					}
				}
				i++;
			}
			return returning;

		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Books fetched");
		return null;
	}

	//***USER METHODS***//
	public static void insertUser(User user) {
		String name = user.getName();
		String surname = user.getSurname();
		int noOfBooks = user.getNofBooks();
		int userId = user.getId();

		try {
			Connection con = getConnection("library");
			PreparedStatement insert = con.prepareStatement("INSERT INTO Users(userName, userSurname, userNoOfBooks, userId) VALUES('"+name+"', '"+surname+"', '"+noOfBooks+"', '"+userId+"')");
			insert.executeUpdate();
			System.out.println("Insert complete");
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}


	public static User getUser(int id) {

		try {
			Connection con = getConnection("library");
			PreparedStatement get = con.prepareStatement("SELECT userId, userName, userSurname, userNoOfBooks FROM users");

			ResultSet result = get.executeQuery();

			while(result.next()) {
				if(result.getInt("userId") == id) {
					return new User(result.getString("userName"), result.getString("userSurname"), result.getInt("userNoOfBooks"), id);
				}
			}

			System.out.println("Librarian fetched");
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		System.out.println("No such librarian");
		return null;
	}
	
	
	public static String[][] fetchUsers() {
	
	Connection con = getConnection("library");

	try {
		PreparedStatement getData = con.prepareStatement("SELECT userId, userName, userSurname, userNoOfBooks FROM users");

		ResultSet result = getData.executeQuery();

		PreparedStatement rowCounter = con.prepareStatement("SELECT COUNT(*) FROM users");
		ResultSet rows = rowCounter.executeQuery();

		int rowCount = 0;

		if(rows.next()) {
			rowCount = rows.getInt("COUNT(*)");
		}

		String[][] data = new String[rowCount][7];

		Integer id = null;
		String name = null;
		String surname = null;
		Integer booksBorrowed = null;

		int i = 0;

		while(result.next()) {
			id = getUser(result.getInt("userId")).getId();
			name = getUser(result.getInt("userId")).getName();
			surname = getUser(result.getInt("userId")).getSurname();
			booksBorrowed = getUser(result.getInt("userId")).getNofBooks();

			for(int j = 0; j < 7; j++) {
				switch(j) {
				case 0:
					data[i][j] = id.toString();
					break;

				case 1:
					data[i][j] = name;
					break;

				case 2:
					data[i][j] = surname;
					break;

				case 3:
					data[i][j] = booksBorrowed.toString();
					break;
				}
			}
			i++;
		}
		return data;
		
	} catch (SQLException e) {
		System.out.println(e);
		e.printStackTrace();
	}
	System.out.println("An error occured");
	return null;
}

	public static void updateUser(int id, String name, String surname) {
		
		Connection con = getConnection("library");
		
		try {
			
			User user = getUser(id);
			
			if(!user.getName().equals(name)) {
				PreparedStatement updateTitle = con.prepareStatement("UPDATE users SET userName = '"+name+"' WHERE userId = '"+id+"'");
				updateTitle.executeUpdate();
			}
			
			if(!user.getSurname().equals(surname)) {
			 	PreparedStatement updateAuthor = con.prepareStatement("UPDATE users SET userSurname = '"+surname+"' WHERE userId = '"+id+"'");
			 	updateAuthor.executeUpdate();
			}
			} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Could not update the user");
		}
	}
	
	
	//***BORROW METHODS***//
	public static void insertBorrow(Borrowings borrow) {

		int userId = borrow.getUser().getId();
		int bookId = borrow.getBook().getId();
		int borrowTime = borrow.getBorrowTime();
		String borrowDate = borrow.getBorrowDate();
		String returnDate = borrow.getReturnDate();
		int newNoOfBooksSet = borrow.getUser().getNofBooks() + 1;

		try {
			Connection con = getConnection("library");

			PreparedStatement insert = con.prepareStatement("INSERT INTO borrowings(userId, bookId, borrowDate, borrowTime, returnDate) VALUE('"+userId+"', '"+bookId+"', '"+borrowDate+"', '"+borrowTime+"', '"+returnDate+"')");
			insert.executeUpdate();

			PreparedStatement addToUser = con.prepareStatement("UPDATE users SET userNoOfBooks = '"+newNoOfBooksSet+"' WHERE userId = '"+userId+"'");
			addToUser.executeUpdate();

			bookStatusChange(borrow.getBook().getId(), !borrow.getBook().isBorrowed());
			System.out.println("Insert complete");
			System.out.println("User account updated");
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public static Borrowings getBorrow(User user, Book book) {

		try {
			Connection con = getConnection("library");
			PreparedStatement get = con.prepareStatement("SELECT userId, bookId, borrowDate, returnDate, borrowTime FROM borrowings");

			ResultSet result = get.executeQuery();

			while(result.next()) {
				if(result.getInt("userId") == user.getId() &&
						result.getInt("bookId") == book.getId()) {
					return new Borrowings(user, book, result.getInt("borrowTime"));
				}
			}
			System.out.println("Librarian fetched");
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("No such borrow");
		return null;
	}

	public static void returnBorrow(int bookId, int userId) {

		int noOfBooksSet = Database.getUser(userId).getNofBooks() - 1;

		try {
			Connection con = getConnection("library");
			PreparedStatement delete = con.prepareStatement("DELETE FROM borrowings WHERE bookId = '"+bookId+"' AND userId = '"+userId+"'");
			delete.executeUpdate();

			PreparedStatement addToUser = con.prepareStatement("UPDATE users SET userNoOfBooks = '"+noOfBooksSet+"' WHERE userId = '"+userId+"'");
			addToUser.executeUpdate();

			System.out.println("Book returned");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String[][] fetchBorrowings() {

		Connection con = getConnection("library");

		try {
			PreparedStatement getData = con.prepareStatement("SELECT userId, bookId, returnDate FROM borrowings");

			ResultSet result = getData.executeQuery();

			PreparedStatement rowCounter = con.prepareStatement("SELECT COUNT(*) FROM borrowings");
			ResultSet rows = rowCounter.executeQuery();

			int rowCount = 0;

			if(rows.next()) {
				rowCount = rows.getInt("COUNT(*)");
			}

			String[][] data = new String[rowCount][7];

			String uId = null;
			String bEdition = null;

			String uName = null;
			String uSurname = null;
			String bName = null;
			String bAuthor = null;
			String returnDate = null;

			int i = 0;

			while(result.next()) {
				Integer intUId = getUser(result.getInt("userId")).getId();
				uId = intUId.toString();

				uName = getUser(result.getInt("userId")).getName();
				uSurname = getUser(result.getInt("userId")).getSurname();
				bName = getBook(result.getInt("bookId")).getName();
				bAuthor = getBook(result.getInt("bookId")).getAuthor();

				Integer intBEdition = getBook(result.getInt("bookId")).getEdition();
				bEdition = intBEdition.toString();

				returnDate = result.getString("returnDate");

				for(int j = 0; j < 7; j++) {
					switch(j) {
					case 0:
						data[i][j] = returnDate;
						break;

					case 1:
						data[i][j] = uId;
						break;

					case 2:
						data[i][j] = uName;
						break;

					case 3:
						data[i][j] = uSurname;
						break;

					case 4:
						data[i][j] = bName;
						break;

					case 5:
						data[i][j] = bAuthor;
						break;

					case 6:
						data[i][j] = bEdition;
						break;
					}
				}
				i++;
			}
			return data;
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("An error occured");
		return null;
	}
}
