package org.zerock.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;

import lombok.Setter;

@Component
public class FileCheckTask {
	
	@Setter(onMethod_ = {@Autowired})
	private BoardAttachMapper attachMapper;
	
	//어제 날짜 폴더 경로
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}

	@Scheduled(cron="0 0 2 * * *")	//새벽 2시마다 실행
	public void checkFiles() throws Exception{
		
		//어제 업로드 된 첨부파일 목록 
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		
		//어제 업로드 된  첨부파일 목록으로 파일 경로 구함
		List<Path> fileListPaths = fileList
			      .stream()
			      .map(
			            vo -> Paths.get(
			                  "C:\\KOSA_P\\upload", 
			                  vo.getUploadPath(), 
			                  vo.getUuid() + "_" + vo.getFileName())
			      )
			      .collect(Collectors.toList());

		//썸네일 파일의 경로 목록
		fileList.stream().filter(vo -> vo.isFileType() == true)
				.map(
					vo -> Paths.get(
							"C:\\KOSA_P\\upload",
							vo.getUploadPath(),
							"s_" + vo.getUuid() + "_" + vo.getFileName())
				)
				.forEach(p -> fileListPaths.add(p));
		
		//어제 디렉토리 경로
		File targetDir = Paths.get("C:\\KOSA_P\\upload", getFolderYesterDay()).toFile();
		
		//어제 디렉토리와 비교해서 살제할 파일 경로 구하기
		File[] removeFiles = targetDir.listFiles(
				file -> fileListPaths.contains(file.toPath()) == false);
		
		//하나씩 삭제
		for(File file : removeFiles) {
			file.delete();
		}
		
	}
	
}
