# Todo Tool

## Auth0のアプリケーション作成
以下のページを参考にRegular Web Applicationを作成する  
https://auth0.com/docs/get-started/auth0-overview/create-applications/regular-web-apps

## Auth0のアプリケーション設定を行う
https://auth0.com/docs/get-started/applications/application-settings#application-uris

Allowed Callback URLs  
http://localhost:3000/api/auth/callback

Allowed Logout URLs  
http://localhost:3000

## .env.exmpleをコピーして.env.localを作成　.env.localを書き換える
書き換え項目

* AUTH0_SECRET
* AUTH0_ISSUER_BASE_URL
* AUTH0_CLIENT_ID
* AUTH0_CLIENT_SECRET
* ISSUER

Auth0の作成したアプリケーションのQuickstartタブを表示すると「Configure the SDK」にサンプルが表示されるのでそれに従って変更  
ISSUERはAUTH0_ISSUER_BASE_URLの末尾に/を付与した値を設定

## 実行
```
docker compose  --env-file .env.local up
```
