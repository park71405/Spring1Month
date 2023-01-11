package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyService {

	//등록
	public int registre(ReplyVO vo);
	
	//상세보기
	public ReplyVO get(Long rno);
	
	//수정
	public int modify(ReplyVO vo);
	
	//삭제
	public int remove(Long rno);
	
	//목록 + 페이징 처리
	public List<ReplyVO> getList(Criteria cri, Long bno);
	
}
