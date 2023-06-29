package br.com.professorisidro.lexico;

import java.nio.file.Files;
import java.nio.file.Paths;

import br.com.professorisidro.common.Token;
import br.com.professorisidro.common.TokenType;

public class IsiScanner {

	private char[] content;
	private int    estado;
	private int    pos;
	private int    line;
	private int    column;
	
	public IsiScanner(String filename) {
		try {
			String txtFileContent;
			txtFileContent = new String(Files.readAllBytes(Paths.get(filename)));
			this.content = txtFileContent.toCharArray();
			this.estado = 0;
			this.pos = 0;
			this.line = 1;
			this.column = 0;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Token nextToken() {
		char currentChar;
		Token token;
		String text="";
		if (isEOF()) {
			return null;
		}
		
		estado = 0;
		while (true) {
			currentChar = nextChar();
			switch(estado) {
			case 0:
				if (isBlank(currentChar)) {
					updateCursor(currentChar);
					estado = 0;
				}
				else if (isChar(currentChar)) {
					text += currentChar;
					estado = 1;
				}
				else if (isDigit(currentChar)) {
					text += currentChar;
					estado = 3;
				}
				else if (isOperator(currentChar)) {
					text += currentChar;
					token = new Token(TokenType.OPERATOR, text);
					column += text.length();
					return token;
				}
				else {
					throw new IsiLexException("Simbolo nao reconhecido");
				}
				break;
			case 1:
				if (isDigit(currentChar) || isChar(currentChar)) {
					text += currentChar;
					estado = 1;
				}
				else if (isBlank(currentChar) || isOperator(currentChar) || isEOF(currentChar)) {
					if (isBlank(currentChar)) {
						updateCursor(currentChar);
					}					
					token = new Token(TokenType.IDENTIFIER, text);
					column += text.length();
					if (!isEOF(currentChar)) {
						backTrack();
					}
					return token;
				}	
				break;
			case 3:
				if (isDigit(currentChar)) {
					text += currentChar;
				}
				else if (isBlank(currentChar) || isOperator(currentChar) ||isEOF(currentChar)) {
					if (isBlank(currentChar)) {
						updateCursor(currentChar);
					}
					if (!isEOF(currentChar)) {
						backTrack();						
					}
					token = new Token(TokenType.NUMBER, text);
					column+=text.length();
					return token;					
				}
				else {
					throw new IsiLexException("Numero mal formado");
				}
				break;
			}
		}

	}
	
	// algumas funcoes utilitarias
	private boolean isChar(char c) {
		return c >= 'a' && c <= 'z' || c>= 'A' && c <= 'Z';
	}
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' ||
			   c == '>' || c == '<' || c == '=';
	}
	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	private boolean isEOF() {
		return pos >= content.length;
	}
	private boolean isEOF(char c) {
		return c == '\0';
	}
	private boolean isBlank(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}
	private void backTrack() {
		pos--;
	}
	private char nextChar() {
		if (isEOF()) {
			return '\0';
		}
		return content[pos++];
	}
	
	private void updateCursor(char currentChar) {
		if (currentChar == ' ') {
			column++;
		}
		else if (currentChar == '\n') {
			line++;
			column  = 0;
		}
		else if (currentChar == '\t') {
			column += 4;
		}
	}
	
	public int getLine() {
		return this.line;
	}
	
	public int getColumn(){
		return this.column;
	}
}
