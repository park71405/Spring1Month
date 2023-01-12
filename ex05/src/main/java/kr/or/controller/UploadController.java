package kr.or.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class UploadController {

	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	private boolean checkImageType(File file) {
		try {

			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@GetMapping("/uploadForm")
	public void updateForm() {

	}

	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {

		String uploadFolder = "C:\\upload"; // 추가

		for (MultipartFile multipartFile : uploadFile) {

			System.out.println("-------------------------------------");
			System.out.println("Upload File Name: " + multipartFile.getOriginalFilename());
			System.out.println("Upload File Size: " + multipartFile.getSize());

			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

			try {
				multipartFile.transferTo(saveFile); // 파일 저장
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		System.out.println("upload ajax");
	}

	@PostMapping(value="/uploadAjaxAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)  //수정
	@ResponseBody  // jsp로 전달되는게 아니라 json이나 xml 데이터로 리턴
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {  //수정
		
		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();  //추가
		String uploadFolder = "C:\\upload";
		
		String uploadFolderPath = getFolder();  //추가

		File uploadPath = new File(uploadFolder, uploadFolderPath);  //수정
		System.out.println("upload path: " + uploadPath);
		
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		
		for (MultipartFile multipartFile : uploadFile) {
			
			AttachFileDTO attachDTO = new AttachFileDTO();  //추가
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE 에서 filepath 삭제하고 파일명만 구하기
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			System.out.println("only file name: " + uploadFileName);
			attachDTO.setFileName(uploadFileName);  //추가
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				//추가
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				if (checkImageType(saveFile)) {
					
					attachDTO.setImage(true);  //추가
					
					FileOutputStream thumbnail = new FileOutputStream(
							new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					
					thumbnail.close();
				}
				
				list.add(attachDTO);  //추가
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);  //추가
	}

	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		
		File file = new File("c:\\upload\\" + fileName);
		
		ResponseEntity<byte[]> result = null;
		
		try {
	
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(
			@RequestHeader("User-Agent") String userAgent,  //추가) 디바이스 정보를 알아냄
			String fileName) 
	{
		System.out.println("download file: " + fileName);
		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
		System.out.println("resource: " + resource);
		
		// 추가
		if (resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		
		// 추가) remove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		HttpHeaders headers = new HttpHeaders();
		
		// 수정
		try {
		    String downloadName = null;
		    if (userAgent.contains("Trident")) {
		    	System.out.println("IE browser");
		        downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
		    } else if (userAgent.contains("Edge")) {
		    	System.out.println("Edge browser");
		        downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
		        System.out.println("Edge name: " + downloadName);
		    } else {
		    	System.out.println("Chrome browser");
		        downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
		    }
		    headers.add("Content-Disposition", "attachment; fileName=" + downloadName);
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		System.out.println("deleteFile: " + fileName);
	    File file;
	    try {
	        //일반 파일일 경우, 파일만 삭제
	        file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
	        file.delete();

	        //이미지일 경우, 썸네일을 삭제 후 원본 삭제
	        if (type.equals("image")) {
	            String largeFileName = file.getAbsolutePath().replace("s_", "");
	            System.out.println("largeFileName: " + largeFileName);
	            file = new File(largeFileName);
	            file.delete();
	        }
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	    }
	    
	    return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
}
