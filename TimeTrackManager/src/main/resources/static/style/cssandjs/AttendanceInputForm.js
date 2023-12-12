function showCurrentTime() {
 //現在時刻を取得するオブジェクトを作成
   const now = new Date();

   //Dateオブジェクトから｢時｣｢分｣｢秒｣の数値を取り出す
   const hour = now.getHours();
   const minute = now.getMinutes();
   const second = now.getSeconds();

   //表示を｢現在の時刻は○時△分×秒です｣の形に整える
 　const time = `現在の時刻は${hour}時${minute}分${second}秒です。`;

   //id=currentTimeを持つ要素内のテキストをtimeに代入された文字列に変更する
   document.getElementById('currentTime').textContent = time;
}

showCurrentTime();