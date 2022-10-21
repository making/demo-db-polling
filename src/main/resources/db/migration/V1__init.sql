CREATE TABLE usage
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    minutes    INTEGER,
    data_usage INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    seen_at    TIMESTAMP WITH TIME ZONE
);

CREATE INDEX usage_seen_at ON usage (seen_at);
