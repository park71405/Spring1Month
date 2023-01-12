package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {

	//등록
	public void register(BoardVO board);
	
	//상세보기
	public BoardVO get(Long bno);
	
	//수정
	public boolean modify(BoardVO board);
	
	//삭제
	public boolean delete(Long bno);
	
	//목록
	public List<BoardVO> getList();
	
	//페이징 처리 목록
	public List<BoardVO> getList(Criteria cri);
	
	//전체 데이터 개수
	public int getTotal(Criteria cri);
	
	//첨부파일 목록
	public List<BoardAttachVO> getAttachList(Long bno);
	
}
