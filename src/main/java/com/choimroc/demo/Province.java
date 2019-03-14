package com.choimroc.demo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author choimroc
 * @since 2019/3/11
 */
@Getter
@Setter
public class Province {
    private int id;
    private String name;
    private List<City> cities;

    public Province(int id, String name, List<City> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
    }


}

