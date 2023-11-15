package org.mgd.design;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface IBaseEnum<T> {
     default void initEnum(T code,String msg){
        EnumContainer.putEnum(this,code,msg);
     }

     default T getCode(){
         return EnumContainer.getEnum(this).getCode();
     }

    default String getMsg(){
        return EnumContainer.getEnum(this).getMgs();
    }

    class EnumContainer{
        private static final Map<IBaseEnum,EnumBean> ENUM_MAP=new ConcurrentHashMap<>();

        static <T>void putEnum(IBaseEnum<T> iBaseEnum,T code,String msg){
            ENUM_MAP.put(iBaseEnum, new EnumBean(code, msg));
        }

        static <K extends  IBaseEnum<T>,T>EnumBean<T> getEnum(K key){
            return ENUM_MAP.get(key);
        }
    }

    class EnumBean<T>{
         private final T code;
         private final String mgs;

        public T getCode() {
            return code;
        }

        public String getMgs() {
            return mgs;
        }

        public EnumBean(T code, String mgs) {
            this.code = code;
            this.mgs = mgs;
        }
    }


}
