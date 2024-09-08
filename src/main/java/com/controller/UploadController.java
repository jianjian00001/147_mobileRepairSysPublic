package com.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.util.VeDate;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {

	@RequestMapping("preUpload.action")
	public String preUpload() {
		return "page/saveimage";
	}

	@RequestMapping(value = "/image.action")
	public String upload(@RequestParam(value = "image", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		String path = request.getSession().getServletContext().getRealPath("/") + File.separator + "upfiles/";
		String fileName = file.getOriginalFilename();
		int i = fileName.lastIndexOf(".");
		String name = String.valueOf(VeDate.getStringDatex());
		String type = fileName.substring(i + 1);
		fileName = name + "." + type;
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("imageFileName", fileName);
		return "page/saveimage";
	}

	@RequestMapping("preFiles.action")
	public String preFiles() {
		return "page/savefile";
	}

	@RequestMapping(value = "/files.action")
	public String files(@RequestParam(value = "image", required = false) MultipartFile file, HttpServletRequest request,
			ModelMap model) {
		String path = request.getSession().getServletContext().getRealPath("/") + "upfiles/";
		String fileName = file.getOriginalFilename();
		int i = fileName.lastIndexOf(".");
		String name = String.valueOf(VeDate.getStringDatex());
		String type = fileName.substring(i + 1);
		fileName = name + "." + type;
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("imageFileName", fileName);
		return "page/savefile";
	}
}


