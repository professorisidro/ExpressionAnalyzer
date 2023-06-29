package br.com.professorisidro.main;

import br.com.professorisidro.lexico.IsiScanner;
import br.com.professorisidro.parser.IsiParser;

public class MainClass {
	public static void main(String[] args) {
		try {
			IsiScanner sc = new IsiScanner("input.isi");
			IsiParser parser = new IsiParser(sc);
			System.out.println("Starting expression analisys...");
			parser.E();
			System.out.println("Expression analyzed - success!!");
		} catch (Exception ex) {
			System.err.println("ERROR - "+ex.getMessage());
		}
	}
}
