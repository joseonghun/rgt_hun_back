package hun.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import hun.test.dto.BooksDto;
import hun.test.dto.CommonDto;

@Mapper
public interface ApiMapper {
	int selectBooksCount(CommonDto commonDto);
	
	List<BooksDto> selectBooksList(CommonDto booksDto);
	
	BooksDto selectBooksDetail(BooksDto booksDto);
	
	int addBooks(BooksDto booksDto);
	
	int updateBooks(BooksDto booksDto);
	
	int deleteBooks(BooksDto booksDto);
	
	Integer selectThumbFile(String arg0);
}
