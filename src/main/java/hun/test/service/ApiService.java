package hun.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import hun.test.dto.BooksDto;
import hun.test.dto.BooksLogDto;
import hun.test.dto.CommonDto;
import hun.test.dto.FileMngDto;
import hun.test.mapper.ApiMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiService {
	private final ApiMapper apiMapper;
	private final FileService fileService;
	
	/**
	 * 도서 총 건수 가져오기
	 * @param dto
	 * @return
	 */
	public int getBooksCnt(CommonDto dto) {
		
		return apiMapper.selectBooksCount(dto);
	}
	
	/**
	 * 도서 목록 가져오기
	 * @param dto
	 * @return
	 */
	public List<BooksDto> getBooksList(CommonDto commonDto, HttpServletRequest request) {
		String thumbPath = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/downFiles/");
		List<BooksDto> result = apiMapper.selectBooksList(commonDto);
		
		for(BooksDto b : result) {
			b.setThumbUrl(thumbPath + b.getThumbUrl());
		}
		return result;
	}
	
	/**
	 * 도서 상세 가져오기
	 * @param booksDto
	 * @return
	 */
	public BooksDto getBooksDetail(BooksDto booksDto, HttpServletRequest request) {
		System.out.println("schema : " + request.getScheme());
		System.out.println("serverName : " + request.getServerName());
		System.out.println("port : " + request.getServerPort());
		String thumbPath = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/downFiles/");
		booksDto.setThumbUrl(thumbPath);
		return apiMapper.selectBooksDetail(booksDto);
	}
	
	/**
	 * 도서 저장
	 * @param booksDto
	 * @param thumbnail
	 * @return
	 */
	public BooksDto addBooksDto(BooksDto booksDto, MultipartFile thumbnail) {
		apiMapper.addBooks(booksDto);

		if(thumbnail != null) {
			FileMngDto fileDto = fileService.saveUploadFile(thumbnail, "tb_books", "thumbnail", String.valueOf(booksDto.getId()));
		}

		// 로그 저장
		BooksLogDto logDto = new BooksLogDto();
		logDto.setId(booksDto.getId());
		logDto.setType("I");
		logDto.setBooksAmount(booksDto.getBooksAmount());
		apiMapper.insertBookCountLog(logDto);
		
		return booksDto;
	}
	
	/**
	 * 도서 정보 업데이트
	 * @param booksDto
	 * @param thumbnail
	 * @return
	 */
	public BooksDto updateBooksDto(BooksDto booksDto, MultipartFile thumbnail) {
		BooksDto oldBooks = apiMapper.selectBooksDetail(booksDto);
		apiMapper.updateBooks(booksDto);

		if(thumbnail != null) {
			// 기존 파일 제거
			Integer thumbId = apiMapper.selectThumbFile(String.valueOf(booksDto.getId()));
			if(thumbId != null && thumbId > 0) {
				fileService.deleteFile(thumbId);
			}
			
			// 신규 파일 저장
			FileMngDto fileDto = fileService.saveUploadFile(thumbnail, "tb_books", "thumbnail", String.valueOf(booksDto.getId()));
		}

		// 로그 저장
		if(oldBooks.getBooksAmount() != booksDto.getBooksAmount()) {
			BooksLogDto logDto = new BooksLogDto();
			logDto.setId(booksDto.getId());
			logDto.setType("M");
			logDto.setBooksAmount(booksDto.getBooksAmount() - oldBooks.getBooksAmount());
			apiMapper.insertBookCountLog(logDto);
		}
		return booksDto;
	}
	
	/**
	 * 도서 삭제
	 * @param booksDto
	 * @return
	 */
	public int deleteBooks(String id) {
		BooksDto booksDto = new BooksDto();
		booksDto.setId(Integer.parseInt(id));
		booksDto.setThumbUrl("");	// 에러 방지용
		BooksDto oldBooks = apiMapper.selectBooksDetail(booksDto);
		
		// 기존 파일 제거
		Integer thumbId = apiMapper.selectThumbFile(id);
		if(thumbId != null && thumbId > 0) {
			fileService.deleteFile(thumbId);
		}
		// 로그 저장
		BooksLogDto logDto = new BooksLogDto();
		logDto.setId(Integer.parseInt(id));
		logDto.setType("D");
		logDto.setBooksAmount(oldBooks.getBooksAmount());
		apiMapper.insertBookCountLog(logDto);
		
		return apiMapper.deleteBooks(booksDto);
	}
	
}
