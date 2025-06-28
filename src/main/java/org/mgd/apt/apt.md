[回到主目录](/README.md)
## APT技术（Annotation Processing Tool）
```text
APT 是一种注解处理工具，主要用于在编译时解析源代码中的注解，并根据这些注解生成新的源代码或配置文件。它常用于框架中实现零运行时开销的代码生成。
用途：编译期处理注解，生成代码。
示例：javac 编译器通过 AbstractProcessor 处理注解。编译时期的特殊校验
特点：
提高开发效率。
实现声明式编程（如依赖注入、路由映射等）。
```
自定义一个编译校验器
- 1 配置入口
```text
目录：
src
    main
        java
            org.some.MyProcessor.java
        resources
            META-INF
                services
                    javax.annotation.processing.Processor
内容：
org.some.MyProcessor.java
```
- 2 定义Process实现类
```java
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class MyProcessor extends AbstractProcessor {
    JavacTrees trees;
    private Messager messager;
    private final String restAnnotation = "org.springframework.web.bind.annotation.RestController";
    private final String[] ignoreAnnotations = {"Deprecated", "OperateRecordNoNeed"};
    private final String needAnnotation = "OperateRecord";
    private final String[] controllerMethodCheckWithAnnotations = {"PostMapping", "PutMapping", "DeleteMapping", "PatchMapping"};


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(restAnnotation);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            if (restAnnotation.equals(((Symbol.ClassSymbol) annotation).className())) {
                Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(annotation);
                recordFlag:
                for (Element element : elementsAnnotatedWith) {
                    for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                        for (String ignoreAnnotation : ignoreAnnotations) {
                            if (annotationMirror.getAnnotationType().toString().contains(ignoreAnnotation)) {
                                continue recordFlag;
                            }
                        }
                    }
                    processEverElement(element);
                }
            }
        }
        return roundEnv.processingOver();
    }

    private void processEverElement(Element element) {
        JCTree jcTree = trees.getTree(element);
        jcTree.accept(new TreeTranslator() {
            @Override
            public void visitMethodDef(JCTree.JCMethodDecl methodDecl) {
                List<JCTree.JCAnnotation> annotations = methodDecl.getModifiers().getAnnotations();
                boolean inCheckAnnotation = inControllerMethodCheckAnnotations(annotations);
                if (inCheckAnnotation) {
                    if (!withIgnoreAnnotations(annotations)) {
                        doRealCheckOperateRecordAnnotation(methodDecl, annotations);
                    }
                }
                super.visitMethodDef(methodDecl);
            }
        });
    }

    /**
     * 看需要加注解的地方是否有注解
     *
     * @param annotations
     */
    private void doRealCheckOperateRecordAnnotation(JCTree.JCMethodDecl method, List<JCTree.JCAnnotation> annotations) {
        boolean needFlag = false;
        for (JCTree.JCAnnotation annotation : annotations) {
            if (annotation.toString().contains(needAnnotation)) {
                needFlag = true;
                break;
            }
        }
        if (!needFlag) {
            String methodName = method.getName().toString();
            String className = ((Symbol.ClassSymbol) method.sym.owner).fullname.toString();
            StringBuilder stringBuild = new StringBuilder(" ,也没有忽略标记");
            for (String ignoreAnnotation : ignoreAnnotations) {
                stringBuild.append("@").append(ignoreAnnotation).append(",");
            }
            messager.printMessage(Diagnostic.Kind.ERROR, "方法:" + className + "." + methodName + " ,没有添加注解@" + needAnnotation + stringBuild);
        }
    }


    /**
     * 看是否加了需要忽略的注解
     *
     * @param annotations
     * @return
     */
    private boolean withIgnoreAnnotations(List<JCTree.JCAnnotation> annotations) {
        for (JCTree.JCAnnotation annotation : annotations) {
            for (String ignoreAnnotation : ignoreAnnotations) {
                if (annotation.toString().contains(ignoreAnnotation)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 这些注解是否是需要校验的注解
     *
     * @param annotations
     * @return
     */
    private boolean inControllerMethodCheckAnnotations(List<JCTree.JCAnnotation> annotations) {
        for (JCTree.JCAnnotation annotation : annotations) {
            for (String controllerMethodCheckWithAnnotation : controllerMethodCheckWithAnnotations) {
                if (annotation.toString().contains(controllerMethodCheckWithAnnotation)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        trees = JavacTrees.instance(processingEnv);
    }
}
```

## SPI技术（Service Provider Interface）
```text

SPI 是 Java 提供的一种服务发现机制，允许第三方为某个接口或抽象类提供实现，并通过配置文件动态加载这些实现。
用途：动态加载接口的实现类。
配置：在 META-INF/services 目录下创建以接口全限定名命名的文件，文件内容为实现类的全限定名。
示例：JDBC 驱动加载、日志框架实现切换。
特点：通过ServiceLoader类加载，实现动态加载。
支持模块化开发。
实现解耦，提高扩展性。
```

mysql示例：
```java
  //mysql的DriverManager中通过SPI技术加载Driver驱动
   ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
    Iterator<Driver> driversIterator = loadedDrivers.iterator();
```
