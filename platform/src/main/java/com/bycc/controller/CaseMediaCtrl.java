package com.bycc.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.dialect.HANAColumnStoreDialect;
import org.smartframework.common.kendo.Filter;
import org.smartframework.common.kendo.LogicFilter;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.common.kendo.Sort;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bycc.dto.caseMedia.CaseMediaDto;
import com.bycc.dto.caseMedia.MediaTreeListDto;
import com.bycc.service.bdmVideoCg.BdmVideoCgService;
import com.bycc.service.caseMedia.CaseMediaService;

/**
 * 案件视频
 */
@Controller
@RequestMapping("/caseMedias")
public class CaseMediaCtrl {

	@Autowired
	CaseMediaService service;
	@Autowired
	CaseRecordCtrl caseRecordSer;
	@Autowired
	BdmVideoCgService videoCgSer;
	/**
	 * 首页
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public String index(Model model,@RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize, 
			@RequestParam(value = "category", defaultValue = "", required = false) String categoryContent,
			@RequestParam(value = "filter", defaultValue = "", required = false) String filterContent) throws UnsupportedEncodingException {			
		
		QueryBean qb=new QueryBean(currentPage, pageSize);
		//过滤
		LogicFilter logicFilter=new LogicFilter();
		List<Filter> filters=new ArrayList<>();
		Filter filter=new Filter();
		filter.setField("title");
		filter.setOperator("contains");
		filter.setType("string");
		filter.setValue(filterContent);
		filters.add(filter);			
		Filter category=new Filter();
		category.setField("category.name");
		category.setOperator("contains");
		category.setType("string");
		category.setValue(categoryContent);
		filters.add(category);
		logicFilter.setFilters(filters);
		qb.setFilter(logicFilter);
		//排序
		List<Sort> sorts=new ArrayList<>();
		Sort sort=new Sort();
		sort.setDir("desc");
		sort.setField("updateDate");
		sorts.add(sort);
		qb.setSort(sorts);		
		//返回
		model.addAttribute("medias", service.query(qb));
		model.addAttribute("cgMenu", videoCgSer.findAll());
		model.addAttribute("filter", filterContent);
		model.addAttribute("category", categoryContent);
		model.addAttribute("queryBean", qb);
		return "/pages/caseMedia/index";
	}	
	
	/**
     *  上传视频页面
     */
    @RequestMapping("/load")
    public String score(@RequestParam("caseRecordId") Integer caseRecordId, Model model) {
        model.addAttribute("caseRecord", caseRecordSer.findById(caseRecordId));
        return "/pages/caseMedia/upload";
    }

	/**
	 * @description 派出所视频列表
	 * @return
	 * @date 2017年5月5日上午10:02:28
	 */
	@RequestMapping("/getLocalMediaList")
	@ResponseBody
	public List<MediaTreeListDto> getLocalMediaList(@RequestParam(value="mappingPath",required=false) String path,@RequestParam("caseRecordId") Integer caseRecordId) {
		return service.getLocalMediaList(caseRecordId,path);
	}		
	/**
	 * @description 市局视频列表
	 * @return
	 * @date 2017年5月5日上午10:02:28
	 */
	@RequestMapping("/getRemoteMediaList")
	@ResponseBody
	public List<MediaTreeListDto> getRemoteMediaList(@RequestParam(value="mappingPath",required=false) String path,@RequestParam("caseRecordId") Integer caseRecordId) {
		return service.getRemoteMediaList(caseRecordId,path);
	}
	/**
	 * @description NFS上传
	 * @date 2017年5月5日下午2:13:53
	 */
	@RequestMapping("/copyFiles")
	@ResponseBody
	public String upload(@RequestBody List<MediaTreeListDto> selectedMedias) {
		service.copyFilesNFS(selectedMedias);
		return null;
	}

	/**
	 * @description HTTP上传
	 * @date 2017年5月5日下午2:13:53
	 */
	@RequestMapping("/doUpload")
	@ResponseBody
	public void upload(HttpServletRequest request, PrintWriter writer, HttpServletResponse response,@RequestParam("id") Integer caseRecordId
			,@RequestParam("category") String category)throws Exception {		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;		
		// 获取传入文件
		multipartRequest.setCharacterEncoding("utf-8");
		MultipartFile file = multipartRequest.getFile("myFile");
		String range=service.saveFiles(file,request.getHeader("Content-Range"),caseRecordId,category.trim());
		
		// 设置返回值
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", file.getOriginalFilename());
		if(range==null){
			response.setStatus(403);
		}else{
			response.setStatus(200);
		}		
		response.setHeader("Range", range);
		writer.write(JsonHelper.getJson(map));
	}
			
}
