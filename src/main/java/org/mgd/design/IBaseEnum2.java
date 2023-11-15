package org.mgd.design;

public interface IBaseEnum2 {
     default void initEnum(int code,String msg){
         new IBaseBean(code, msg);
     }

    default int getCode(){
         return 1;
    }

    class IBaseBean{
          private final int code;
          private final String msg;

          public IBaseBean(int code, String msg) {
              this.code = code;
              this.msg = msg;
          }
      }
}
