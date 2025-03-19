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


-- postgres용
CREATE TABLE hun_test.tb_books (
    id SERIAL,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(30) NOT NULL,
    publisher VARCHAR(30),
    publish_dt VARCHAR(10),
    book_details TEXT,
    isbn VARCHAR(20),
    thumb_url VARCHAR(100),
    crt_dt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    udt_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    del_yn CHAR(1) NOT NULL DEFAULT 'N',
    primary key ("id")
);

-- 인덱스 생성
COMMENT ON COLUMN hun_test.tb_books.id IS '도서 관리번호';
COMMENT ON COLUMN hun_test.tb_books.title IS '도서 제목';
COMMENT ON COLUMN hun_test.tb_books.author IS '저자';
COMMENT ON COLUMN hun_test.tb_books.publisher IS '출판사';
COMMENT ON COLUMN hun_test.tb_books.publish_dt IS '출간일';
COMMENT ON COLUMN hun_test.tb_books.book_details IS '도서 상세';
COMMENT ON COLUMN hun_test.tb_books.isbn IS 'ISBN 정보';
COMMENT ON COLUMN hun_test.tb_books.thumb_url IS '썸네일 경로';
COMMENT ON COLUMN hun_test.tb_books.crt_dt IS '등록 일자';
COMMENT ON COLUMN hun_test.tb_books.udt_dt IS '수정 일자';
COMMENT ON COLUMN hun_test.tb_books.del_yn IS '삭제여부';

CREATE TABLE hun_test.tb_books_log (
	log_seq SERIAL,
	id INT NOT NULL,
	"type" CHAR(1) NOT NULL DEFAULT 'I',
	books_amount INT NOT NULL,
	log_dt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key("log_seq")
);

COMMENT ON COLUMN tb_books_log.log_seq IS '로그 관리번호';
COMMENT ON COLUMN tb_books_log.id IS '도서 관리번호';
COMMENT ON COLUMN tb_books_log.type IS '로그타입(I:입고, O:출고, S:판매, R:환불, M:수정)';
COMMENT ON COLUMN tb_books_log.books_amount IS '변동 수량';
COMMENT ON COLUMN tb_books_log.log_dt IS '판매일자';

CREATE table hun_test.tb_file_mng (
	file_seq SERIAL,
	origin_file_nm varchar(100) NOT NULL,
	file_path varchar(200) NOT NULL,
	file_ext varchar(10) NOT NULL,
	file_size INT NOT NULL,
	file_type varchar(30) NOT NULL,
	rel_table varchar(30) NOT NULL,
	rel_column varchar(30) NOT NULL,
	rel_pk varchar(10) NOT NULL,
	del_yn CHAR(1) NOT NULL DEFAULT 'N',
	crt_dt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	udt_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	primary key ("file_seq")
);

COMMENT ON COLUMN hun_test.tb_file_mng.file_seq IS '파일 관리번호';
COMMENT ON COLUMN hun_test.tb_file_mng.origin_file_nm IS '원본 파일명';
COMMENT ON COLUMN hun_test.tb_file_mng.file_path IS '파일 저장경로';
COMMENT ON COLUMN hun_test.tb_file_mng.file_ext IS '파일 확장자';
COMMENT ON COLUMN hun_test.tb_file_mng.file_size IS '파일 용량';
COMMENT ON COLUMN hun_test.tb_file_mng.file_type IS '파일 타입';
COMMENT ON COLUMN hun_test.tb_file_mng.rel_table IS '연관 테이블';
COMMENT ON COLUMN hun_test.tb_file_mng.rel_column IS '연관 칼럼명';
COMMENT ON COLUMN hun_test.tb_file_mng.rel_pk IS '연관 PK값';
COMMENT ON COLUMN hun_test.tb_file_mng.del_yn IS '삭제여부';
COMMENT ON COLUMN hun_test.tb_file_mng.crt_dt IS '파일저장일자';
COMMENT ON COLUMN hun_test.tb_file_mng.udt_dt IS '수정 일자';
