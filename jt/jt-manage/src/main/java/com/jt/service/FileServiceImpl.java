package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.EasyUIImage;
@Service
@Transactional
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
	@Value("${image.localFileDir}")
	private String localFileDir;
	@Value("${image.urlPath}")
	private String urlPath;
	@Override
	public EasyUIImage uploadFile(MultipartFile uploadFile) {
		// TODO Auto-generated method stub
		EasyUIImage uiImage=new EasyUIImage();
		//验证是否为图片(正则)
		String fileName=uploadFile.getOriginalFilename();
		fileName=fileName.toLowerCase();
		System.out.println(localFileDir);
		if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			uiImage.setError(1);
			System.out.println(uiImage);
			return uiImage;
		}
		//获取图片宽度和高度
		try {
			BufferedImage bufferedImage=ImageIO.read(uploadFile.getInputStream());
			int height=bufferedImage.getHeight();
			int width=bufferedImage.getWidth();
			if (height==0||width==0) {
				uiImage.setError(1);
				return uiImage;
			}
			//为了防止同一个文件夹中图片数量过多，将图片分目录存储，按照时间进行存储 yyyy/MM/dd
			String dateDir=new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			uiImage.setHeignt(height).setWidth(width);
			String localdir =localFileDir+dateDir;
			File dirFile=new File(localdir);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			//生成新的图片名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileType=fileName.substring(fileName.lastIndexOf("."));
			String realFileName=uuid+fileType;
			//实现文件上传
			String realPath=localdir+"/"+realFileName;
			File realFilePath=new File(realPath);
			uploadFile.transferTo(realFilePath);
			System.out.println("文件上传成功");
			String url=urlPath+dateDir+"/"+realFileName;
			uiImage.setUrl(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			uiImage.setError(1);
			return uiImage;
		}
		//防止恶意程序
		//
		
		return uiImage;
	}

}
