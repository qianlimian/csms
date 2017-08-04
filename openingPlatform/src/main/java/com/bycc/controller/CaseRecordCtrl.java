package com.bycc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bycc.dto.CaseRecordDto;
import com.bycc.dto.LawDto;
import com.bycc.dto.LawyerDto;
import com.bycc.service.caserecord.CaseRecordService;

/**
 * 案件记录
 */
@Controller
@RequestMapping("/caseRecords")
public class CaseRecordCtrl {

	@Autowired
	CaseRecordService service;

	/**
	 * 首页
	 */
	@RequestMapping
	public String index() {
		return "/pages/caseRecord/index";
	}
	/**
	 * @description 编辑 | 详情
	 * @return
	 * @date 2017年7月11日上午10:43:14
	 */
	@RequestMapping("/edit")
	public String edit() {
		return "/pages/caseRecord/edit";
	}
	/**
	 * @description 导入案件
	 * @return
	 * @date 2017年7月12日上午8:13:34
	 */
	@RequestMapping("/impIndex")
	public String impIndex() {
		return "/pages/caseRecord/impIndex";
	}
	
	/**
	 * 分页查询
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
		List<CaseRecordDto> dtos = service.query(qb);
		Map<String, Object> map = new HashMap<>();
		map.put("total", qb.getTotal());
		map.put("items", dtos);
		return map;
	}

	/**
	 * @description 开放
	 * @param id
	 * @date 2017年7月10日下午1:31:30
	 */
	@RequestMapping(value = "/doOpen", method = RequestMethod.POST)
	@ResponseBody
	public void doOpen(@RequestParam("caseRecordId") String ids) {
		service.doOpen(ids);
	}

	/**
	 * @description 取消开放
	 * @param id
	 * @date 2017年7月10日下午1:43:43
	 */
	@RequestMapping(value = "/doCancel", method = RequestMethod.POST)
	@ResponseBody
	public void doCancel(@RequestParam("caseRecordId") String ids) {
		service.doCancel(ids);
	}

	/**
	 * 按id查询
	 */
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ResponseBody
	public CaseRecordDto findById(@RequestParam("id") Integer id) {
		return service.findById(id);
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/impExcel", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> importExcel(@RequestBody MultipartFile file, HttpServletResponse response){
		Map<String, Object> result = new HashMap<>();
		try {
			int number=service.importExcel(file);	
			result.put("success", Boolean.TRUE);
			result.put("number", number);
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("errorMsg", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	/**--------------------法律法规--------------------**/

	/**
	 * @description 关联法律法规
	 * @return
	 * @date 2017年7月11日上午10:44:00
	 */
	@RequestMapping("/law")
	public String law(Model model, @RequestParam("id") Integer id) {
		CaseRecordDto dto = service.findById(id);
		model.addAttribute("caseRecord", dto);
		return "/pages/caseRecord/partial/law";
	}
	/**
	 * @description 查询已关联
	 * @date 2017年7月11日下午2:42:14
	 */
	@RequestMapping("/queryLaw")
	@ResponseBody
	public Map<String, Object> queryLaw(@RequestParam("caseRecordId") Integer id) {
		List<LawDto> dtos = service.queryLaw(id);
		Map<String, Object> map = new HashMap<>();
		map.put("total", dtos.size());
		map.put("items", dtos);
		return map;
	}
	/**
	 * @description 保存关联法律
	 * @date 2017年7月11日上午10:57:00
	 */
	@RequestMapping("/saveLaw")
	@ResponseBody
	public void saveLaw(@RequestParam("caseRecordId") Integer id,@RequestParam("lawIds") String ids){
		 service.doSaveLaw(id,ids);
	}
	
	/**
	 * @description 解除关联法律
	 * @date 2017年7月11日上午10:57:00
	 */
	@RequestMapping("/cancelLaw")
	@ResponseBody
	public void cancelLaw(@RequestParam("caseRecordId") Integer id,@RequestParam("lawIds") String ids){
		 service.doCancelLaw(id, ids);
	}
	

	/**--------------------律师--------------------**/
	/**
	 * @description  关联律师
	 * @return
	 * @date 2017年7月11日上午10:43:39
	 */
	@RequestMapping("/lawyer")
	public String lawyer(Model model, @RequestParam("id") Integer id) {
		CaseRecordDto dto = service.findById(id);
		model.addAttribute("caseRecord", dto);
		return "/pages/caseRecord/partial/lawyer";
	}
	/**
	 * @description 查询已关联
	 * @date 2017年7月11日下午2:42:14
	 */
	@RequestMapping("/queryLawyer")
	@ResponseBody
	public Map<String, Object> queryLawyer(@RequestParam("caseRecordId") Integer id) {
		List<LawyerDto> dtos = service.queryLawyer(id);
		Map<String, Object> map = new HashMap<>();
		map.put("total", dtos.size());
		map.put("items", dtos);
		return map;
	}
	/**
	 * @description 保存关联律师
	 * @date 2017年7月11日上午10:58:22
	 */
	@RequestMapping("/saveLawyer")
	@ResponseBody
	public void saveLawyer(@RequestParam("caseRecordId") Integer id,@RequestParam("lawyerIds") String ids){
		 service.doSaveLawyer(id,ids);
	}
	
	/**
	 * @description 解除关联律师
	 * @date 2017年7月11日上午10:58:22
	 */
	@RequestMapping("/cancelLawyer")
	@ResponseBody
	public void cancelLawyer(@RequestParam("caseRecordId") Integer id,@RequestParam("lawyerIds") String ids){
		service.doCancelLawyer(id,ids);
	}
	
}
