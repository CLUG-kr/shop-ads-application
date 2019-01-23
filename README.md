# shop-ads-application
 This repository is for the store's grocery advertisement. 
 This code is based on Android version 6.0 or later, and Python version python 3.5 or later.
 
## Function of application (location: Korea)
 - Advertise event products by item.
 - Based on the map, it advertises the event goods of the nearby shops.
 - It informs the event of the shop.
 - General users of apps and shop owners will use this apps
 - Store owners can apply for advertising through this app.
 

## Required library
### python
 - flask
 
## 프로젝트 진행상황 ~ 01/23
* 서버 (AWS & Python Flask)
  * 로그인 - 오너 회원가입, 일반 회원가입, 로그인
  * 서비스 - gs25 cu 행사 데이터 크롤링 후 업로드<p>
* 안드로이드
  * Layout - 하단 네비게이션바, 상단 툴바 등 대략적인 Layout 작성
  * 서버와 연동 - 서버에서 받은 Data를 바탕으로 Card View 형식으로 데이터 
 
## 앞으로의 계획 01/23 ~ 
* 서버 (AWS & Python Flask)
  * 서비스 - 다양한 마트의 행사정보와 데이터를 품목별로 정리하는 작업 필요
  * 제품사진 - 행사제품 사진 준비<p>
* 안드로이드
  * 로그인 - 점주들의 광고를 위한 로그인 기능 구현 및 광고 관련 기능 구현
  * 지도 API - 근처 마트의 행사정보를 알려주기 위한 지도 구현
