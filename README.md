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

jar-файл, созданный в результате в каталоге `target`, скопируйте в каталог `lib/ext` сервера BGBilling.

В конфигурацию BGBilling добавьте это:

```
custom.servlet.keys=DemoServlet
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

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Посмотрите [аналогичный проект](https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala) на языке Scala.
