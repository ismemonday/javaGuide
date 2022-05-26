[回到主目录](/README.md)
# git获取远程新分支
* 查看远程分支

        git branch -r

* 查看远程和本地分支
        
        git branch -a

* 查看本地分支
    
        git branch

* 拉取远程分支并创建本地分支
	```txt
    默认切换到新分支：	git checkout -b 本地分支名x origin/远程分支名x
	需要手动切换：	git fetch origin 远程分支名x:本地分支名x
    ```

* 本地新分支关联上远程分支，并推送
    ```java
    关联：	git branch --set-upstream-to=origin/远程分支名 本地分支名
    推送：   git push origin HEAD:远程分支名
    ```