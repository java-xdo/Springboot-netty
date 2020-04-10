package com.qs.udp.water.check.tool;

public class text {
	public static void main(String[] args) {
		String str = "68000869975032169826000000000101000000000000000000000000000000000000000000000034011f6c0220200409095824046004083710902517364131389d0000000000000000009d0000000003020018661809c400000000006116";
		System.out.println(getFileAddSpace(str));
	}

	public static String getFileAddSpace(String replace) {
		String regex = "(.{2})";
		replace = replace.replaceAll(regex, "$1 ");
		return replace;
	}

}
