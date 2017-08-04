package com.bycc.controller;

import com.bycc.dto.CaseRecordDto;
import com.bycc.dto.LawyerDto;
import com.bycc.service.index.IndexService;
import com.bycc.service.law.LawService;

import com.bycc.service.lawyer.LawyerService;
import org.smartframework.common.kendo.Filter;
import org.smartframework.common.kendo.LogicFilter;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexCtrl {
	@Autowired
	private IndexService indexService;
	@Autowired
	private LawService lawService;
	@Autowired
	private LawyerService lawyerService;

	/**
	 * @description 前台首页
	 */
	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "/pages/front/index";
	}

	/**
	 * 查询
	 *
	 * @param code
	 *            验证码
	 * @param idNumber
	 *            身份证号
	 * @param name
	 *            嫌疑人姓名
	 */
	@RequestMapping("/display")
	public ModelAndView display(String code, String idNumber, String name) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/pages/front/display");
		try {
			CaseRecordDto dto = indexService.display(code, idNumber, name);
			mv.addObject("caseRecord", dto);
		} catch (BusinessException e) {
			e.printStackTrace();
			mv.addObject("error", e.getMessage());
			mv.addObject("caseRecord", new CaseRecordDto());
		}
		return mv;
	}

	@RequestMapping("/law")
	public String law(Model model,
			@RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize,
			@RequestParam(value = "filter", defaultValue = "", required = false) String filterContent) {
		QueryBean qb = new QueryBean(currentPage, pageSize);
		
		if(filterContent!=null&&!filterContent.equals("")){
			//过滤
			LogicFilter logicFilter=new LogicFilter();
			List<Filter> filters=new ArrayList<>();
			Filter filter=new Filter();
			filter.setField("title");
			filter.setOperator("contains");
			filter.setType("string");
			filter.setValue(filterContent);
			filters.add(filter);	
			
			logicFilter.setFilters(filters);
			qb.setFilter(logicFilter);
		}
		
		
		//返回
		model.addAttribute("laws", lawService.query(qb));
		model.addAttribute("filter", filterContent);
		model.addAttribute("totals", qb.getTotal());
		model.addAttribute("currentPage", qb.getPage());
		return "/pages/front/law";
	}

	@RequestMapping("/lawContent")
	public String lawContent(Model model,@RequestParam("id") Integer id) {		
		model.addAttribute("law", lawService.findById(id));
		return "/pages/front/lawContent";
	}

	@RequestMapping("/evaluate")
	public String evaluate() {
		return "/pages/front/evaluate";
	}

	/**
	 * 发送手机验证码
	 *
	 * @param tel
	 *            手机号
	 * @return 发送状态
	 */
	@RequestMapping(value = "/sendCode")
	@ResponseBody
	public Map<String, Object> sendCode(@RequestParam("tel") String tel) {
		Map<String, Object> result = new HashMap<>();
		try {
			// 发送验证码
			indexService.sendCode(tel);
			// 设置结果
			result.put("success", Boolean.TRUE);
		} catch (BusinessException e) {
			e.printStackTrace();
			result.put("success", Boolean.FALSE);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@RequestMapping("/lawyer")
	public String lawyer(){
		return "/pages/front/lawyer";
	}
	@RequestMapping("/lawyersFind")
	@ResponseBody
	public Map<String,Object> lawyersFind(@RequestParam("offset") Integer offset,@RequestParam("limit") Integer limit,@RequestParam("lawyerName") String name,@RequestParam("lawyerOffice") String lawyerOffice){
		QueryBean queryBean = new QueryBean(offset/limit+1,limit);
		LogicFilter logicFilter = new LogicFilter();
		List<Filter> filterList = new ArrayList<>();
		if (name!=null && !name.equals("")){
			Filter filterName = new Filter();
			filterName.setField("name");
			filterName.setOperator("contains");
			filterName.setType("string");
			filterName.setValue(name);
			filterList.add(filterName);
		}
		if (lawyerOffice!=null && !lawyerOffice.equals("")){
			Filter filterOffice = new Filter();
			filterOffice.setField("lawyerOffice");
			filterOffice.setOperator("contains");
			filterOffice.setType("string");
			filterOffice.setValue(lawyerOffice);
			filterList.add(filterOffice);
		}
		if (filterList.size()>0){
			logicFilter.setFilters(filterList);
			queryBean.setFilter(logicFilter);
		}
		List<LawyerDto> lawyerDtoList = lawyerService.findAllPage(queryBean);
//		List<LawyerDto> lawyerDtoList = lawyerService.findAll();
		Map<String,Object> map = new HashMap<>();
		map.put("rows",lawyerDtoList);
		map.put("total", queryBean.getTotal());
		return map;
	}
}
