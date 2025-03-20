package hun.test.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BooksDto {
	
	// 화면 표시 건수 번호
	private Integer no;
	
	// 도서 관리번호
	private Integer id;
	
	// 도서 제목
	private String title;

	// 도서 저자
	private String author;
	
	// 출판사
	private String publisher;
	
	// 출판일
	private String publishDt;

	// 도서 상세
	private String bookDetails;
	
	// 도서 ISBN 정보
	private String isbn;
	
	// 썸네일 경로
	private Integer thumbSeq;

	// 현재 수량
	private Integer booksAmount;
	
	// 썸네일 업로드용 파일객체
	private MultipartFile thumbnail;
	
	// 도서 판매 수량
	private Integer sellAmount;
	
}
