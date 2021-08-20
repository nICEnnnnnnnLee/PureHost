# PureHost
一个免Root实现类似PC端Host功能的app。   
该项目VpnService部分只拦截了必要的UDP报文，对TCP的处理请参考[freedom4NG](https://github.com/nICEnnnnnnnLee/freedom4NG)


## 下载
[PureHost](https://github.com/nICEnnnnnnnLee/PureHost/releases)

## 简介
* 基于Android VpnService
* 部分协议实现参考[shadowsocks-android-java](https://github.com/dawei101/shadowsocks-android-java)

## 功能使用及预览
* 程序运行界面如下图：
![](https://raw.githubusercontent.com/nICEnnnnnnnLee/PureHost/master/view/settings.jpg)
    * DNS设置为可选项，点击**DNS保护**为使用界面设置DNS，点击**DNS还原**使用默认DNS
    * Host设置与PC端一致，支持注释。点击**Host保护**为读取并使用程序设置的Host，点击**Host更改**将当前界面Host配置写入程序设置。  
    一个简单的流程是： 修改界面Hosts -> 点击**Host更改** -> 点击**Host保护** -> 点击**START**

* 功能预览（将[百度](https://www.baidu.com)指向了[nicelee.top](http://nicelee.top)）：
![](https://raw.githubusercontent.com/nICEnnnnnnnLee/PureHost/master/view/function-preview.jpg)

## 更新日志
* v3.0
    修复了一个bug, 该bug导致处理某些非DNS查询的UDP包时功能会出现异常
* v2.0
    * 增加基础域名匹配(全域名匹配优先任意匹配)  
    e.g. host文件
    ```
    127.0.0.3 www.test.com
    127.0.0.1 *.test.com
    127.0.0.2 test.test.com
    ```
    那么，
    ```
    test.test.com --> 127.0.0.2
    www.test.com --> 127.0.0.3
    test.com --> 127.0.0.1
    123.test.com --> 127.0.0.1
    xxx.test.com --> 127.0.0.1
    ```
* v1.0
    * 初版
## LICENSE
Apache 2.0
