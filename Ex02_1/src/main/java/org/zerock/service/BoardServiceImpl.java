package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.Setter;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;

	//등록
	@Transactional
	@Override
	public void register(BoardVO board) {
		mapper.insertSelectKey(board);
		
		//첨부파일이 없을 경우, 중지
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return ;
		}
		
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	//조회
	@Override
	public BoardVO get(Long bno) {
		return mapper.read(bno);
	}

	
	//수정
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		
		attachMapper.deleteAll(board.getBno());
		boolean modifyResult = mapper.update(board) == 1;
		
		if(modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}
		
		return modifyResult; 
	}

	//삭제
	@Transactional
	@Override
	public boolean delete(Long bno) {
		
		attachMapper.deleteAll(bno);
		
		return mapper.delete(bno) == 1; //정상 수행시 1반환
	}

	//목록
	@Override
	public List<BoardVO> getList() {
		
		return mapper.getList();
	}

	//페이징 처리 목록
	@Override
	public List<BoardVO> getList(Criteria cri) {
		
		return mapper.getListWithPaging(cri);
	}

	//전체 데이터 개수
	@Override
	public int getTotal(Criteria cri) {
		
		return mapper.getTotalCount(cri);
	}

	//첨부파일 목록
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		
		return attachMapper.findByBno(bno);
	}

}
