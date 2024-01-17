function showCurrentTime() {
 //現在時刻を取得するオブジェクトを作成
   const now = new Date();

   //Dateオブジェクトから｢年｣｢月｣｢日｣｢時｣｢分｣｢秒｣の数値を取り出す
   const year = now.getFullYear();
   const month = now.getMonth() + 1;
   const day = now.getDate();
   const hour = String(now.getHours()).padStart(2, '0');
   const minute = String(now.getMinutes()).padStart(2, '0');
   const second = String(now.getSeconds()).padStart(2, '0');

   var dayOfWeekStrJP = [ "日", "月", "火", "水", "木", "金", "土" ];
   var date = new Date(year, month - 1, day);
   var dayOfWeek = dayOfWeekStrJP[date.getDay()];

 　const time = `${hour}:${minute}:${second}`;
 　const today = `${month}月${day}日（${dayOfWeek}）`

   //id=currentTimeを持つ要素内のテキストをtimeに代入された文字列に変更する
   document.getElementById('currentTime').textContent = time;
   document.getElementById('monthAndDayTime').textContent = today;
}
showCurrentTime();
setInterval(showCurrentTime, 1000);

function startAnnounce() {
    if(window.confirm('出勤します。よろしいでしょうか？')){
        return true;
    }else{
        return false;
    }
}

function endAnnounce() {
    if(window.confirm('退勤します。よろしいでしょうか？')){
        return true;
    }else{
        return false;
    }
}

function breakStartAnnounce() {
    if(window.confirm('休憩を開始します。よろしいでしょうか？')){
        return true;
    }else{
        return false;
    }
}

function breakEndAnnounce() {
    if(window.confirm('休憩を終了します。よろしいでしょうか？')){
        return true;
    }else{
        return false;
    }
}

function logoutAnnounce() {
    if(window.confirm('ログアウトします。よろしいでしょうか？')){
        return true;
    }else{
        return false;
    }
}