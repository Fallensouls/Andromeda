# 后端项目说明


------

本项目基于springboot构建

------
## 现阶段完成的服务
> * MailService：利用spring-boot-mail实现的发送邮件服务
> * Gateway：使用zuul实现的网关服务，处理跨域请求，并将请求转发至内部服务
> * Eureka：服务注册与发现，在线服务均注册于服务注册中心，使得其他服务可以发现并调用它
> * AuthService：利用spring security以及JWT完成的注册、登录及权限管理服务