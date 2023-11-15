package org.mgd.design;

public enum Enum2 implements IBaseEnum<Integer>{
    A(1,""),B(2,"");

    Enum2(int i, String s) {
        initEnum(i, s);
    }
}
