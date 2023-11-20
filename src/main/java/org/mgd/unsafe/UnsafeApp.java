package org.mgd.unsafe;


import jdk.internal.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.LockSupport;


public class UnsafeApp {
    public static void main(String[] args) throws Exception {
        Class<Unsafe> unsafeClass = Unsafe.class;
        Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Object o = theUnsafe.get(null);
        System.out.println(o.getClass().getName());
        sun.misc.Unsafe unsafe = sun.misc.Unsafe.getUnsafe();
        ByteBuffer.allocateDirect(10);
        LockSupport.park();
    }

}
