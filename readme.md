# campus-book-trade —— 校园二手书交易平台

> 基于 Spring Boot + MyBatis-Plus + JWT 开发的前后端分离校园二手书交易系统，实现书籍发布、购买、订单管理、用户/管理员权限控制等完整业务流程。

---

## 📌 项目简介
本项目是面向校园场景的二手书交易平台，为学生提供闲置书籍发布、购买、订单管理的一站式服务，同时为管理员提供用户与书籍管理功能，支持双角色权限控制，解决校园内二手书流转不透明、交易无保障的问题。

---

## 🛠️ 技术栈

### 后端
- Java
- Spring Boot
- MyBatis-Plus
- JWT（JSON Web Token）
- MySQL
- Maven

### 前端
- HTML / CSS / JavaScript
- jQuery / Ajax（静态页面交互）

---

## ✨ 核心功能

### 用户模块
- 用户注册、登录（JWT 身份认证）
- 个人信息管理
- 普通用户/管理员双角色权限控制

### 书籍模块
- 书籍发布、编辑、删除
- 书籍列表展示、查询
- 书籍状态管理（可售/已售）

### 订单模块
- 创建订单、订单状态更新
- 我的订单查询与管理

### 管理员模块
- 用户信息管理
- 书籍管理与审核
- 订单数据查看

---


### 📁 项目结构
```text
campus-book-trade/
├── src/
│   ├── main/
│   │   ├── java/com/whxy/campusbooktrade2/
│   │   │   ├── common/              # 通用模块（全局异常处理、统一结果封装）
│   │   │   ├── config/              # 配置类（拦截器、MyBatis-Plus、Web配置）
│   │   │   ├── controller/          # 接口层（用户、书籍、订单、管理员）
│   │   │   ├── entity/              # 实体类（Book、OrderInfo、User）
│   │   │   ├── mapper/              # 数据访问层
│   │   │   ├── service/             # 业务层（接口与实现）
│   │   │   ├── util/                # 工具类（JWT工具）
│   │   │   ├── vo/                  # 视图对象（OrderVo）
│   │   │   └── CampusBookTrade2Application.java  # 启动类
│   │   └── resources/
│   │       ├── static/              # 前端静态页面
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── index.html
│   │       │   ├── myOrder.html
│   │       │   ├── adminBookList.html
│   │       │   └── adminUserList.html
│   │       └── application.yml       # 配置文件（已脱敏）
├── pom.xml                          # Maven依赖
└── README.md                        # 项目说明
```
## ⚠️ 重要说明
配置文件已脱敏，本地运行需自行修改 application.yml 中的数据库连接信息、端口配置等敏感参数，方可正常启动项目。

## 🚀 快速启动
1. 环境准备：JDK 1.8+、MySQL 8.0+、Maven 3.6+
2. 创建数据库，执行SQL脚本创建数据表
3. 修改 application.yml 中的数据库配置
4. 使用Maven安装依赖，启动主类 CampusBookTrade2Application.java
5. 访问地址：http://localhost:8080

## 📈项目亮点
- 前后端分离架构，接口规范清晰
- JWT无状态身份认证，保证接口安全
- 统一结果封装与全局异常处理
- MyBatis-Plus简化CRUD开发
- 双角色权限控制，业务流程完整
- 系统稳定可演示

## 📄许可证
本项目仅用于学习交流，未经允许不得用于商业用途。

## 📬联系方式
作者：倪天成
邮箱：2787322345@qq.com