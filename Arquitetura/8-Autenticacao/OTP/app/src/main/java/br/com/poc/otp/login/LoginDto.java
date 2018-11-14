package br.com.poc.otp.login;

public class LoginDto {
	
	private final String account;
	private final String password;

	public LoginDto(String account, String password) {
		this.account = account;
		this.password = password;
	}

}