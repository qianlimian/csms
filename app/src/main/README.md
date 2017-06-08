1. 此项目为java application项目，集成了spring + spring data jpa(hibernate)，没有web端
2. MainClass: com.bycc.App
3. 开发阶段resources下的资源文件打包进app.jar;
   上线部署时资源文件放在config目录，方便修改配置（注意：需要修改pom文件！！！）
4. 运行mvn assembly:assembly（也可以用package或install)来打包最终的jar的zip文件（名为*-bin）