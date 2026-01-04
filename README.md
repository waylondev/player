# Bilibili Video Player

A modern, high-performance video playback platform built with Kotlin Multiplatform + Compose Multiplatform, supporting multiple platforms with a clean, extensible architecture.

## 🚀 Core Features

### Video Playback
- ✅ **Multi-platform Support**: Android, JVM Desktop, Web (JS)
- ✅ **DASH Format**: Support for separate audio and video streams
- ✅ **Platform-native Players**: ExoPlayer (Android), HTML5 Video (Web), Common Player (Desktop)
- ✅ **Playback Control**: Play/pause with async state management

### Content Features
- ✅ **Home Recommendations**: Async loading with coroutines
- ✅ **Hot Ranking**: Real-time data with optimized API calls
- ✅ **Video Search**: Non-blocking operations
- ✅ **Adaptive Layout**: FlowRow-based responsive design for related videos

### User System
- ✅ **QR Code Login**: Direct Bilibili API integration with real QR code display
- ✅ **State Management**: Clean, structured state handling

## 🏗️ Architecture

### Modular Design
```
player/
├── bilibiliApi/          # API client layer
├── composeApp/          # UI layer (Compose Multiplatform)
└── shared/              # Shared models and interfaces
```

### Technology Stack
- **Language**: Kotlin Multiplatform with Coroutines
- **UI**: Compose Multiplatform (Material3)
- **Networking**: Ktor Client (Async)
- **Serialization**: Kotlinx Serialization
- **Dependency Management**: Gradle Version Catalog

### Clean Architecture Principles
- **SOLID**: Single responsibility, open/closed, interface segregation, dependency inversion
- **Dependency Inversion**: Clear layered architecture with abstractions
- **Non-blocking**: All network operations use suspend functions
- **Resource Safety**: Automatic cleanup with DisposableEffect

## 🛠️ Quick Start

### Environment Requirements
- JDK 21+
- Android SDK (for Android builds)

### Build Commands

#### Android Release APK
```bash
./gradlew :composeApp:assembleRelease
```

#### JVM Desktop
```bash
./gradlew :composeApp:run
```

#### Web (JS)
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
```

## 📱 Platform Details

### Android
- **Video Player**: ExoPlayer 3.x with DASH support
- **UI**: Compose Material3 with adaptive layouts
- **APK Size**: Optimized with ProGuard and resource shrinking

### JVM Desktop
- **Video Player**: Common implementation with Compose Desktop
- **UI**: Material3 design system

### Web (JS)
- **Video Player**: HTML5 Video element
- **Compatibility**: Modern browsers with UTF-8 support

## 🔧 Key Implementation Details

### Video Player Component
```kotlin
// Multiplatform video player with audio support
expect fun VideoPlayerComponent(
    url: String,
    audioUrl: String? = null,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
)
```

### DASH Stream Handling
- **Separate Audio/Video**: MergingMediaSource for combined playback
- **Progressive Loading**: Async stream preparation with ExoPlayer
- **Quality Adaptation**: Automatic resolution handling

### Adaptive Related Videos Layout
- **FlowRow**: Responsive grid layout
- **Consistent Design**: Matches recommendation list appearance
- **Optimized Spacing**: Proper padding and alignment

## 📊 Performance Optimizations

### Resource Management
- **DisposableEffect**: Automatic cleanup of coroutines and players
- **Image Lazy Loading**: Efficient cover image loading
- **State Reuse**: Optimized state object management

### Build Optimization
- **ProGuard**: Code obfuscation and shrinking
- **Resource Compression**: Reduced APK size
- **Dependency Tree**: Optimized library dependencies

## 📄 License

MIT License - See [LICENSE](LICENSE) file

---

A production-ready, multi-platform video playback solution built with modern Kotlin technologies.