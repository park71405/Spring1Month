package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		
		model.addAttribute("list", service.getList());
	}
	
	//게시품 등록
	@GetMapping("/register") 
	public void register() {
		
	}
	//게시물 등록
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register: " + board);
		
		service.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	//페이지 조회
	@GetMapping("/get")
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("/get or modify");
		
		model.addAttribute("board", service.get(bno));
	}
	
	@GetMapping("/modify")
	public void modify(@RequestParam("bno") Long bno, Model model) {
	log.info("/get or modify");
		
		model.addAttribute("board", service.get(bno));
	}
	
	//게시글 수정
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify:" + board);
	
		if (service.modify(board)) {
				rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("remove: " + bno);
		
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list";
	}
	
}
