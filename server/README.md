以下のプロパティファイルを自分の環境の内容に変更します。
```
server/src/main/resources/application.properties
```

以下を実行すると、サーバーが稼働を始めます。
```
org.mascheraveneziana.zitan.Application
```

lombokを使えるようにするには、以下の場所のjarをダブルクリックしてください。
```
.m2/repository/org/projectlombok/lombok/{バージョン}
```
インストール画面が表示され「IDEs」に入れる対象が表示されるので、OKであれば「Install/Update」をクリックします。
「IDEs」になければ「Specify location」から選択して追加してください。
インストール後、STS.iniの「-javaagent」にlombok.jarが追加されていることを確認してください
