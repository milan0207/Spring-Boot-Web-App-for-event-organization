futtatas elott Redis szerver inditasa:
``` docker run -d -p 6379:6379 --name redis redis```

Testing redis connection (answer should be PONG):
``` docker exec -it redis redis-cli ping```
```


PLuszok megcsinalva:
 * Pageable
 * cache
 * filter
 * task
 * interceptor