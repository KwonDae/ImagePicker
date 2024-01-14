# Compose Multiplatform ImagePicker

### Android
|메인 화면|상세 화면|
|:-----:|:-----:|
|<img width="240" src="https://github.com/KwonDae/ImagePicker/assets/51016231/8d7ef240-1b2a-4cc1-bae4-251f741c91e9">|<img width="240" src="https://github.com/KwonDae/ImagePicker/assets/51016231/e5197e4a-e9cc-4699-a2dd-4503bf320930">|

### iOS
|메인 화면|상세 화면|다국어 지원|
|:-----:|:-----:|:-----:|
|<img width="240" src="https://github.com/KwonDae/ImagePicker/assets/51016231/ee9f0b59-f8d4-4afb-8f23-ade3033a6ec2">|<img width="240" src="https://github.com/KwonDae/ImagePicker/assets/51016231/2dcca5fb-b86c-4765-a239-43c0680d55f6">|<img width="240" src="https://github.com/KwonDae/ImagePicker/assets/51016231/7ceb6ad9-657d-44fc-9de3-7dd1ed1e351f">|

## Article
- [빌표 자료](https://speakerdeck.com/easyhooon/compose-multiplatform-image-picker)
- [[Compose Multiplatform] Coil 을 이용한 Network Image Load](https://velog.io/@mraz3068/How-to-load-Network-Image-by-Coil-in-Compose-Multiplatform)
- [[Compose Multiplatform] moko-resources 를 이용하여 font 적용하기](https://velog.io/@mraz3068/Compose-multiplatform-apply-font-by-moko-resources)
- [[Compose Multiplatform] moko-resources 라이브러리를 통해 국제화(i18n) 적용하기](https://velog.io/@mraz3068/Compose-Multiplatform-i18n-by-moko-resources)
- [[Compose Multiplatform] Decompose 라이브러리를 통해 Navigation 구현하기](https://velog.io/@mraz3068/Compose-Multiplatform-Implement-Navigation-With-Decompose)

## Development

### Environment

- IDE : Android Studio Hedgehog, Xcode

### Language

- Kotlin, Kotlin Native

### Libraries

- AndroidX
  - Activity Compose

- Kotlin Libraries (Coroutine, Serialization)
- Compose
  - Material3

- Coil
- Ktor
- Decompose
- moko-resources
- Napier
- [zoomable](https://github.com/mxalbert1996/Zoomable)

#### Gradle Dependency

- Gradle Version Catalog

## ToDo
- [x] 프로젝트에 필요한 라이브러리 의존성 추가
- [x] 이미지 피커 UI 구성
- [x] 공통 로직 작성(expect)
- [x] Android 이미지 피커 로직 구현(actual)
- [x] iOS 이미지 피커 로직 구현(actual)
- [x] [moko-resources](https://github.com/icerockdev/moko-resources) 라이브러리 적용(Font, String(i18n))
- [x] [Decompose](https://github.com/arkivanov/Decompose) 라이브러리 적용(Navigation 및 ViewModel)
- [ ] [TouchLab SKIE](https://skie.touchlab.co/) 라이브러리 적용(CommonFlow 를 대체) -> flow 를 사용하지 않음 


## About
This is a Kotlin Multiplatform project targeting Android, iOS.
* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
