# 学不通 - 学习通课程助手

![版本](https://img.shields.io/badge/版本-1.0.0-blue)
![平台](https://img.shields.io/badge/平台-Android-brightgreen)
![开源协议](https://img.shields.io/badge/协议-MIT-green)

## 📝 项目介绍

"学不通"是一个开源的Android应用程序，旨在帮助学生更高效地完成学习通（超星）平台上的课程学习任务。本项目仅供学习和技术研究使用，请勿用于其他目的。

由于开发者精力有限，现将此项目开源，希望能帮助对Android开发感兴趣的同学学习，也欢迎社区贡献力量共同完善。

## 📱 应用截图

<div align="center">
  <img src="screenshots/login.png" alt="登录界面" width="200"/>
  <img src="screenshots/course_list.png" alt="课程列表" width="200"/>
  <img src="screenshots/video_player.png" alt="视频播放" width="200"/>
  <img src="screenshots/progress.png" alt="学习进度" width="200"/>
</div>

> 注：请在发布前添加实际截图

## ✨ 功能特点

- **QQ快捷登录**：支持通过QQ账号快速登录学习平台
- **视频自动完成**：自动播放并完成视频学习任务
- **PPT自动翻阅**：自动完成PPT课件学习任务
- **智能跳过问题**：自动处理视频中途出现的互动问题
- **学习进度记录**：实时记录和展示学习进度
- **离线缓存**：支持课程内容离线缓存，随时随地学习
- **多账号管理**：支持多账号切换，方便管理不同课程
- **定时任务**：设置定时任务，自动完成学习

## 📥 安装方法

### 方式一：克隆源码

1. 克隆本仓库
   ```bash
   git clone https://github.com/xiaoxiaoguai-yyds/xuebutong-app.git
   ```

2. 使用Android Studio打开项目

3. 配置QQ登录（如需使用）
   - 在[QQ互联开放平台](https://connect.qq.com/)申请开发者资质
   - 获取APP ID和APP KEY

4. 构建APK
   ```bash
   ./gradlew assembleDebug
   ```

5. 在`app/build/outputs/apk/debug/`目录下找到生成的APK文件

## 🚀 使用说明

1. 打开应用，使用学习通账号或QQ账号登录
2. 在主界面选择您需要完成的课程
3. 点击"开始学习"按钮，应用将自动完成相关学习任务
4. 学习过程中请保持应用在前台运行，不要锁屏或切换到其他应用
5. 在设置中可以自定义学习速度、是否自动答题等选项

## ⚙️ 配置QQ登录

如果您需要启用QQ登录功能，请按照以下步骤操作：

1. 前往[QQ互联开放平台](https://connect.qq.com/)注册并申请开发者资质
2. 创建应用，获取APP ID和APP KEY
3. 在AndroidManifest.xml中添加相关权限和配置（具体配置请参考QQ互联开发文档）

## 🛠️ 技术架构

- **基础框架**：
  - 基于Android原生开发
  - 使用Gradle 8.2作为构建工具
  - 采用AndroidX支持库
  - 支持Android 5.0 (API 21)及以上版本

- **主要技术**：
  - MVVM架构设计模式
  - LiveData和ViewModel组件
  - Room数据库实现本地存储
  - Retrofit2+OkHttp3处理网络请求
  - Glide加载图片资源
  - ExoPlayer实现视频播放功能
  - WorkManager处理后台任务

- **第三方集成**：
  - QQ互联SDK实现第三方登录
  - Jsoup解析HTML内容

## 📂 项目结构

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/xuebutong/
│   │   │   ├── api/            # 网络API接口
│   │   │   ├── database/       # 本地数据库
│   │   │   ├── model/          # 数据模型
│   │   │   ├── ui/             # 用户界面
│   │   │   │   ├── login/      # 登录相关
│   │   │   │   ├── course/     # 课程相关
│   │   │   │   ├── player/     # 播放器相关
│   │   │   │   └── settings/   # 设置相关
│   │   │   ├── utils/          # 工具类
│   │   │   └── workers/        # 后台任务
│   │   ├── res/                # 资源文件
│   │   └── AndroidManifest.xml # 应用清单
│   └── test/                   # 单元测试
└── build.gradle                # 模块构建脚本
```

## ❓ 常见问题 (FAQ)

### Q: 应用会收集我的个人信息吗？
A: 本应用仅收集必要的信息用于登录和课程同步，不会收集与功能无关的个人信息，也不会将信息分享给第三方。

### Q: 使用本应用会被学校发现吗？
A: 本应用模拟正常的学习行为，但无法保证100%不被检测。请谨慎使用，风险自负。

### Q: 为什么某些课程无法自动完成？
A: 不同课程平台可能有不同的技术实现，本应用主要针对标准的学习通课程设计，部分特殊课程可能不兼容。

### Q: 如何报告bug或提出建议？
A: 请在GitHub仓库的Issues页面提交问题或建议，我们会尽快回应。

## 🔮 未来计划

- [ ] 本项目永久停更

## ⚠️ 注意事项

- 本应用仅供学习和技术研究使用
- 使用自动化功能可能违反学习平台的使用条款，请谨慎使用
- 开发者不对因使用本应用导致的任何问题负责
- 请尊重知识产权，真正掌握课程知识才是学习的目的
- 定期检查更新，以获取最新功能和安全修复

## 🤝 贡献指南

欢迎对本项目做出贡献！您可以通过以下方式参与：

1. 提交Issue报告bug或提出新功能建议
2. 提交Pull Request改进代码
3. 完善文档
4. 分享您的使用体验

请确保您的代码符合项目的编码规范，并附上必要的测试和文档。

### 开发环境设置

1. 安装Android Studio最新版
2. 安装JDK 8或更高版本
3. 配置Android SDK (API 21及以上)
4. 克隆项目并导入

### 提交PR流程

1. Fork本仓库
2. 创建您的特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交您的更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 打开Pull Request

## 📃 开源协议

本项目采用[MIT许可证](LICENSE)。您可以自由使用、修改和分发本项目，但需保留原作者署名和许可证信息。

## 💖 鸣谢

- 感谢所有为本项目做出贡献的开发者
- 特别感谢Android开源社区提供的各种优秀库和工具
- 感谢所有提供反馈和建议的用户


## 免责声明

**重要提示**：本项目不隶属于、不受官方认可、不由官方赞助，也不与学习通（超星）或腾讯QQ有任何官方关联。所有产品名称、徽标和品牌均为其各自所有者的财产。

**法律声明**：本项目仅用于学习研究，使用者需自行承担使用本应用可能带来的风险和后果。开发者不对因使用本应用导致的任何问题负责。请在遵守相关法律法规和学校规定的前提下使用本软件。

**学习建议**：真正的学习在于理解和掌握知识，而非简单完成任务。希望本工具能够帮助您更好地管理学习时间，而不是逃避学习责任。 
