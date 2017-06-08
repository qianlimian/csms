package org.smartframework.manager.dto.user;

import org.smartframework.manager.entity.UserSetting;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;
import javax.validation.constraints.Size;

public class UserSettingDto {

    /**
     * 页面宽度设置
     */
    private String pageWidth;

	/**
	 * 菜单位置设置
	 */
	private String menuPosition;


	public String getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(String pageWidth) {
		this.pageWidth = pageWidth;
	}

	public String getMenuPosition() {
		return menuPosition;
	}

	public void setMenuPosition(String menuPosition) {
		this.menuPosition = menuPosition;
	}

}
