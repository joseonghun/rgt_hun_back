package hun.test.common;

import hun.test.dto.CommonDto;

public class CommonUtil {

	public static int setStartRow(CommonDto commonDto) {

		return (commonDto.getPage() - 1) * commonDto.getViewCnt();
	}
	
	public static int setTotalPage(CommonDto commonDto) {
		return (int) Math.ceil((double) commonDto.getTotalRecord() / (double) commonDto.getViewCnt());
	}
}
