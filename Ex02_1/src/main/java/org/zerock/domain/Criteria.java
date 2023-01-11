package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum; // 페이지 번호
	private int amount; // 페이지 당 데이터 개수
	private String type; // 검색 조건
	private String keyword; // 키워드

	public Criteria() {
		this(1, 10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public String[] getTypeArr() { //검색 조건은 한글자, 배열로 만들어 한번에 처리
		return type == null ? new String[] {} : type.split("");
	}
	
	public String getListLink() {	//여러개의 파라미터를 연결해 URL 형태로 반환, 한글처리 신경 x
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")		//fromPath("경로") 경로를 적으면 뒤로 파라미터가 붙음, 아무것도 안적으면 ?부터 붙음
												.queryParam("pageNum", this.pageNum)
												.queryParam("amount", this.getAmount())
												.queryParam("type", this.getType())
												.queryParam("keyword", this.getKeyword());
		return builder.toUriString();
	}

}