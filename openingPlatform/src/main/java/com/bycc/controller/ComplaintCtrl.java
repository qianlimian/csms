package com.bycc.controller;

import com.bycc.dto.ComplaintDto;
import com.bycc.dto.ReplyDto;
import com.bycc.enumitem.ReplyType;
import com.bycc.service.complaint.ComplaintService;
import org.apache.poi.ss.usermodel.Workbook;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投诉管理Ctrl
 */
@Controller
@RequestMapping("/complaints")
public class ComplaintCtrl {
	@Autowired
	private ComplaintService service;

	/**
	 * 首页
	 */
	@RequestMapping
	public String index() {
		return "/pages/complaint/index";
	}

	/**
	 * 编辑页面
	 */
	@RequestMapping("edit")
	public String edit() {
		return "/pages/complaint/edit";
	}

	/**
	 * 回复页面
	 */
	@RequestMapping("showReply")
	public ModelAndView showReply(@RequestParam("id") Integer id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/pages/complaint/reply");
		mv.addObject("dto", service.findOne(id));
		mv.addObject("replyType", ReplyType.values());
		return mv;
	}

	/**
	 * 查询投诉记录
	 *
	 * @param qb 分页
	 */
	@RequestMapping("query")
	@ResponseBody
	public Map<String, Object> query(@RequestParam(value = "queryBean", required = false) QueryBean qb) {
		List<ComplaintDto> list = service.query(qb);
		Map<String, Object> map = new HashMap<>();
		map.put("total", qb.getTotal());
		map.put("items", list);
		return map;
	}

	/**
	 * 回复投诉
	 *
	 * @param replyDto 回复内容
	 */
	@RequestMapping(value = "reply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveReply(@RequestBody ReplyDto replyDto) throws BusinessException {
		service.saveReply(replyDto);
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}

	/**
	 * (按页面的过滤条件不分页)导出投诉记录
	 */
	@RequestMapping(value = "export")
	@ResponseBody
	public void export(@RequestParam(value = "queryBean") QueryBean qb, HttpServletResponse response) throws BusinessException {
		try {
			Workbook workbook = service.export(qb);
			response.setContentType("application/vnd.ms-excel");
			// 设置相应头
			response.setCharacterEncoding("utf-8");
			// filename*=utf-8'zh_cn'"+name+".xls" 解决火狐下乱码问题
			response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode("投诉记录", "utf8") + ".xls");
			workbook.write(response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * 保存投诉记录
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@RequestBody ComplaintDto dto, HttpServletRequest request) {
		return service.save(dto, request);
	}
}
