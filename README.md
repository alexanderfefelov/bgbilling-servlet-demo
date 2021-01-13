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

- [HelloWorld.java](src/main/java/com/github/alexanderfefelov/bgbilling/servlet/demo/HelloWorld.java)
- [TerryPratchettFilter.java](src/main/java/com/github/alexanderfefelov/bgbilling/servlet/demo/TerryPratchettFilter.java)

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

## О системе

- [SysInfo.java](src/main/java/com/github/alexanderfefelov/bgbilling/servlet/demo/SysInfo.java)
- [UptimePuncherFilter.java](src/main/java/com/github/alexanderfefelov/bgbilling/servlet/demo/UptimePuncherFilter.java)

Добавьте в конфигурацию BGBilling:

```properties
# Servlet: О системе
#
custom.servlet.keys=SysInfo
#                   │     │
#                   └─┬───┘
#                     │
#             Ключ сервлета                       Класс сервлета
#                  │                                     │
#              ┌───┴─┐       ┌───────────────────────────┴────────────────────────────┐
#              │     │       │                                                        │
custom.servlet.SysInfo.class=com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfo
custom.servlet.SysInfo.mapping=/demo-servlet/sys-info
#                              │                    │
#                              └──────────┬─────────┘
#                                         │
#                             Часть URL после контекста
#
custom.servlet.SysInfo.filter.keys=UptimePuncher,TerryPratchett,CORS
#                                  │           │ │            │ │  │
#                                  └───┬───────┘ └─────┬──────┘ └─┬┘
#                                      │               │          │
#                                 Ключ фильтра    Ещё фильтр    И ещё один
#                                      │
#                             ┌────────┴──┐
#                             │           │
custom.servlet.SysInfo.filter.UptimePuncher.name=UptimePuncher
custom.servlet.SysInfo.filter.UptimePuncher.class=com.github.alexanderfefelov.bgbilling.servlet.demo.UptimePuncherFilter
#                                                 │                                                                    │
#                                                 └──────────────────────────────┬─────────────────────────────────────┘
#                                                                                │
#                                                                          Класс фильтра
custom.servlet.SysInfo.filter.TerryPratchett.name=TerryPratchett
custom.servlet.SysInfo.filter.TerryPratchett.class=com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilter
custom.servlet.SysInfo.filter.CORS.name=CORS
custom.servlet.SysInfo.filter.CORS.class=org.apache.catalina.filters.CorsFilter
custom.servlet.SysInfo.filter.CORS.init-param.keys=AllowedOrigins
#                                                  │            │
#                                                  └───┬────────┘
#                                                      │
#                                                Ключ параметра    Название параметра
#                                                      │                    │
#                                             ┌────────┴───┐      ┌─────────┴────────┐
#                                             │            │      │                  │
custom.servlet.SysInfo.filter.CORS.init-param.AllowedOrigins.name=cors.allowed.origins
custom.servlet.SysInfo.filter.CORS.init-param.AllowedOrigins.value=*
#                                                                  │
#                                                                  │
#                                                         Значение параметра
```

Перезапустите BGBilling.

Теперь в логах будет так:

```
01-13/21:07:35  INFO [main] Server - Add custom servlet from setup...
01-13/21:07:35  INFO [main] Server - Custom.servlet.keys => SysInfo
01-13/21:07:35  INFO [main] Server - Custom.servlet.class => com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfo
01-13/21:07:35  INFO [main] Server - Custom.servlet.mapping => /demo-servlet/sys-info
01-13/21:07:35  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfo to /demo-servlet/sys-info
01-13/21:07:35  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.UptimePuncherFilter to /demo-servlet/sys-info
01-13/21:07:35  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilter to /demo-servlet/sys-info
01-13/21:07:35  INFO [main] Server - Add mapping: org.apache.catalina.filters.CorsFilter to /demo-servlet/sys-info
```

и в ответ на запрос:

```
http --verbose --check-status \
  GET http://bgbilling-server.backpack.test:63081/billing/demo-servlet/sys-info \
    "Origin: http://example.com"
```

```
GET /billing/demo-servlet/sys-info HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: bgbilling-server.backpack.test:63081
Origin: http://example.com
User-Agent: HTTPie/1.0.3
```

вы получите:

```
HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: http://example.com
Content-Length: 488
Date: Wed, 13 Jan 2021 18:55:20 GMT
Vary: Origin
X-BGBilling-Server-Uptime: Started: 13.01.2021 21:55:07 Uptime: 0 d 00:00:12
X-Clacks-Overhead: GNU Terry Pratchett

Modules
--------------------------------------------------

0 kernel 8.0.1320 / 16.12.2020 18:10:08
2 inet 8.0.832 / 15.12.2020 17:06:32
1 card 8.0.307 / 06.10.2020 01:52:21
3 npay 8.0.287 / 19.11.2020 18:41:17
5 subscription 8.0.128 / 06.10.2020 01:52:39
4 rscm 8.0.272 / 06.10.2020 01:52:36

Runtime
--------------------------------------------------

Hostname/IP address: bgbilling-server.backpack.test/172.17.0.8
Available processors: 8
Memory free / max / total, MB: 156 / 444 / 307
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
