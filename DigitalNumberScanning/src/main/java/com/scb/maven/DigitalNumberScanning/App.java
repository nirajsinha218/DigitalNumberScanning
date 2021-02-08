package com.scb.maven.DigitalNumberScanning;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
	static Map<String, String> symbolMatcher = new HashMap<String, String>();
	static int lineCount = 1;
	private final static String ZERO = "0";
	private final static String ONE = "1";
	private final static String TWO = "2";
	private final static String ILLEGAL = "?";
	private final static char SPACE = ' ';
	private final static char UNDERSCORE = '_';
	private final static char PIPE = '|';

	public static List<String> fileParser(String path) {
		symbolMatcher.put("020101121", "0");
		symbolMatcher.put("000001001", "1");
		symbolMatcher.put("020021120", "2");
		symbolMatcher.put("020021021", "3");
		symbolMatcher.put("000121001", "4");
		symbolMatcher.put("020120021", "5");
		symbolMatcher.put("020120121", "6");
		symbolMatcher.put("020001001", "7");
		symbolMatcher.put("020121121", "8");
		symbolMatcher.put("020121021", "9");
		List<String> resultList = new ArrayList<String>();
		try (Stream<String> lines = (Files.newBufferedReader(Paths.get(path)).lines())) {
			Map<Integer, String> symbolReader = new HashMap<Integer, String>();
			lines.forEach((n) -> {
				String line = (String) n;
				if (!line.contains("_") && !line.contains("|")) {
					lineCount = 1;
				} else {
					char[] chArr = line.toCharArray();
					int charCount = 0;
					for (char ch : chArr) {
						if (ch == SPACE) {
							if (symbolReader.containsKey(charCount / 3)) {
								symbolReader.put(charCount / 3, symbolReader.get(charCount / 3) + ZERO);
							} else {
								symbolReader.put(charCount / 3, ZERO);
							}
						} else if (ch == UNDERSCORE) {
							if (symbolReader.containsKey(charCount / 3)) {
								symbolReader.put(charCount / 3, symbolReader.get(charCount / 3) + TWO);
							} else {
								symbolReader.put(charCount / 3, TWO);
							}
						} else if (ch == PIPE) {
							if (symbolReader.containsKey(charCount / 3)) {
								symbolReader.put(charCount / 3, symbolReader.get(charCount / 3) + ONE);
							} else {
								symbolReader.put(charCount / 3, ONE);
							}
						} else {
							if (symbolReader.containsKey(charCount / 3)) {
								symbolReader.put(charCount / 3, symbolReader.get(charCount / 3) + ILLEGAL);
							} else {
								symbolReader.put(charCount / 3, ILLEGAL);
							}
						}
						charCount++;
					}

					if (lineCount == 3) {
						StringBuilder sb = new StringBuilder();
						boolean isInvalid = false;
						for (Map.Entry<Integer, String> entry : symbolReader.entrySet()) {
							if (symbolMatcher.get(entry.getValue()) != null) {
								sb.append(symbolMatcher.get(entry.getValue()));
							} else {
								sb.append("?");
								isInvalid = true;
							}
						}
						if (isInvalid) {
							sb.append("ILL");
						}
						resultList.add(sb.toString());
						symbolReader.clear();
					}

					lineCount++;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public static void main(String[] args) {

		System.out.println("Enter file Path");
		Scanner in = new Scanner(System.in);
		String path = in.nextLine();
		List<String> resultList = fileParser(path);
		for (String result : resultList) {
			System.out.println(result);
		}
	}

}
