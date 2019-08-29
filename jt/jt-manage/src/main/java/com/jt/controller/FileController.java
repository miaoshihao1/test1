package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;
@RestController
@Transactional
public class FileController {
	@Autowired
	private FileService fileService;
	/*
	 * springMVC负责流操作
	 * 1.准备文件路径
	 * 2.准备文件名称和后缀
	 * 3.利用IO流进行写盘操作
	 */
	
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		File dirFile=new File("D:/image");
		if (!dirFile.exists()) {
			//如果文件不存在需要新建文件夹、目录
			dirFile.mkdirs();//创建多级目录
		}
		String fileName=fileImage.getOriginalFilename();
		System.out.println(fileName);
		File file =new File("D:/image/"+fileName);
		fileImage.transferTo(file);
		//转发或重定向不经过视图解析器
		return "redirect:/file.jsp";
	}
	@RequestMapping("/pic/upload")  
	public EasyUIImage uploadFile(MultipartFile uploadFile) {
		
		return fileService.uploadFile(uploadFile);
		
	}
}
