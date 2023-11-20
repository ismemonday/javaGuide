module hello{
    requires java.base;
    requires java.desktop;
    requires java.compiler;
    requires jdk.jdi;
    exports org.mgd.aop to java.base,java.desktop;
    requires jdk.unsupported;
}