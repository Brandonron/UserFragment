# UserFragment

API 文件: https://docs.github.com/en/rest/reference/users


---------------------------------- 目前採用框架模式 ---------------------------------- 
1. 採用 Okhttp + Retrofit2 進行 API Restful (尚未建立ApiExceptionManager 進行統一的 response code / timeout 處理)

2. Image UI 採用 Fresco(Facebook lib)，由元件控制 image Loading 和 image catch

3. 顯示子分頁的方式採用DialogFragment，目的在於不使用Transaction 去進行 Framelayout 覆蓋，而是可以在任何頁面都可以呼叫使用


---------------------------------- 後續要完善的加入項目 ---------------------------------- 
1. Room Database 達成離線顯示功能，同時練習嘗試用 Relationships 進行關聯表操作

2. RecyclerView + Recycler Adapter，並提供下拉更新元件可操作(未採用但尚在嘗試 Kotlin paging lib for recycler adapter)

3. Flaoting button 提供資料過多時，想從最上頁回到最頂層的功能

4. 使用 BroadcastReceiver 進行 Network staus 觀察，以及API狀態異常顯示分兩種: 1. 網路發生異常(無網路能力)，採用Snack Bar 顯示。2. API failed/timeout 採用 Toast 顯示



