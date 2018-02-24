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
