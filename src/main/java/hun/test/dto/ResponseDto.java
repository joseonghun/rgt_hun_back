package hun.test.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto<E> extends CommonDto {
	
	private Integer responseCode;
	
	private String responseMessage;
	
	private List<E> list;
	
	private Object obj;
}
