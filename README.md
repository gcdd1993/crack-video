# crack-video
#### 简介
开源的全网VIP视频解析器
#### 使用教程

----------
##### 下载
- [百度云](https://pan.baidu.com/s/1gmh5R9WpfT1VbGhOThBMNw "百度云")
#### 常用功能

----------

- 搜索视频(支持网站:腾讯视频、爱奇艺、优酷)
![](https://i.imgur.com/Znc8JdE.png)
- 点击右边，弹出设置按钮
![](https://i.imgur.com/Sp1Dw7S.png)
- 支持自定义VIP解析器(请至少启用一个VIP解析器)
![](https://i.imgur.com/OfKSNeE.png)
- 进入详情页，点击解析播放即可
![](https://i.imgur.com/GWLdmBR.png)
#### 开发中的功能:

----------

- 支持更多网站(优酷、爱奇艺...)
- 自动获取VIP解析器
- 智能使用VIP解析器(爱奇艺使用爱奇艺播放接口,腾讯使用腾讯播放接口...)
- ...
#### 开发

----------

##### 环境
- java1.8+ 
- gradle 
##### 编译

----------

    git clone https://github.com/qq1398371419/crack-video.git
    cd crack-video
    gradlew shadowJar
##### 运行

----------

    cd build/libs
    java -jar crack-video-1.0-SNAPSHOT-all.jar

