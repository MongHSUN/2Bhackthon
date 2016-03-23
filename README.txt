dsl_lab2&final

組員(分工):
楊承勳 B02902027(server+rasberry pi)
林雅琦 B02902045(server+rasberry pi)
黃刻力 B02902121(gps device)
李怡璇 B02902081(app)
黃亦晨 B02902053(app)

宗旨:可以將gps device貼在身邊重要的物品上,當找不到或是失竊的時候可以透過手機app來找到東西的位置.

code實作:
app_server.py是app端向server要資料的socket,並從1.txt檔中取得資料(從檔尾讀,代表最近一筆的位置)再傳回去.

pi_server.py是gps device傳資料到server的socket,並將資料寫在1.txt檔尾,若是client停止傳送資料,則中止server端的socket.

client是android app,可以向server拿到某gps device最近期的位置資料並定位在google map上

pi_client.py同時對gps跟server端進行socket的connection,取得gps資料後轉換成需要的格式再傳送給server

