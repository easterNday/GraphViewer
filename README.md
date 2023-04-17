# 图谱显示

本项目用于显示图谱，并在一个 PDF 文件中设定对应的页面和图谱对应程序，并可以执行点击查询信息。

## 0. 数据库

- 用户数据库

  - 相关内容存储在 [Database/MySQL](./Database/MySQL)，数据库采用 `MySQL8.0` 存储。

- 图谱数据库

  - 相关内容存储在 [Database/Neo4j](./Database/Neo4j)，数据库采用 `neo4j` 存储。

## 1. 前端

前端文件位于 [front-end/GraphView](./front-end/GraphView/) 文件夹内。

### 项目架构

- 开发框架： `Vue3` + `Vite`
- 包管理器： `pnpm`
- HTTP 客户端： `axios`
- 开发语言： `JavaScript` + `TypeScript`

## 2. 后端

后端文件位于 [backend/GraphViewer](./backend/GraphViewer/) 文件夹内。

### 项目架构

- Java 版本： `19`
- 框架： `Spring Boot`
- 文件打包格式： `Jar`
- 包管理器： `Maven`
