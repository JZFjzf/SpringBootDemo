package com.choimroc.demo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author choimroc
 * @since 2019/3/11
 */
@Setter
@Getter
public class City {
    private int id;
    private String name;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
