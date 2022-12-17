package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service	//계층 구조상 주로 비즈니스 영역을 담당하는 객체임을 표시하기 위해 사용
@AllArgsConstructor		//모든 파라미터를 이용하는 생성자를 만들음 , BoardMapper를 주입받는 생성자가 만들어짐
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper mapper;

	@Override
	public void register(BoardVO board) {
		log.info("register....." + board);
		
		mapper.insertSelectKey(board);
	}

	@Override
	public List<BoardVO> getList() {
		
		log.info("getList......");
		
		return mapper.getList();
	}
	
	@Override
	public BoardVO get(Long bno) {
		
		log.info("get...." + bno);
		
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		
		log.info("modify....." + board);
		
		return mapper.update(board) == 1;
	}

	@Override
	public boolean remove(Long bno) {
		
		log.info("remove......." + bno);
		
		return mapper.delete(bno) == 1;
	}

}
