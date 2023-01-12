package org.zerock.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomNoOpPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		
		//암호화 알고리즘 등을 사용해 인코딩
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//사용자가 입력한 비밀번호를 인코딩해서 비교
		return rawPassword.toString().equals(encodedPassword);
	}

	
	
}
