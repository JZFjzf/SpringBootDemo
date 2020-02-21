package com.choimroc.mybatisplusdemo.tool.menu;


import com.choimroc.mybatisplusdemo.tool.ValidatorUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author choimroc
 * @since 2019/4/23
 */
public class MenuUtils {

    /**
     * 所有菜单
     */
    public static List<MenuTree> parse(List<MenuTree> menuTrees) {
        if (menuTrees == null || menuTrees.isEmpty()) {
            return new ArrayList<>();
        }
        List<List<MenuTree>> lists = new ArrayList<>();
        for (int i = 0; i < getMaxLevel(menuTrees); i++) {
            lists.add(new ArrayList<>());
        }

        //将所有菜单按等级划分
        for (MenuTree value : menuTrees) {
            lists.get(value.getLevel()).add(value);
        }

        return generate(lists);
    }

    /**
     * 用户菜单
     */
    public static List<MenuTree> parse(List<MenuTree> menuTrees, String menus) {
        if (menuTrees == null || menuTrees.isEmpty() || ValidatorUtils.isBlank(menus)) {
            return new ArrayList<>();
        }
        int maxLevel = getMaxLevel(menuTrees);
        List<List<MenuTree>> lists = new ArrayList<>();
        for (int i = 0; i < maxLevel; i++) {
            lists.add(new ArrayList<>());
        }

        //将所有菜单按等级划分
        for (MenuTree value : menuTrees) {
            lists.get(value.getLevel()).add(value);
        }

        //将用户菜单id转换
        String[] s = menus.split("\\.");
        //去重(一般不会重复，以防万一)
        Set<Integer> userMenus = new HashSet<>();
        for (String value : s) {
            userMenus.add(Integer.valueOf(value));
        }
        //去除不在用户菜单内的菜单项
        lists.get(maxLevel).removeIf(menuTree -> !userMenus.contains(menuTree.getId()));

        return generate(lists);
    }

    /**
     * 生成菜单树,从上往下分类
     */
    private static List<MenuTree> generate(List<List<MenuTree>> lists) {
        for (int i = 0; i < lists.size() - 1; i++) {
            for (int j = 0; j < lists.get(i).size(); j++) {
                List<MenuTree> m = new ArrayList<>();
                for (int k = 0; k < lists.get(i + 1).size(); k++) {
                    if (lists.get(i + 1).get(k).getParentId() == lists.get(i).get(j).getId()) {
                        m.add(lists.get(i + 1).get(k));
                    }
                }
                lists.get(i).get(j).setList(m);
            }
        }

        return lists.get(0);
    }

    /**
     * 获取最高级数
     */
    private static int getMaxLevel(List<MenuTree> menuTrees) {
        int maxLevel = 0;
        for (MenuTree menuTree : menuTrees) {
            if (menuTree.getLevel() > maxLevel) {
                maxLevel = menuTree.getLevel();
            }
        }
        return maxLevel;
    }
}
