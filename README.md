# java-module-template
Java 模板

## 打包
`````
  使用 gradle 打包， 打包成 war 包
  外部 jar 包放在 ./libs 目录下， 打包的时候可以自动加到目录下
  静态资源放在./controller/../resources/webapp目录下
`````

## 启动
``````$xslt
 1、直接启动 java -jar -Dspring.config.location=application.yml  xxx.war
    application.yml 是放在 war 统计目录下， 可以覆盖 war 包里面的配置文件
   
 2、放到tomcat里面启动
``````