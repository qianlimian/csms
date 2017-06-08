package org.smartframework.platform.dictionary.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.smartframework.platform.dictionary.bean.DictionaryBean;
import org.smartframework.platform.dictionary.exception.EntryException;
import org.smartframework.platform.dictionary.model.DictionaryModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("dictionary")
public class DictionaryCtrl {
	
	@Autowired
	private DictionaryModel dictionaryModel;

	@RequestMapping("/entry")
	@ResponseBody
	public List<DictionaryBean> getDictionaryById(@RequestParam("dicId") String dicId) throws Exception {
		try {
			if ((dicId == null) || (dicId.length() == 0)) {
				return new ArrayList<DictionaryBean>();
			}
			return this.dictionaryModel.createEntry(dicId);
		} catch (EntryException e) {
			e.printStackTrace();
			throw new Exception("字典：" + dicId + "获取失败，请联系该页面开发人员。");
		}
	}
	
	public void setDictionaryModel(DictionaryModel dictionaryModel) {
		this.dictionaryModel = dictionaryModel;
	}
}
