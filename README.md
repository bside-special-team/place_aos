# place_aos(가칭)

# Architecture
![img.png](img.png)
```
app/            -> UI 레이어
buildSrc/       -> 안드로이드 버전정보, dependency 정보
data/           -> Android Library Based, Presenter 레이어, UseCases 레이어 - Repository, Usecase, Room, ViewModel 등 작성 하는 곳.
domain/         -> Entities 레이어, 추상화된 DataSource, entity, enum 데이터 작성 하는 곳.
mock/           -> 서버 API 연동 전 더미 데이터 작성 하는 곳.
remote/         -> Conttollers 레이어, 서버 API 연동 하는 곳.
```