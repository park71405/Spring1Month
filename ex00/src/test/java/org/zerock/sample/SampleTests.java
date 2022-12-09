package org.zerock.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) //테스트 코드가 스프링을 실행하는 역할을 할 것임을 명시
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") //지정된 클래스나 문자열을 이용해서 필요한 객체들을 스프링 내 객체로 등록
@Log4j //Lombok을 이용해 로그 기록하는 Logger 변수 생성
public class SampleTests {
	
	// @Autowired : 해당 인스턴스 변수가 스프링으로부터 자동 주입해 달라는 표시
	//		-> obj 변수에 Restaurant 타입의 객체 주입
	
	@Setter(onMethod_ = { @Autowired }) 
	private Restaurant restaurant;
	
	@Test	//JUnit에서 테스트 대상을 표시
	public void testExist() {
		
		assertNotNull(restaurant);	//restaurant 변수가 null이 아니어야만 테스트 성공함을 의미
		
		log.info(restaurant);
		log.info("--------------------------");
		log.info(restaurant.getChef());
		
	}
	
}
