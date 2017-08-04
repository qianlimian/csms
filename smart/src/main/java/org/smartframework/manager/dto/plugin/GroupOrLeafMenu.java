package org.smartframework.manager.dto.plugin;

import org.smartframework.manager.entity.Menu;

import java.io.Serializable;
import java.util.List;

public class GroupOrLeafMenu implements Serializable {

    private static final long serialVersionUID = -8440843142503664836L;

    private Integer id;

    private String name;

    private String url;

    private List<LeafMenu> leafMenus;

    public GroupOrLeafMenu() {
    }

    public GroupOrLeafMenu(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.url = menu.getUrl();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<LeafMenu> getLeafMenus() {
        return leafMenus;
    }

    public void setLeafMenus(List<LeafMenu> leafMenus) {
        this.leafMenus = leafMenus;
    }
}
