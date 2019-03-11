package com.thoughtworks;

/**
 * @author William D X Zheng
 * @date 2019/3/11 23:45
 */
public enum City {
    // main use of overriding enumerations
    A("A", 0),
    B("B", 1),
    C("C", 2),
    D("D", 3),
    E("E", 4),
    ANY("", -1),
    OPTIONAL("", -2);
    private int index;
    private String name;

    City(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * >= 0: normal cities.
     * <  0: else status.
     * @return index
     */
    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
