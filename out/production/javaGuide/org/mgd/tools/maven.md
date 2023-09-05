[回到主目录](/README.md)

## maven配置管理

* 配置分类 maven的配置主要有三种方式：
    - ${maven安装目录}/conf/settings.xml
    - ${home}/.m2/settings.xml
    - ${projects}/pom.xml
* [配置详情如setting.xml](https://maven.apache.org/settings.html)

    - localRepository 本地仓库地址${user.home}/.m2/repository
    - interactiveMode 交互默认，默认为true,目前不知道干啥用的
    - offline 默认false,是否连接远程服务器
    - pluginGroups 自定义插件组
    - servers 服务配置，一般用于配置服务用户名和密码
    - mirrors 镜像配置
    - proxies 代理
    - profiles 配置环境
        - activation 环境的默认配置
        - repositories 获取依赖的仓库配置
        - pluginRepositories 获取插件的仓库配置
    - activeProfiles/>    设置活跃环境
* [配置详情如pom.xml](https://maven.apache.org/pom.html)<br/>
  *--The Basics--*
    - groupId
    - artifactId
    - version
    - packaging
    - dependencies
    - parent
    - dependencyManagement
    - modules
    - properties
      *--Build Settings--*
    - build
    - reporting
    -
  <!-- Build Settings -->
  <build>...</build>
  <reporting>...</reporting>

  <!-- More Project Information -->
  <name>...</name>
  <description>...</description>
  <url>...</url>
  <inceptionYear>...</inceptionYear>
  <licenses>...</licenses>
  <organization>...</organization>
  <developers>...</developers>
  <contributors>...</contributors>

  <!-- Environment Settings -->
  <issueManagement>...</issueManagement>
  <ciManagement>...</ciManagement>
  <mailingLists>...</mailingLists>
  <scm>...</scm>
  <prerequisites>...</prerequisites>
  <repositories>...</repositories>
  <pluginRepositories>...</pluginRepositories>
  <distributionManagement>...</distributionManagement>
  <profiles>...</profiles>

---

* repo仓库分类<br>

1. 本地仓库  </br>
    ```java
    本地仓库默认位置为：${home}/.m2/repositories
    可以通过settings.xml中的<localRepository>D:/tem/指定目录</localRepository>
    ```
2. 远程仓库>dddddd

```java
    远程仓库又常称为：私服或中央仓库
        Nexus中将仓库分为三大类：group,proxy,hosted
        *group:仓库组概念，如maven-public就是常用的仓库组，目的就是在获取依赖的时候不知道依赖在哪个具体仓库中，故可以配置仓库组，从组中依次搜寻依赖。
        *proxy:代理仓库，如aliyun仓库，中介的概念，当向此仓库获取依赖，其实本质是从配置的实际仓库获取数据
        *host： 普通仓库，常见的有maven-release,maven-snapshot,maven-mixed,一般用作私服存储指定类型依赖 
```

3. 仓库配置，以settings.xml为例
   ```java```

4.仓库配置优先级

* 在settings.xml中的profile优先级高于pom中的
*
同在settings.xml的properties，如果都激活了，根据profile定义的先后顺序来进行覆盖取值，后面定义的会覆盖前面，其properties为同名properties中最终有效。并不是根据activeProfile定义的顺序
。
* 同在settings.xml中profiles中配置的repository,根据从前到后配置
* 如果有user setting和globel settings，则两者合并，其中重复的配置，以user settings为准。

```

---
* repo配置的优先级
```java


```

## 依赖管理
---

* 依赖传递 <a id="dependency"/>
* 依赖继承 <a id="extends"/>

## 插件管理
---

* 生命周期<a id="life"/>

```shell


```

* 自定义插件<a id="plugin"/>

```shell
#maven构建相关
    <build>
        <finalName>ms</finalName> #打包的依赖名称，如ms.jar 或者ms.war
        <plugins>
            #此插件用于mvn compile时，如果不配置会使用默认的配置。
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration> #一些插件可配置的参数配置
                    <source>11</source> #指定编译时使用的jdk版本，如果不指定会使用默认的配置
                    <target>11</target> #指定编译的.class文件可以在什么jdk版本下执行
                </configuration>
            </plugin>
            #此插件用于mvn package，如果不配置会使用默认的配置, <packaging>war/jar</packaging>
             <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration> #一些插件可配置的参数配置
                    <source>11</source> #指定编译时使用的jdk版本，如果不指定会使用默认的配置
                    <target>11</target> #指定编译的.class文件可以在什么jdk版本下执行
                </configuration>
            </plugin>
        </plugins>
    </build>

```
source和target有什么区别？
```shell
    在Maven插件中，source和target是两个重要的参数，用于指定编译Java源代码的版本和目标Java版本。source指定源代码的版本，通常是JDK的版本号，例如1.8、1.9等。而target指定编译后生成的字节码的版本，通常也是JDK的版本号，例如1.6、1.7、1.8等。
区别在于，source指定的是开发环境可以使用的Java版本，而target指定的是编译后生成的字节码可以在哪些版本的Java环境下运行。如果source和target的版本不同，则会出现源代码编译成功，但在运行时出现兼容性问题的情况。
因此，建议在配置Maven插件时，source和target的值应当设置为相同的版本号，以确保编译出的字节码可以在目标Java环境中正常运行。
```

## 可执行的jar包
```shell
java -cp 目录：jar包 主类名
```