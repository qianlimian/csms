package com.bycc.controller;

import com.bycc.dto.LawDto;
import com.bycc.service.law.LawService;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 法律法规
 */
@Controller
@RequestMapping("/laws")
public class LawCtrl {

	@Autowired
	LawService service;

	/**
	 * 首页
	 */
	@RequestMapping
	public String index() {
		return "/pages/law/index";
	}

	@RequestMapping("/impIndex")
	public String impIndex() {
		return "/pages/law/impIndex";
	}

	/**
	 * 分页查询
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
		List<LawDto> dtos = service.query(qb);
		Map<String, Object> map = new HashMap<>();
		map.put("total", qb.getTotal());
		map.put("items", dtos);
		return map;
	}

	/**
	 * 新增|编辑页
	 */
	@RequestMapping("/edit")
	public String edit() {
		return "/pages/law/edit";
	}

	/**
	 * 按id查找
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public LawDto findById(@RequestParam("id") Integer id) {
		//查询用户信息
		return service.findById(id);
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public LawDto save(@RequestBody LawDto dto) throws Exception {
		return service.save(dto);
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestParam("ids") String ids) {
		service.delete(ids);
	}


	@RequestMapping(value = "/impExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importExcel(@RequestBody MultipartFile file, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		try {
			int number = service.importExcel(file);
			result.put("success", Boolean.TRUE);
			result.put("number", number);
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("errorMsg", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("export")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		File file = new File(Paths.get(request.getRealPath("/") + "/views/pages/law/templates/law.xls").toUri());
		InputStream in = new FileInputStream(file.getPath());
		response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode("法律法规模版", "utf8") + ".xls");
		int len = 0;
		byte[] buffer = new byte[1024];
		OutputStream out = response.getOutputStream();
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
	}
}
