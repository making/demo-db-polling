```
docker-compose up
```


```
psql postgresql://demo:demo@localhost:5432/demo -c "insert into usage(first_name, last_name, minutes, data_usage) values('John', 'Doe', 60, 1000)"
psql postgresql://demo:demo@localhost:5432/demo -c "insert into usage(first_name, last_name, minutes, data_usage) values('Jone', 'Doe', 120, 2200)"
psql postgresql://demo:demo@localhost:5432/demo -c "insert into usage(first_name, last_name, minutes, data_usage) values('Richard', 'Roe', 50, 800)"

psql postgresql://demo:demo@localhost:5432/demo -c "select * from usage"
```