[回到主目录](/README.md)
# Https方案
---
## 整体流程
![https方案.png](https方案.png)
## SpringBoot实现
- ### 修改配置文件，开启ssl加密
```text
server:
  port: 8089
  ssl:
    protocol: TLS
    key-store: "classpath:springtest.jks" #指定证书位置,keystore证书库类似数据库文件
    key-store-password: javastack #服务私钥
    key-store-type: JKS #证书类型
```
- ### 生成自签证书
```text
使用java自带的keytool.exe生成自签证书
```
```text
keytool -genkeypair -alias springtest -keyalg RSA -keysize 2048 -keypass springtest -validity 1000 -keystore D:\springtest.jks -storepass springtest
keytool 生成密钥对 别名 加密方式 加密长度 密钥 时效 密钥库 密钥库密码
```

### keytool使用和证书的基本概念
- #### 自签证书和机构颁发证书

- #### 常见的名词
  - 文件列表
    <table>
      <tr>
      </tr>
    </table>
    11
    ffsd
  - 名词详解
    - csr：二进制文件；certificate request:证书请求 一段字符串解析后数据如下
      ```text
          通用名称(CN) maoguidong.com
          备用名
          国家(C) CN
          省份(S) Zhejiang Province
          城市(L) Hangzhou City
          组织(O)
          部门(OU)IT
          密钥强度 2048
          密钥类型 RSA
          签名算法 SHA256
          签名是否有效 是
          签名信息
           a5358480e19d5e89edcd67b76fc30f08856fe0767d7525b701f132f6c8e0f61a
           b237dccd1660bc76a803737712a3a836c3ff651accb44f79d51b2c603a115abe
           15be226b1eedf00d8552219199c1768b236de547cd3fb5c7132c889d78321856
           1c1b661f88ef9c308297d971a0b0f67add2fafcc980402d8d88f37c61d20d27f
           63b060e330821771d31b533f9095a11a3be6382b2078d71756bc79632c8b7559
           cb34005fa85eb018ecb641e1cde08beb218fddb4b0b7a748c81e002cfe7d3d73
           1e65509cb5590cd385243781eaa8918020a7e77c52c9fd5a4ace29163f899ae4
           cc52019efe70ddfeb6465f3c3f2785c7fafc6c88c19becba413c4c1d048d38ab
      ```
    - rsa:非对称加密算法
      ```text
        RSA是一种非对称加密算法，它使用两个密钥，即公钥和私钥，用于加密和解密数据
      ```
    - CA:证书颁发机构
    - CRT文件：certificate数字证书文件
      ```text
        CRT文件是一种数字证书文件，常用于存储和传输SSL/TLS证书。它包含了公钥以及与公钥相关的证书信息。
      ```
    - .key文件：证书的私钥文件（申请证书时如果没有选择自动创建CSR，则没有该文件）
      ```text
        Key文件包含了用于加密和解密数据的密钥信息，通常是私钥
      ```
    - .pem文件：是一种文本格式的文件，可以是私钥文件，也可以是证书文件，nginx的证书通常采用pem格式的
      - 下面是表示私钥的pem格式的私钥文件
      ```text
        -----BEGIN PRIVATE KEY-----
        MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDBaEW7bbrb5+Ah...
      ```
      - 下面是表示证书的的pem格式的证书文件
      ```text
        -----BEGIN CERTIFICATE -----
        MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDBaEW7bbrb5+Ah...
      ```
- #### 证书中的ip或者域名和实际访问的ip和域名不一致会怎样
```text
    当客户端向服务器发送请求时，服务器会返回其证书。客户端会验证证书的合法性，
   其中的一个重要步骤就是检查证书中的主体（IP或域名）是否与客户端请求的主机匹配。
   如果证书中的IP或域名与实际访问的主机不一致，客户端会认为证书不可信，并显示安全警告或者拒绝建立连接。
```