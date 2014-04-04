package com.java7developer.chapter2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathExample {
	public static void main(String[] args) throws IOException {
		Path prefixpath = Paths.get("src/main/java/");// 获取前置路径
		Path path = prefixpath.resolve("com/java7developer/chapter2/PathExample.java");
		System.out.println(path.getNameCount());// 显示指定Path的别名数量
		System.out.println(path.getRoot());// 显示指定Path的根目录
		System.out.println(path.toAbsolutePath());// 显示指定Path的真实路径
		System.out.println(path.toRealPath());// 显示指定Path的真实路径(支持符号链接)
		System.out.println(path.getFileName());// 获得文件名称
		System.out.println(path.toFile());// 转换为File对象
		System.out.println(path.getParent());// 获取父路径
	}
}
