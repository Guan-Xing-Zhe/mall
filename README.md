# mall — 电商系统深度分析与二次开发

> 本仓库 Fork 自 [macrozheng/mall](https://github.com/macrozheng/mall)（83k+ Stars），
> 旨在深入剖析电商系统架构，并基于此进行二次开发与功能增强。

---

## 📋 项目概述

mall 是一套**电商全栈项目**，基于 Spring Boot + MyBatis 实现，采用 Docker 容器化部署。涵盖前台商城系统和后台管理系统两大模块，是 Java 后端开发者学习企业级项目的最佳实战案例之一。

### 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot + Spring Security + MyBatis |
| 数据库 | MySQL + Redis + Elasticsearch |
| 中间件 | RabbitMQ + RocketMQ + MongoDB |
| 部署 | Docker + Jenkins + Nginx |
| 前端 | Vue.js + Element UI |

### 功能模块

`
mall
├── mall-admin       # 后台管理系统（商品管理、订单管理、权限管理等）
├── mall-search      # 搜索系统（基于 Elasticsearch）
├── mall-portal      # 前台商城系统（首页、商品、购物车、订单等）
└── mall-common      # 公共模块
`

---

## 🔍 源码分析

### 核心架构设计

- **分层架构**：Controller → Service → DAO 三层解耦
- **统一响应**：通用返回结果 CommonResult + 全局异常处理
- **JWT 鉴权**：Spring Security + JWT Token 无状态认证
- **缓存策略**：Redis 缓存热点数据，Canal 同步 MySQL → Redis
- **搜索架构**：Elasticsearch 商品搜索，Kibana 可视化

### 亮点功能

- [x] 商品管理（SPU/SKU 体系）
- [x] 秒杀系统（Redis + RabbitMQ 削峰）
- [x] 订单超时取消（延迟队列实现）
- [x] 权限管理（RBAC 模型）
- [x] 分布式事务（RocketMQ 事务消息）

---

## 🚀 快速部署

`ash
# 1. 克隆项目
git clone https://github.com/Guan-Xing-Zhe/mall.git

# 2. Docker 一键部署（推荐）
docker-compose up -d

# 3. 访问地址
# 后台: http://localhost:8080
# 前台: http://localhost:8200
`

> 详细部署文档请参考 [deploy-guide.md](docs/deploy-guide.md)

---

## 📚 学习笔记

- [架构设计文档](docs/architecture.md)
- [数据库设计分析](docs/database.md)
- [性能优化实践](docs/performance.md)

---

## 🔗 原项目

- 原作者: [macrozheng](https://github.com/macrozheng)
- 原仓库: [macrozheng/mall](https://github.com/macrozheng/mall)
- 在线演示: [http://mall.macrozheng.com](http://mall.macrozheng.com)

---

> ⭐ 如果这个项目对你有帮助，欢迎 Star 支持！
> 本仓库仅用于学习研究，请尊重原项目 License。
---

## ✨ 我的二次开发

### 1. 促销日期计算器 (DateCalculatorController)

电商业务中经常需要计算促销周期、工作日和剩余时间。新增了一套日期计算工具接口：

| 接口 | 说明 | 示例 |
|------|------|------|
| GET /dateCalc/workdays | 计算两个日期之间的工作日天数 | 用于物流发货周期估算 |
| GET /dateCalc/promotion-remaining | 计算促销剩余时间 | 倒计时展示 |
| GET /dateCalc/marketing-calendar | 获取当前营销日历 | 季度/月份信息 |

> 代码位置: mall-demo/src/main/java/com/macro/mall/demo/controller/DateCalculatorController.java
> 配套测试: mall-demo/src/test/java/com/macro/mall/demo/service/impl/DateCalculatorServiceImplTest.java

### 2. SystemDiagnosisController (ruoyi-vue-pro)

JVM 运行时监控接口，实时查看堆内存、线程状态、系统负载等信息。支持 Spring Security 权限控制，仅管理员可访问。

> 代码位置: yudao-module-system/.../controller/admin/logger/SystemDiagnosisController.java
