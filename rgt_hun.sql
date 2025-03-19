CREATE TABLE rgt_hun.tb_books (
	id INT auto_increment NOT NULL COMMENT '도서 관리번호',
	title varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '도서 제목',
	author varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '저자',
	publisher varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '출판사',
	publish_dt varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '출간일',
	book_details TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '도서 상세',
	isbn varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT 'ISBN 정보',
	thumb_url varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '썸네일 경로',
	crt_dt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일자',
	udt_dt DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '수정 일자',
	del_yn CHAR(1) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
	CONSTRAINT tb_books_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE rgt_hun.tb_books_log (
	log_seq INT auto_increment NOT NULL COMMENT '로그 관리번호',
	id INT NOT NULL COMMENT '도서 관리번호',
	`type` ENUM('I', 'O', 'S', 'R', 'M') NOT NULL DEFAULT 'I' COMMENT '로그타입(I:입고, O:출고, S:판매, R:환불, M:수정)'
	books_amount INT NOT NULL COMMENT '변동 수량',
	log_dt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '판매일자',
	CONSTRAINT tb_books_log_pk PRIMARY KEY(log_seq)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE table rgt_hun.tb_file_mng (
	file_seq INT auto_increment NOT NULL COMMENT '파일 관리번호',
	origin_file_nm varchar(100) NOT NULL COMMENT '원본 파일명',
	file_path varchar(200) NOT NULL COMMENT '파일 저장경로',
	file_ext varchar(10) NOT NULL COMMENT '파일 확장자',
	file_size INT NOT NULL COMMENT '파일 용량',
	relation_table varchar(30) NOT NULL COMMENT '연관 테이블',
	relation_column varchar(30) NOT NULL COMMENT '연관 칼럼명',
	relation_pk varchar(10) NOT NULL COMMENT '연관 PK값',
	del_yn CHAR(1) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
	crt_dt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '파일저장일자',
	udt_dt DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '수정 일자',
	CONSTRAINT tb_file_mng_pk PRIMARY KEY(file_seq)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
