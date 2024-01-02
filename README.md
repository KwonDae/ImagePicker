This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

# ToDo
- [x] 프로젝트에 필요한 라이브러리 의존성 추가
- [x] 이미지 피커 UI 구성
- [ ] 공통 로직 작성(expect)
- [ ] Android 이미지 피커 로직 구현(actual)
- [ ] iOS 이미지 피커 로직 구현(actual)
- [ ] [TouchLab SKIE](https://skie.touchlab.co/) 라이브러리 적용(CommonFlow 를 대체)
- [ ] [moko-resources](https://github.com/icerockdev/moko-resources) 라이브러리 적용(optional)

# Article
- [[Compose Multiplatform] Coil 을 이용한 Network Image Load](https://velog.io/@mraz3068/How-to-load-Network-Image-by-Coil-in-Compose-Multiplatform)
