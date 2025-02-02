# todo-service

ktor 練習リポジトリ

## DB

MySQL を使用

```
docker compose  --env-file .env.local up
```

## 認証

Auth0 を使用
環境変数に以下の設定が必要

| 環境変数名     | 設定例                         |
| -------------- | ------------------------------ |
| ISSUER         | https://YOUR_TENANT.auth0.com/ |
| AUTH0_AUDIENCE | https://localhost:8080         |
