package hun.test.controller;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hun.test.common.CommonUtil;
import hun.test.dto.BooksDto;
import hun.test.dto.CommonDto;
import hun.test.dto.FileMngDto;
import hun.test.dto.ResponseDto;
import hun.test.service.ApiService;
import hun.test.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
	private final ApiService apiService;
	private final FileService fileService;
	
	/**
	 * 도서 목록 가져오기 API
	 * @param commonDto
	 * @return
	 */
	@GetMapping("/books")
	public ResponseEntity<ResponseDto<BooksDto>> getBooksList(@ModelAttribute CommonDto commonDto, HttpServletRequest request) {
		ResponseDto<BooksDto> response = new ResponseDto<>();
		List<BooksDto> list = null;
		commonDto.setStartRow(CommonUtil.setStartRow(commonDto));
		commonDto.setTotalRecord(apiService.getBooksCnt(commonDto));

		response.setResponseCode(200);
		
		if(commonDto.getTotalRecord() > 0) {
			response.setPageCnt(CommonUtil.setTotalPage(commonDto));
			response.setTotalRecord(commonDto.getTotalRecord());
			response.setStartRow(commonDto.getStartRow());
			response.setViewCnt(commonDto.getViewCnt());
			response.setTotalPages(CommonUtil.setTotalPage(commonDto));
			
			list = apiService.getBooksList(commonDto, request);
			int i = 0;
			for(BooksDto dto : list) {
				dto.setNo(commonDto.getTotalRecord() - commonDto.getStartRow() - i);
				i++;
			}
			response.setResponseMessage("정상적으로 조회하였습니다.");
			response.setList(list);
		} else {
			response.setResponseMessage("조회된 목록이 없습니다.");
		}
		
		return new ResponseEntity<ResponseDto<BooksDto>>(response, HttpStatus.OK);
	}
	
	/**
	 * 도서 상세정보 가져오기 API
	 * @param id
	 * @return
	 */
	@GetMapping("/books/{id}")
	public ResponseEntity<ResponseDto<BooksDto>> getBooksDetail(@PathVariable("id") int id, HttpServletRequest request) {
		ResponseDto<BooksDto> response = new ResponseDto<>();
		BooksDto booksDto = new BooksDto();
		try {
			booksDto.setId(id);
		} catch (NumberFormatException | NullPointerException e) {
			response.setResponseMessage("관리번호 형식 오류");
			response.setResponseCode(400);
			return new ResponseEntity<ResponseDto<BooksDto>>(response, HttpStatus.BAD_REQUEST);
		}
		
		booksDto = apiService.getBooksDetail(booksDto, request);
		if(booksDto.getId() != null && booksDto.getId() > 0) {
			
			response.setObj(booksDto);
			response.setResponseMessage("정상적으로 조회하였습니다.");
		} else {
			response.setResponseMessage("해당 도서가 존재하지 않습니다.");
		}
		
		return new ResponseEntity<ResponseDto<BooksDto>>(response, HttpStatus.OK);
	}
	
	/**
	 * 도서정보 추가 API
	 * @param booksDto
	 * @param thumbnail
	 * @return
	 */
	@PostMapping("/books")
	public ResponseEntity<ResponseDto<BooksDto>> addBooks(@ModelAttribute BooksDto booksDto, @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {
		ResponseDto<BooksDto> response = new ResponseDto<>();
		response.setResponseCode(500);
		response.setResponseMessage("저장중 오류가 발생했습니다.");

		BooksDto res = apiService.addBooksDto(booksDto, thumbnail);
		if(res.getId() != null && !res.getId().equals("")) {
//			response.setObj(res);
			response.setResponseCode(200);
			response.setResponseMessage("정상적으로 저장되었습니다.");
		} else {
			response.setResponseCode(500);
			response.setResponseMessage("저장중 오류가 발생했습니다.");
		}
		return new ResponseEntity<ResponseDto<BooksDto>>(response, HttpStatus.OK);
	}
	
	/**
	 * 도서정보 수정 API
	 * @param id
	 * @param booksDto
	 * @param thumbnail
	 * @return
	 */
	@PutMapping("/books/{id}")
	public ResponseEntity<ResponseDto<BooksDto>> updateBooks(@PathVariable("id") String id, @ModelAttribute BooksDto booksDto, @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {
		ResponseDto<BooksDto> response = new ResponseDto<>();
		response.setResponseCode(500);
		response.setResponseMessage("저장중 오류가 발생했습니다.");
		BooksDto res = apiService.updateBooksDto(booksDto, thumbnail);

		if(res.getId() != null && !res.getId().equals("")) {
//			response.setObj(res);
			response.setResponseCode(200);
			response.setResponseMessage("정상적으로 저장되었습니다.");
		} else {
			response.setResponseCode(500);
			response.setResponseMessage("저장중 오류가 발생했습니다.");
		}
		return new ResponseEntity<ResponseDto<BooksDto>>(response, HttpStatus.OK);
	}

	/**
	 * 도서정보 삭제 API
	 * @param id
	 * @return
	 */
	@DeleteMapping("/books/{id}")
	public ResponseEntity<ResponseDto<BooksDto>> deleteBooks(@PathVariable("id") String id) {
		ResponseDto<BooksDto> response = new ResponseDto<>();
		response.setResponseCode(500);
		response.setResponseMessage("삭제중 오류가 발생했습니다.");

		int res = apiService.deleteBooks(id);
		if(res > 0) {
//			response.setObj(res);
			response.setResponseCode(200);
			response.setResponseMessage("정상적으로 삭제되었습니다.");
		} else {
			response.setResponseCode(500);
			response.setResponseMessage("삭제중 오류가 발생했습니다.");
		}
		return new ResponseEntity<ResponseDto<BooksDto>>(response, HttpStatus.OK);
	}
	
	/**
	 * 이미지 사진 가져오기 API
	 * @param fileId
	 * @return
	 */
	@GetMapping("/downFiles/{thumbId}")
	public ResponseEntity<Resource> getFileImages(@PathVariable("thumbId") String thumbId, HttpServletResponse response) {
		FileMngDto fileDto = fileService.getFile(thumbId);
		try {
	        // 실제 파일 경로 (내부 저장 파일명)
	        Path filePath = Paths.get(fileDto.getFilePath());
	        Resource resource = new UrlResource(filePath.toUri());
	
	        if (resource.exists()) {

	            // 파일명 인코딩
	            String encodedFileName = URLEncoder.encode(fileDto.getOriginFileNm(), StandardCharsets.UTF_8);
	            // 브라우저 호환성을 위해 공백 처리
	            encodedFileName = encodedFileName.replaceAll("\\+", "%20");
	            // 다운로드 헤더 설정
	            return ResponseEntity.ok()
	                    .contentType(MediaType.parseMediaType(fileDto.getFileType()))
	                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8\"" + encodedFileName + "\"")
	                    .body(resource);
	        } else {
	            throw new RuntimeException("File not found: " + fileDto.getOriginFileNm());
	        }
	    } catch (MalformedURLException ex) {
	        throw new RuntimeException("File not found", ex);
	    }
	}
}
