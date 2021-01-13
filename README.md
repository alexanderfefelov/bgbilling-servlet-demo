# bgbilling-servlet-demo

## Что это?

bgbilling-servlet-demo - это демонстрационная реализация сервлетов для использования совместно с сервером [BGBilling](https://bgbilling.ru/). 

## Требования

* git
* JDK 8
* [Maven](https://maven.apache.org/)

## Как это установить? 

Выполните команды:

```
git clone https://github.com/alexanderfefelov/bgbilling-servlet-demo
cd bgbilling-servlet-demo
mvn package
```

Скопируйте jar-файл, созданный в результате в каталоге `target`, в каталог `lib/custom` вашего экземпляра BGBilling.

## Привет, мир!

[HelloWorld.java](src/main/java/com/github/alexanderfefelov/bgbilling/servlet/demo/HelloWorld.java)

В конфигурацию BGBilling добавьте:

```properties
# Servlet: Привет, мир!
#
custom.servlet.keys=HelloWorld
#                   │        │
#                   └─┬──────┘
#                     │
#               Ключ сервлета                         Класс сервлета
#                     │                                      │
#              ┌──────┴─┐       ┌────────────────────────────┴──────────────────────────────┐
#              │        │       │                                                           │
custom.servlet.HelloWorld.class=com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorld
custom.servlet.HelloWorld.mapping=/demo-servlet/hello-world
#                                 │                       │
#                                 └───────────┬───────────┘
#                                             │
#                                 Часть URL после контекста
#
custom.servlet.HelloWorld.filter.keys=TerryPratchett
#                                     │            │
#                                     └───┬────────┘
#                                         │
#                                    Ключ фильтра
#                                         │
#                                ┌────────┴───┐
#                                │            │
custom.servlet.HelloWorld.filter.TerryPratchett.name=TerryPratchett
custom.servlet.HelloWorld.filter.TerryPratchett.class=com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilter
#                                                     │                                                                     │
#                                                     └──────────────────────────────┬──────────────────────────────────────┘
#                                                                                    │
#                                                                              Класс фильтра
```

Перезапустите BGBilling.

Если всё в порядке, в логах можно будет увидеть:

```
01-13/14:13:03  INFO [main] Server - Add custom servlet from setup...
01-13/14:13:03  INFO [main] Server - Custom.servlet.keys => HelloWorld
01-13/14:13:03  INFO [main] Server - Custom.servlet.class => com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorld
01-13/14:13:03  INFO [main] Server - Custom.servlet.mapping => /demo-servlet/hello-world
01-13/14:13:03  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorld to /demo-servlet/hello-world
01-13/14:13:03  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilter to /demo-servlet/hello-world
```

Теперь выполните:

```
http --verbose --check-status \
  GET http://bgbilling-server.backpack.test:63081/billing/demo-servlet/hello-world
```

В результате на запрос:

```
GET /billing/demo-servlet/hello-world HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: bgbilling-server.backpack.test:63081
User-Agent: HTTPie/1.0.3
```

будет получен ответ:

```
HTTP/1.1 200 OK
Content-Length: 14
Date: Wed, 13 Jan 2021 11:14:37 GMT
X-Clacks-Overhead: GNU Terry Pratchett

Hello, World!
```

## Логи

Для того, чтобы собирать логи сервлетов в отдельный файл, необходимо изменить `data/log4j.xml`.

Добавьте новый аппендер:

```xml
<appender name="SERVLET" class="org.apache.log4j.RollingFileAppender">
    <param name="File" value="log/servlet.log"/>
    <param name="MaxFileSize" value="50MB"/>
    <param name="MaxBackupIndex" value="3"/>
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
    <appender-ref ref="ERROR"/>
    <appender-ref ref="MQ"/>
    <appender-ref ref="SCRIPT"/>
    <appender-ref ref="SERVLET"/>
</appender>
```

В результате после перезапуска BGBilling в файле `log/servlet.log` можно будет увидеть что-то вроде:

```
01-13/14:13:11 TRACE [localhost.localdomain-startStop-1] TerryPratchettFilter - init
01-13/14:14:37 TRACE [http-nio-0.0.0.0-8080-exec-4] HelloWorld - init
01-13/14:14:37 TRACE [http-nio-0.0.0.0-8080-exec-4] TerryPratchettFilter - doFilter
01-13/14:14:37 TRACE [http-nio-0.0.0.0-8080-exec-4] HelloWorld - doGet
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Посмотрите аналогичные проекты на других языках:
  * Clojure - https://github.com/alexanderfefelov/bgbilling-servlet-demo-clojure,
  * Kotlin - https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin,
  * Scala - https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala.
