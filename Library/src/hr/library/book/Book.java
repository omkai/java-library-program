package hr.library.book;

public class Book {

	private String name;
	private String author;
	private Genre genre;
	private Integer edition;
	private Integer id;
	boolean borrowed;
	
	public Book(String name, String author, Genre genre, Integer edition, Integer id) {
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.edition = edition;
		this.id = id;
		this.borrowed = false;
	}
	
	public Book(String name, String author, Genre genre, Integer edition, Integer id, boolean borrowed) {
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.edition = edition;
		this.id = id;
		this.borrowed = borrowed;
	}
	

	public boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + ", genre=" + genre + ", edition=" + edition + ", id=" + id
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + edition;
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if ((int)edition != (int)other.edition)
			return false;
		if (genre != other.genre)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
