package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper mapper;

	//등록
	@Override
	public void register(BoardVO board) {
		mapper.insertSelectKey(board);
	}

	//조회
	@Override
	public BoardVO get(Long bno) {
		return mapper.read(bno);
	}

	//수정
	@Override
	public boolean modify(BoardVO board) {
		return mapper.update(board) == 1; //정상 수행시 1반환
	}

	//삭제
	@Override
	public boolean delete(Long bno) {
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

}
