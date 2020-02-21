package com.choimroc.mybatisplusdemo.tool.menu;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author choimroc
 * @since 2019/4/23
 */
@Getter
@Setter
public class MenuTree {
    private int id;
    private int parentId;
    private int name;
    private int ordinal;
    private int level;
    private List<MenuTree> list;

    public MenuTree(int id, int parentId, int level, List<MenuTree> list) {
        this.id = id;
        this.parentId = parentId;
        this.level = level;
        this.list = list;

    }
}
