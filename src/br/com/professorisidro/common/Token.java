package br.com.professorisidro.common;

public class Token {
	private TokenType type;
	private String    text;
	public Token(TokenType type, String text) {
		super();
		this.type = type;
		this.text = text;
	}
	public Token() {
		super();
	}
	public TokenType getType() {
		return type;
	}
	public void setType(TokenType type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Token [type=" + type + ", text=" + text + "]";
	}
	
}
