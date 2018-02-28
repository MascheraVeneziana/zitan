以下のプロパティファイルを自分の環境の内容に変更します。
```
server/src/main/resources/application.properties
```

以下を実行すると、サーバーが稼働を始めます。
```
org.mascheraveneziana.zitan.Application
```

## lombokについて
lombokを使えるようにするには、以下の場所のjarをダブルクリックしてください。
```
.m2/repository/org/projectlombok/lombok/{バージョン}
```
インストール画面が表示され「IDEs」に入れる対象が表示されるので、OKであれば「Install/Update」をクリックします  
「IDEs」になければ「Specify location」から選択して追加してください  
インストール後、STS.iniの「-javaagent」にlombok.jarが追加されていることを確認してください      
## H2の表示の仕方  
WEBブラウザ表示URLの後ろに/consoleをつけることで表示されます  
application.propertiesに記載されていusernameとpasswordでログインできます  
もし出来なかった場合は、server直下のdb内データをバックアップを取ってから削除してみて下さい。
## クライアント情報の登録
クライアント情報を以下プロパティファイルに入力する必要があります。
```
server/src/main/respurces/application.properties
```
クライアント情報の取得は以下の通りです。
1. Google Cloud Ptatformのコンソール画面を表示します。
1. 左メニューの［APIとサポート］にマウスオーバーします。
1. 出現するメニューの［認証情報］をクリックします。
1. ［認証情報を作成］ボタンをクリックします。
1. 出現するメニューの［OAuthクライアントID］をクリックします。
1. ［ウェブアプリケーション］をクリックします。
1. ［名前］に任意の内容を入力します。
1. ［承認済みのJavaScript生成元］に`http://localhost:{port no}`を入力します。
1. ［承認済みのリダイレクトURI］に`http://localhost:{port no}/login/oauth2/code/google`を入力します。
1. ［作成］ボタンをクリックします。
1. クライアントIDとクライアントシークレットが表示されますので、これらをプロパティファイルに反映します。
