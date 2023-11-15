package org.mgd.design;

/**
 * 建造者模式
 */
public class BuilderClassDesign {
    private int age;
    private String name;



    public static DogBuilder builder() {
        return new DogBuilder();
    }


    public static class DogBuilder{
        private int age;
        private String name;

        public DogBuilder age(int age){
            this.age=age;
            return this;
        }

       public DogBuilder name(String name){
           this.name=name;
           return this;
       }

        public BuilderClassDesign build() {
            BuilderClassDesign builderClassDesign = new BuilderClassDesign();
            builderClassDesign.age=age;
            builderClassDesign.name=name;
            return builderClassDesign;
        }
    }
}
