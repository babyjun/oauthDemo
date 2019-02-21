# oauthDemo
springboot2.0+springSecurity+oauth2+jwt

## jwt优点：
1. 自包含
2. 防篡改
3. 可自定义扩展


## jwt的使用

token生成的其实就是一个UUID，和业务没有丝毫的关系，这样带来最大的问题，就是需要人工持久化处理token（像处理分布式下的sessionId一样，使用redis实现持久化）。但是jwt就不需要，因为自包含，所以token里有身份验证信息，不需要做后台持久化处理，前端每次请求被保护的资源时请求头里带上该token就可以实现。

## springboot2.0 oauth2升级
1、oauth和security合并，pom文件只需要引入
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- 由于一些注解和API从spring security5.0中移除，所以需要导入下面的依赖包  -->
<dependency>
	<groupId>org.springframework.security.oauth.boot</groupId>
	<artifactId>spring-security-oauth2-autoconfigure</artifactId>
	<version>2.0.0.RELEASE</version>
</dependency>
```
2.增加了@EnableWebSecurity配置

