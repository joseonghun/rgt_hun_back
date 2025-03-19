package hun.test.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BooksLogDto {
	
	private Integer logSeq;
	
	private Integer id;
	
	private String type;
	
	private Integer booksAmount;
	
	private String logDt;
}
