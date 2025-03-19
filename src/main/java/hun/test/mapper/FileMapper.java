package hun.test.mapper;

import org.apache.ibatis.annotations.Mapper;

import hun.test.dto.FileMngDto;

@Mapper
public interface FileMapper {
	void saveFile(FileMngDto fileMngDto);
	
	FileMngDto selectFile(FileMngDto fileMngDto);
	
	int deleteFile(FileMngDto fileMngDto);
}
