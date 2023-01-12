package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	
	private BoardService service;
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return ;
		}
		
		attachList.forEach(attach -> {
			try {
				
				Path file = Paths.get("c:\\KOSA_P\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());
				
				Files.deleteIfExists(file);
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumNail = Paths.get("c:\\KOSA_P\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());
					Files.delete(thumNail);
				}
				
			} catch(Exception e) {
				System.out.println("delete file error" + e.getMessage());
			}
		});
	}
	
	//전체 목록 조회
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		model.addAttribute("list", service.getList(cri));
		
		int total = service.getTotal(cri);
		
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}

	//등록화면
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {
		
	}
	
	//등록 처리
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		
		if (board.getAttachList() != null) {
	        board.getAttachList().forEach(attach -> System.out.println(attach));
	    }
		
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	//RedirectAttributes : 리다이렉트가 발생하기 전에 모든 플래시 속성을 세션에 복사
	//리다이렉션 이후에는 저장된 플래시 속성을 세션에서 모델로 이동

	//조회 & 수정
	@GetMapping("/get")		//@ModelAttribute : 자동으로 Model의 데이터를 지정한 이름으로 담아줌
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		model.addAttribute("board", service.get(bno));
	}
	
	//수정화면
	@PreAuthorize("principal.username == #writer")
	@GetMapping("/modify")		//@ModelAttribute : 자동으로 Model의 데이터를 지정한 이름으로 담아줌
	public void modify(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model, String writer) {
		
		model.addAttribute("board", service.get(bno));
	}

	//수정 처리
	@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "sucess");
		}
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	//삭제 처리
	@PreAuthorize("principal.username == #writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, String writer) {
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		if(service.delete(bno)) {
			deleteFiles(attachList);	//첨부파일 삭제 함수
			rttr.addFlashAttribute("result", "success");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list" + cri.getListLink(); //목록으로 이동
	}
	
	//첨부파일 목록
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}
	
}
