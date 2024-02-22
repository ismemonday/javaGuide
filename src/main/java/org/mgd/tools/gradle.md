[回到主目录](/README.md)  

 -----
## gradle的mavenLocal相关内容
**1.mavenLocal的默认路径和默认路径修改**  

---
**2.查看当前gradle项目的默认mavenLocal路径**  

---

**3.gradle项目如何使用maven仓库中的jar包**

---
**4.gradle_home和gradle_user_home的关系** 

---
**5.repositories仓库配置的配置逻辑**



---

***利用task查看repositories信息***
```groovy
task showMavenInfo(){
    println("mavenLocal url:"+repositories.mavenCentral().getUrl());
    repositories.getNames().forEach(name->println("name:"+name+"  url:"+repositories.getByName(name).getUrl()))
}
```