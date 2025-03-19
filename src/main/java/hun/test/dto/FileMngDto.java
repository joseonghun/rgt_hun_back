package hun.test.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileMngDto {
	
	private Integer fileSeq;
	
	private String originFileNm;
	
	private String filePath;
	
	private String fileExt;
	
	private long fileSize;
	
	private String fileType;
	
	private String delYn;
	
	private String relTable;
	
	private String relColumn;
	
	private String relPk;
}
