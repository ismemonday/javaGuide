## 仓库的分类：
```java
本地仓库:localrepository  
远程仓库:remote repositoy
```
## 仓库优先级：
```java
* 在settings.xml中的profile优先级高于pom中的
* 同在settings.xml的properties，如果都激活了，根据profile定义的先后顺序来进行覆盖取值，后面定义的会覆盖前面，其properties为同名properties中最终有效。并不是根据activeProfile定义的顺序 。
* 如果有user setting和globel settings，则两者合并，其中重复的配置，以user settings为准。

```