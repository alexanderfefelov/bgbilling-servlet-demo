# bgbilling-servlet-demo

## Что это?

bgbilling-servlet-demo -- это демонстрационная реализация сервлета для использования совместно
с сервером [BGBilling](https://bgbilling.ru/). 

## Требования

* git
* JDK 8
* [Maven](https://maven.apache.org/)

## Как это запустить? 

Выполните команды:

```
git clone https://github.com/alexanderfefelov/bgbilling-servlet-demo.git
cd bgbilling-servlet-demo
mvn package
```

jar-файл, созданный в результате в каталоге `target`, скопируйте в каталог `lib/app` сервера BGBilling.

В конфигурацию BGBilling добавьте:

```
custom.servlet.keys=DemoServlet
#                    /
#                   |
#                   v
custom.servlet.DemoServlet.class=com.github.alexanderfefelov.bgbilling.servlet.demo.DemoServlet
custom.servlet.DemoServlet.mapping=/demo-servlet
```

Перезапустите сервер BGBilling.

Для проверки выполните

```
curl --request GET --include http://YOUR.BGBILLING.HOST:8080/bgbilling/demo-servlet
```

В ответ вы получите что-то вроде такого:

```
HTTP/1.1 200 OK
Content-Length: 14
Date: Sun, 30 Dec 2018 06:40:53 GMT

Hello, World!
```

## Логи

С дефолтными настройками BGBilling все логи из сервлета будут попадать в файл `log/server.log`.
Для того, чтобы логи собирались в отдельном файле, необходимо изменить `data/log4j.xml`.

Добавьте новый аппендер:

```xml
<appender name="SERVLET" class="org.apache.log4j.RollingFileAppender">
    <param name="File" value="${log.dir.path}${log.prefix}.servlet.log"/>
    <param name="MaxFileSize" value="100MB"/>
    <param name="MaxBackupIndex" value="2"/>
    <param name="Append" value="true"/>

    <layout class="org.apache.log4j.PatternLayout">
        <param name="ConversionPattern" value="%d{MM-dd/HH:mm:ss} %5p [%t] %c{1} - %m%n"/>
    </layout>

    <filter class="ru.bitel.common.logging.Log4JMDCFilter">
        <param name="key" value="nestedContext"/>
        <param name="value" value="servlet"/>
    </filter>
</appender>
```

и исправьте имеющийся:

```xml
<appender name="ASYNC" class="ru.bitel.common.logging.Log4jAsyncAppender">
    <appender-ref ref="APPLICATION"/>
    <appender-ref ref="MQ"/>
    <appender-ref ref="SERVLET"/>
    <appender-ref ref="SCRIPT"/>
    <appender-ref ref="ERROR"/>
</appender>
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Посмотрите аналогичные проекты на языках [Scala](https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala) и [Kotlin](https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin).
