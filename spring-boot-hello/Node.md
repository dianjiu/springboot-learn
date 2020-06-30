# spring-boot-hello 学习笔记

## 01、如何依赖夫工程
1. 在父工程pom文件中配置spring-boot-starter-parent依赖
2. 在hello工程的pom文件中引入父工程的spring boot依赖
3. 在hello工程中pom文件中引入spring-boot-starter-web
    （会自动加载父工程中spring boot的版本信息）

## 02、packaging的三种形式
1. jar：默认的打包方式，打包成jar用作jar包使用。
2. war：将会打包成war，发布在服务器上，如网站或服务。
3. pom：用在父级工程或聚合工程中，用来做jar包的版本控制，
必须指明这个聚合工程的打包方式为pom。

## 03、modules管理子项目
module就是模块，而pom.xml中的modules也正是这个意思，
用来管理同个项目中的各个模块；如果maven用的比较简单，
或者说项目的模块在pom.xml没进行划分，那么此元素是用不到的；
不过一般大一点的项目是要用到的。总之：modules就是配置聚合的载体，
所有需要聚合的模块都通过modules配置，方便按顺序统一构建。

## 04、properties管理依赖版本
通过<properties>元素用户可以自定义一个或多个Maven属性，
然后在POM的其他地方使用${属性名}的方式引用该属性，这种做法的最大意义在于消除重复和统一管理。
Maven总共有6类属性，内置属性、POM属性、自定义属性、Settings属性、java系统属性和环境变量属性；
例如：自定义属性，在properties中定义版本号<xxx.version>4.7</xxx.version>，在dependency中通过>${xxx.version}引用。

## 05、认识dependencyManagement
dependencyManagement只是声明依赖，并不会自动引入依赖；
在子项目中dependencies的dependency才会真实的引入依赖。

## 06、认识pluginManagement
pluginManagement 下的 plugins 下的 plugin 则仅仅是一种声明，
子项目中可以对 pluginManagement 下的 plugin 进行信息的选择、继承、覆盖等

## 07、认识@Controller
以前在编写Controller方法的时候，需要开发者自定义一个Controller类实现Controller接口，实现handleRequest方法返回ModelAndView。并且需要在Spring配置文件中配置Handle，将某个接口与自定义Controller类做映射。

这么做有个复杂的地方在于，一个自定义的Controller类智能处理一个单一请求。而在采用@Contoller注解的方式，可以使接口的定义更加简单，将@Controller标记在某个类上，配合@RequestMapping注解，可以在一个类中定义多个接口，这样使用起来更加灵活。

被@Controller标记的类实际上就是个SpringMVC Controller对象，它是一个控制器类，而@Contoller注解在org.springframework.stereotype包下。其中被@RequestMapping标记的方法会被分发处理器扫描识别，将不同的请求分发到对应的接口上。


## 08、认识@RestController
@RestController是一个组合注解，在spring4版本后出现。它的功能，是可以由@Controller和@ResponseBody搭配代替的。引用shuaiflying的博客：它的功能就是

使用@Controller 注解，在对应的方法上，视图解析器可以解析return 的jsp,html页面，并且跳转到相应页面
若返回json等内容到页面，则需要加@ResponseBody注解


## 09、认识@RequestMapping
RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
RequestMapping注解有六个属性（分成三类进行说明）与六个基本用法:
- value：指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）；
- method：指定请求的method类型， GET、POST、PUT、DELETE等；
- consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
- produces:    指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
- params： 指定request中必须包含某些参数值是，才让该方法处理。
- headers： 指定request中必须包含某些指定的header值，才能让该方法处理请求。

## 10、认识@GetMapping和@PostMapping
@GetMapping和@PostMapping都是一个复合注解，Spring framework 4.3引入了@RequestMapping注释的变体，以更好地表示带注释的方法的语义。
- @GetMapping相当于@RequestMapping(method = RequestMethod.GET)
- @PostMapping相当于@RequestMapping(method = RequestMethod.POST)

## 11、认识注入参数的三个注解
网上找了一个图片，有时候图片更胜于语言表达！
[图片地址](https://images2018.cnblogs.com/blog/602164/201809/602164-20180907153055007-849088541.png)
![Image text](../data/imgs/hello-001.png)

## 12、如何启动访问hello工程
编写一个Controller,然后右键运行Application启动类，
启动后浏览器访问http://localhost:8080/hello
