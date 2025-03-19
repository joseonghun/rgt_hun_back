package hun.test.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonDto {
	
	// 현재 페이지
	private Integer page;
	
	// 페이지당 목록 수
	private Integer viewCnt;
	
	// 시작열 번호
	private Integer startRow;
	
	// 전체 조회 건수
	private Integer totalRecord;
	
	// 전체 페이지 수
	private Integer totalPages;
	
	// 총 페이지 번호
	private Integer pageCnt;
	
	// 검색어
	private String searchKeyword;
}
