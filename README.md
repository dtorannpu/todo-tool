# Todo Tool

## Auth0 のアプリケーション作成

以下のページを参考に Regular Web Application を作成する  
https://auth0.com/docs/get-started/auth0-overview/create-applications/regular-web-apps

## Auth0 のアプリケーション設定を行う

https://auth0.com/docs/get-started/applications/application-settings#application-uris

Allowed Callback URLs  
http://localhost:3000/api/auth/callback

Allowed Logout URLs  
http://localhost:3000

## .env.exmple をコピーして.env.local を作成　.env.local を書き換える

書き換え項目

- AUTH0_SECRET
- AUTH0_ISSUER_BASE_URL
- AUTH0_CLIENT_ID
- AUTH0_CLIENT_SECRET
- AUTH0_AUDIENCE
- APP_BASE_URL
- ISSUER

Auth0 の作成したアプリケーションの Quickstart タブを表示すると「Configure the SDK」にサンプルが表示されるのでそれに従って変更  
ISSUER は AUTH0_ISSUER_BASE_URL の末尾に/を付与した値を設定

## 実行

```
docker compose  --env-file .env.local up
```
