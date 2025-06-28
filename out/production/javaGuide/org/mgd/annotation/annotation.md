[回到主目录](/README.md)
## 自定义注解的基本探讨
### 继承性的探讨
```text
准备两个自定义注解，配置可以作用于类和方法，一个注解使用@Inherited,一个注解不使用@Inherited
```
[NoInheritedPeo注解](NoInheritedPeo.java)<br>
[InheritedPeo注解](InheritedPeo.java)

- 测试注解在类和方法上的继承关系
[关系的具体代码](Application.java)

### 结论
- 注解中的@Inherited注解标识当前当前自定义注解的继承性，但是只作用于ElementType.TYPE,且类的继承才能获取到，接口的继承无法获取
- 子类如果重写了父类方法则无法获取父类方法的注解，子类没有重写父类的方法，可以获取到父类的注解

## 实际应用场景
```text
问题：在aop面向切面编程中容易遇到混合自定义注解的应用，获取自定义注解为空的场景思考
```
[aop中获取自定义注解为空的解释](../aop/ApplicationAop.java)
```text
结论：
由于jdK的动态代理是基于在内存中根据接口动态生成的实现类，而接口上的自定义注解不具备继承性
但是基于cglib实现的动态代理是基于继承实体类实现的，没有重新目标方法，自定义注解具备继承性

参考Spring中AnnotationUtils类
```