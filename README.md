# bgbilling-servlet-demo

## Что это?

bgbilling-servlet-demo - это демонстрационная реализация сервлета для использования совместно с сервером [BGBilling](https://bgbilling.ru/). 

## Требования

* git
* JDK 8
* [Maven](https://maven.apache.org/)

## Как это запустить? 

Выполните команды:

```
git clone https://github.com/alexanderfefelov/bgbilling-servlet-demo
cd bgbilling-servlet-demo
mvn package
```

Скопируйте jar-файл, созданный в результате в каталоге `target`, в каталог `lib/custom` вашего экземпляра BGBilling.

В конфигурацию BGBilling добавьте:

```properties
custom.servlet.keys=DemoServlet
#                   │         │
#                   └─┬───────┘
#                     │
#                   Ключ                                    Класс
#                     │                                       │
#              ┌──────┴──┐       ┌────────────────────────────┴───────────────────────────────┐
#              │         │       │                                                            │
custom.servlet.DemoServlet.class=com.github.alexanderfefelov.bgbilling.servlet.demo.DemoServlet
custom.servlet.DemoServlet.mapping=/demo-servlet
#                                  │           │
#                                  └─────┬─────┘
#                                        │
#                            Часть URL после контекста
```

Перезапустите BGBilling.

Если всё в порядке, в логах должно появиться:

```
01-11/21:16:31  INFO [main] Server - Add custom servlet from setup...
01-11/21:16:31  INFO [main] Server - Custom.servlet.keys => DemoServlet
01-11/21:16:31  INFO [main] Server - Custom.servlet.class => com.github.alexanderfefelov.bgbilling.servlet.demo.DemoServlet
01-11/21:16:31  INFO [main] Server - Custom.servlet.mapping => /demo-servlet
01-11/21:16:31  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.DemoServlet to /demo-servlet
```

Теперь выполните:

```
http --verbose --check-status \
  GET http://bgbilling-server.backpack.test:63081/billing/demo-servlet
```

В результате на запрос:

```
GET /billing/demo-servlet HTTP/1.1
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
Date: Mon, 11 Jan 2021 18:23:31 GMT

Hello, World!
```

## Логи

Для того, чтобы собирать логи сервлета в отдельный файл, необходимо изменить `data/log4j.xml`.

Добавьте новый аппендер:

```xml
<appender name="SERVLET" class="org.apache.log4j.RollingFileAppender">
    <param name="File" value="/BGBillingServer/log/servlet.log"/>
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
    <appender-ref ref="SCRIPT"/>
    <appender-ref ref="SERVLET"/>
    <appender-ref ref="ERROR"/>
</appender>
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Посмотрите аналогичные проекты на других языках:
  * Clojure - https://github.com/alexanderfefelov/bgbilling-servlet-demo-clojure,
  * Kotlin - https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin,
  * Scala - https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala.
