CREATE TABLE IF NOT EXISTS user_book_status (
  user_id     BIGINT      NOT NULL,
  book_id     BIGINT      NOT NULL,
  status      VARCHAR(20) NOT NULL,
  started_at  TIMESTAMP   NULL,
  finished_at TIMESTAMP   NULL,
  PRIMARY KEY (user_id, book_id)
);

CREATE INDEX IF NOT EXISTS idx_user_book_status_book_id
  ON user_book_status(book_id);

CREATE INDEX IF NOT EXISTS idx_user_book_status_user_id
  ON user_book_status(user_id);
