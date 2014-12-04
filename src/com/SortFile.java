package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortFile {

	public enum SortType {
		WORDASC, // 升序
		WORDDSC, // 降序
		WORDCOUNT;// 按出现次数排序
	}

	/**
	 * srcpathname dstpathname Type 排序方式
	 * 
	 * @return
	 */
	public boolean sortWords(String srcPathName, String dstPathName, SortType type) {
		SortFile sf = new SortFile();
		String ss = sf.readFile(srcPathName);
		String[] tempStrs = ss.split(" ");
		Arrays.sort(tempStrs, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tempStrs.length; i++) {
			if (SortType.WORDASC.equals(type)) {
				sb.append(tempStrs[i] + "\r");
			} else if (SortType.WORDDSC.equals(type)) {
				sb.append(tempStrs[tempStrs.length - 1 - i] + "\r");
			} else {
				break;
			}
		}

		if (SortType.WORDCOUNT.equals(type)) {
			sb.append(doList(tempStrs));
		}

		writeFile(dstPathName, sb.toString());
		return false;
	}

	private void writeFile(String path, String inner) {
		File file = new File(path);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			try {
				fos.write(inner.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private String readFile(String fileNames) {
		File file = new File(fileNames);
		StringBuilder sb = new StringBuilder();
		try {
			FileInputStream fs = new FileInputStream(file);
			byte[] b = new byte[1024];
			try {
				while (fs.read(b) > -1) {
					String str = new String(b);
					sb.append(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return replaceBlank(sb.toString());
	}

	private String doList(String[] str) {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> tempList = new ArrayList<String>();
		list.add(tempList);
		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).contains(str[i])) {
					list.get(j).add(str[i]);
				} else {
					List<String> t = new ArrayList<String>();
					t.add(str[i]);
					list.add(t);
				}
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			List<String> tt = new ArrayList<String>();
			for (int j = i + 1; j < list.size(); j++) {
				int k =list.get(i).size();
				if (list.get(i).size() < list.get(i).size()) {
					tt.addAll(list.get(i));
					
				}
			}
		}
		return null;

	}

	private String replaceBlank(String str) {
		return str.replaceAll("\n", " ").replaceAll("\t", " ").replaceAll("\r", " ").replaceAll("  ", " ")
				.replaceAll("[\\pP‘’“”]", "").trim();
	}

	public static void main(String[] args) {
		SortFile sf = new SortFile();
		sf.sortWords("str.txt", "str1.txt", SortType.WORDCOUNT);
	}
}