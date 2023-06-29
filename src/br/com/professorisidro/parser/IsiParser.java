package br.com.professorisidro.parser;

import br.com.professorisidro.common.Token;
import br.com.professorisidro.common.TokenType;
import br.com.professorisidro.lexico.IsiScanner;

public class IsiParser {
	private IsiScanner scanner;
	private Token token;

	public IsiParser(IsiScanner scanner) {
		this.scanner = scanner;
	}

	public void E() {
		T();
		El();
	}

	public void T() {
		token = scanner.nextToken();
		if (token.getType() != TokenType.IDENTIFIER && token.getType() != TokenType.NUMBER) {
			throw new IsiParserException("Id or Number expected, found " + token.getType() + " at Line "
					+ scanner.getLine() + ", column " + scanner.getColumn());
		}
	}

	public void El() {
		token = scanner.nextToken();
		if (token != null) {
			OP();
			T();
			El();
		}
	}

	public void OP() {
		if (token.getType() != TokenType.OPERATOR) {
			throw new IsiParserException("Operator Expected, found " + token.getType() + " at Line " + scanner.getLine()
					+ ", column " + scanner.getColumn());
		}
	}

}
