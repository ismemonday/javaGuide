[回到主目录](/README.md)

## 增加用户并这只账号密码
```shell
远程登陆:	CREATE USER '新用户'@'%' IDENTIFIED WITH mysql_native_password BY '新用户的密码'; 
只限本地登陆:	CREATE USER '新用户'@'localhost' IDENTIFIED WITH mysql_native_password BY '新用户的密码'; 
```
## 修改用户密码
```shell
修改密码：	ALTER USER '用户名'@'%' IDENTIFIED WITH mysql_native_password BY '新密码'; //用户名和登陆方式要和原来保持一致，不然修改失败
```
## 给用户增加，修改操作数据库权限
```shell
GRANT 权限 PRIVILEGES on 数据库.数据表 to '用户'@'%';
GRANT all PRIVILEGES on *.* to '用户'@'%';   //给新用户增加所有库所有表的所有权限
权限:all 可以替换为 select,delete,update,create,drop

```
## 查看用户权限
```shell
show grants for 新用户;
```
## 修改用户登录方式
```shell
修改为localhost登陆：	update mysql.user set hoset='localhost' where user='用户'；
flush privileges;

```
## 刷新修改用户权限
```shell
flush privileges;
```