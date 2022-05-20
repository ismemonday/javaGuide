## maven配置管理
---
* 配置分类
```java
    maven的配置主要有三种方式：
    1. ${maven安装目录}/conf/settings.xml
    2. ${home}/.m2/settings.xml
    3. ${projects}/pom.xml
```
---
 * settings.xml配置详情
```java
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
      <localRepository/>    //本地仓库地址${user.home}/.m2/repository
      <interactiveMode/>    //交互默认，默认为true,目前不知道干啥用的
      <offline/>            //默认false,是否连接远程服务器
      <pluginGroups/>       //自定义插件组
      <servers/>            //服务配置，一般用于配置服务用户名和密码
      <mirrors/>            //镜像配置
      <proxies/>            //代理
      <profiles/>           //配置环境
      <activeProfiles/>     //设置活跃环境
    </settings>

```
---
* rep仓库分类
a.


---
* repo配置的优先级
```java
* 在settings.xml中的profile优先级高于pom中的
* 同在settings.xml的properties，如果都激活了，根据profile定义的先后顺序来进行覆盖取值，后面定义的会覆盖前面，其properties为同名properties中最终有效。并不是根据activeProfile定义的顺序 。
* 同在settings.xml中profiles中配置的repository,根据从前到后配置
* 如果有user setting和globel settings，则两者合并，其中重复的配置，以user settings为准。
```
---
## 依赖管理
* 依赖传递
* 依赖继承

## 插件管理
* 生命周期
* 自定义插件