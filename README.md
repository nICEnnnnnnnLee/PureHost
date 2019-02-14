# PureHost
一个免Root实现类似PC端Host功能的app 

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

## LICENSE
Apache 2.0
