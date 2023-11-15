package org.mgd.design;



public class DesignTest {
    public static void main(String[] args) {
        BuilderClassDesign builderClassDesign = BuilderClassDesign.builder().name("111").age(16).build();
        System.out.println(builderClassDesign);
    }
}
