# Bilibili 视频播放器项目

一个现代化的视频播放平台，基于 Bilibili API 开发，提供以下核心功能。

## 核心功能


### 1. 视频推荐
- 获取 Bilibili 推荐视频列表
- 支持无限滚动加载


### 2. 视频搜索
- 根据关键词搜索 Bilibili 视频
- 支持分页加载

### 4. 视频播放
- 使用 HTML5 原生 video 标签
- 支持倍速播放、全屏、弹幕等功能


### 3. 相关视频
- 获取与当前视频相关的视频列表

## 平台支持与编译运行

### Android平台

**编译命令**：
```bash
./gradlew :composeApp:compileDebugKotlinAndroid
```

**运行命令**：
```bash
./gradlew :composeApp:installDebug
```

**使用Android Studio运行**：
1. 使用Android Studio打开项目
2. 连接Android设备或启动模拟器
3. 点击"Run"按钮（绿色三角形）

### JVM平台

**编译命令**：
```bash
./gradlew :composeApp:compileKotlinJvm
```

**运行命令**：
```bash
./gradlew :composeApp:run
```

### JS平台

**编译命令**：
```bash
./gradlew :composeApp:compileKotlinJs
```

**运行命令**：
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
```

**构建生产版本**：
```bash
./gradlew :composeApp:jsBrowserProductionBuild
```

### Web平台

**编译命令**：
```bash
./gradlew :composeApp:compileKotlinWasmJs
```

**运行命令**：
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

## 架构设计

### 设计原则

- **Clean Architecture**：清晰的分层结构，依赖倒置
- **SOLID原则**：单一职责、开放封闭、里氏替换、接口隔离、依赖倒置
- **高性能**：使用平台原生库，避免不必要的中间层
- **高扩展性**：基于KMP架构，易于扩展新平台
- **现代化**：使用最新的Kotlin和Compose Multiplatform技术栈

### 项目结构

```
player/
├── bilibiliApi/          # Bilibili API客户端
├── composeApp/          # Compose Multiplatform应用
│   ├── src/
│   │   ├── commonMain/  # 通用代码
│   │   ├── androidMain/ # Android平台代码
│   │   ├── iosMain/     # iOS平台代码
│   │   ├── jvmMain/     # JVM平台代码
│   │   └── jsMain/      # JS平台代码
├── shared/             # 共享代码库
└── server/             # 服务器端代码
```

## 许可证

MIT
