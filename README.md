
## Local Dev

```
docker-compose up
```


```
psql postgresql://demo:demo@localhost:5432/demo -c "insert into usage(first_name, last_name, minutes, data_usage) values('John', 'Doe', 60, 1000)"
psql postgresql://demo:demo@localhost:5432/demo -c "insert into usage(first_name, last_name, minutes, data_usage) values('Jone', 'Doe', 120, 2200)"
psql postgresql://demo:demo@localhost:5432/demo -c "insert into usage(first_name, last_name, minutes, data_usage) values('Richard', 'Roe', 50, 800)"

psql postgresql://demo:demo@localhost:5432/demo -c "select * from usage"
```

## Deploy to Tanzu Application Platform

Deploy PostgreSQL first
```
kubectl apply -f https://github.com/making/demo-db-polling/raw/main/config/usage-db.yaml
```


Then deploy the workload

```
kubectl apply -f https://github.com/making/demo-db-polling/raw/main/config/workload.yaml
```

or 

```
tanzu apps workload apply demo-polling \
  --app demo-polling \
  --type worker \
  --git-repo https://github.com/making/demo-db-polling \
  --git-branch main \
  --build-env BP_JVM_VERSION=17 \
  --request-memory=768Mi \
  --limit-memory=768Mi \
  --service-ref=usage-db=services.apps.tanzu.vmware.com/v1alpha1:ResourceClaim:usage-db
```

```
kubectl exec $(kubectl get pod -l app.kubernetes.io/part-of=usage-db -o jsonpath='{.items[0].metadata.name}') -- psql postgresql://usage:usage@usage-db:5432/usage -c "insert into usage(first_name, last_name, minutes, data_usage) values('John', 'Doe', 60, 1000)"
kubectl exec $(kubectl get pod -l app.kubernetes.io/part-of=usage-db -o jsonpath='{.items[0].metadata.name}') -- psql postgresql://usage:usage@usage-db:5432/usage -c "insert into usage(first_name, last_name, minutes, data_usage) values('Jone', 'Doe', 120, 2200)"
kubectl exec $(kubectl get pod -l app.kubernetes.io/part-of=usage-db -o jsonpath='{.items[0].metadata.name}') -- psql postgresql://usage:usage@usage-db:5432/usage -c "insert into usage(first_name, last_name, minutes, data_usage) values('Richard', 'Roe', 50, 800)"
kubectl exec $(kubectl get pod -l app.kubernetes.io/part-of=usage-db -o jsonpath='{.items[0].metadata.name}') -- psql postgresql://usage:usage@usage-db:5432/usage -c "select * from usage"
```