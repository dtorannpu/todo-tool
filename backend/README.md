# todo-service
ktor練習リポジトリ

## DB
MySQLを使用
```
docker compose  --env-file .env.local up
```


## 認証
Auth0を使用
環境変数に以下の設定が必要

| 環境変数名      | 設定例                         |
|----------------|--------------------------------|
| ISSUER         | https://YOUR_TENANT.auth0.com/ |
| AUTH0_AUDIENCE | https://localhost:8080         |