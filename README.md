# issue-tracker
  
#### 팀원소개
|Name|Part|Github|
|---|---|---|
|Stark|android|[jminie-o8o](https://github.com/jminie-o8o)|
|Josh|android|[junseokseo9306](https://github.com/junseokseo9306)|
|Lee|backend|[street62](https://github.com/street62)|

## Ground Roll

리포지토리 내 위키에 아래와 같이 그라운드 롤을 정리했습니다.
- [Ground Roll 바로가기](https://github.com/jminie-o8o/issue-tracker/wiki/%EA%B7%B8%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%A1%A4)
- [Android Convention Roll 바로가기](https://github.com/jminie-o8o/issue-tracker/wiki/Android)


##  기능
- 일반 회원가입 및 깃허브 OAuth 로그인
- 이슈 추가/삭제
- 필터로 열린이슈, 닫힌이슈, 레이블, 마일스톤에 따른 분류
- 키워드로 이슈 검색
- 레이블 추가
- 마일스톤 추가

## 안드로이드 기술 스택
- Architecture
  - MVVM Architecture ( View - Databinding - ViewModel - Model )
- 100% [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://developer.android.com/kotlin/coroutines) + [Flow](https://developer.android.com/kotlin/flow)
- [Jetpack](https://developer.android.com/jetpack)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAjwq5-WBhB7EiwAl-HEkrzYCgxFBbYLSC4yenlZRy5NtxWbTHP-xThSz_yMY_JUTl3TCklhnBoCDIcQAvD_BwE&gclsrc=aw.ds)
  - [Navigation](https://developer.android.com/guide/navigation)
- REST API 통신을 위한 [Retrofit](https://square.github.io/retrofit/)
- DI를 위한 [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- 상태 처리 및 에러 핸들링을 위한 [CEH](https://kotlinlang.org/docs/exception-handling.html#coroutineexceptionhandler)
- RecyclerView 스와이프 삭제를 위한 [ItemTouchHelper](https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper.Callback)
- JWT 앱 전역 보관 및 okHttp Header 추가를 위한 [SharedPreference](https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper.Callback)


| 회원가입 및 로그인  | GitHub OAuth 로그인 | 이슈 추가 | 이슈 닫기 |
|:--------:|:--------:|:--------:|:--------:|
| <img src=https://user-images.githubusercontent.com/79504043/183874034-4253dc6c-7c72-4e4a-a0f5-2df3a06d2960.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183874953-b8c4ee09-9dec-4d38-9e9f-f4b94d18e7a7.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183877513-b1838be6-5e5a-4462-b437-1a1835411447.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183875847-1b086c37-3289-4f0a-8b12-aadf7d1731d6.gif width=200> |

| 이슈 필터 | 이슈 검색 | 레이블 추가 | 마일스톤 추가 |
|:--------:|:--------:|:--------:|:--------:|
| <img src=https://user-images.githubusercontent.com/79504043/183876156-8cc0684f-181c-4ed8-a021-ddcba04f0a43.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183877330-6bd6b04e-833c-40f5-b03c-399ac53ed2c7.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/177966815-7abcda81-aca1-487d-bd36-847e304eacf8.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183877589-8ac92486-da0d-4d5f-adad-c4add2c336b3.gif width=200> |

## MAD Scorecard
<img src="https://user-images.githubusercontent.com/79504043/177989379-37cf40dc-0e11-4944-a1a9-8a3ee113065b.png">  

