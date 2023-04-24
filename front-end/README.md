# 前端界面设计

这是一个使用 `Vue 3` 和 `Vite` 开发的前端项目，其中使用了 `pnmp` 作为包管理器，`axios` 作为 `HTTP` 客户端，并支持 `JavaScript` 和 `TypeScript` 两种开发语言。

## 推荐的 IDE 设置

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar)（并禁用 Vetur）+ [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin)。

## 项目架构

| 项目        | 工具                    |
| ----------- | ----------------------- |
| 开发框架    | Vue3 + Vite             |
| 包管理器    | pnmp                    |
| HTTP 客户端 | axios                   |
| 开发语言    | JavaScript + TypeScript |

## TS 对.vue 导入的类型支持

TypeScript 默认无法处理.vue 导入的类型信息，因此我们使用`vue-tsc`替换`tsc` CLI 进行类型检查。在编辑器中，我们需要[TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin)让 TypeScript 语言服务认识.vue 类型。

如果独立的 TypeScript 插件速度不够快，Volar 还实现了一种[Take Over Mode](https://github.com/johnsoncodehk/volar/discussions/471#discussioncomment-1361669)，它更加高效。您可以通过以下步骤启用它：

1. 禁用内置的 TypeScript 扩展
   1. 从 VSCode 的命令面板中运行`Extensions: Show Built-in Extensions`
   2. 找到`TypeScript and JavaScript Language Features`，右键单击并选择`Disable (Workspace)`
2. 运行`Developer: Reload Window`从命令面板重新加载 VSCode 窗口。

## 自定义配置

请参阅[Vite 配置参考](https://vitejs.dev/config/)。

## 项目设置

```sh
pnpm install
```

### 编译和热重载以进行开发

```sh
pnpm run dev
```

### 用于生产的类型检查，编译和缩小

```sh
pnpm run build
```
