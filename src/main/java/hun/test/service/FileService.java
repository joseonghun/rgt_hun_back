package hun.test.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import hun.test.dto.FileMngDto;
import hun.test.mapper.FileMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

	private final FileMapper fileMapper;
	
	@Value("${upload.dir}")
	private String uploadDir;
	
	public FileMngDto saveUploadFile(MultipartFile file, String table, String column, String pk) {
		FileMngDto fileMngDto = new FileMngDto();
        try {
        	String originFileName = file.getOriginalFilename();
        	String[] tmpArr = originFileName.split("\\.");
        	String ext = tmpArr[tmpArr.length - 1];
        	
            // 파일 저장 처리
            String saveFileName = UUID.randomUUID().toString() + "." + ext;
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            Path filePath = uploadPath.resolve(saveFileName);
            Files.copy(file.getInputStream(), filePath);
            
            fileMngDto.setDelYn("N");
            fileMngDto.setFileSize(file.getSize());
            fileMngDto.setOriginFileNm(originFileName);
            fileMngDto.setFileExt(ext);
            fileMngDto.setFilePath(filePath.toString());
            fileMngDto.setFileType(file.getContentType());
            fileMngDto.setRelTable(table);
            fileMngDto.setRelColumn(column);
            fileMngDto.setRelPk(pk);
            
            fileMapper.saveFile(fileMngDto);

        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
        
		return fileMngDto;
	}

	public FileMngDto getFile(String fileId) {
		FileMngDto fileDto = new FileMngDto();
		fileDto.setFileSeq(Integer.parseInt(fileId));
		return fileMapper.selectFile(fileDto);
	}
	
	public int deleteFile(int fileId) {
		FileMngDto fileDto = new FileMngDto();
		fileDto.setFileSeq(fileId);
		
		fileDto = fileMapper.selectFile(fileDto);
		File file = new File(fileDto.getFilePath());
		
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("파일 삭제 성공: " + fileDto.getOriginFileNm());
            } else {
                System.out.println("파일 삭제 실패!");
            }
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
		
		return fileMapper.deleteFile(fileDto);
	}
}