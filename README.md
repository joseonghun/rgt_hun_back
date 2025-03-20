# rgt_hun_back

1) 윈도우 및 Ubuntu 22.04 환경에서 테스트 진행
2) 개별 테스트시 업로드 경로의 수정이 필요할 경우 src/main/resources/application.properties의 upload.dir 경로 수정

# 구현내용
1) 기본적인 CRUD 기능 구현
2) 정보 추가/수정/삭제시 Log테이블 구현
3) 썸네일 이미지 업로드 구현(배포된 서버에서는 쓰기권한 불가 이슈로 업로드 불가)
4) MySQL로 구현했다가 호스팅서버 이슈로 PostgreSQL로 변경
