# README

Mobile Application Development Assignment @CSU.

Instructor: Xiang Yang

## 实验内容

1. 在手机中包含主视图和细节视图，主视图显示连续多天的天气预报简讯，用户在主视图中点击某一天的天气简讯以后，跳出细节视图，显示用户选定当天天气的详细信息。
2. 在平板中使用Master-Detail视图，当用户点击某一天的天气预览以后，直接在界面右 边显示当天天气的详细信息。 
3. 主视图中包含Map Location和setting选项，通过”Map location” 选项，可以调用手机中安装的地图应用显示当前天气预报所对应的位置，用户可以通过 setting选项可以修改天气预报的位置，温度的单位（华氏度、摄氏度）以及是否开 启天气通知。如果setting选项中的天气通知选项打开，会定期发送通知消息，其中显示当天的天气简讯。 
4. 细节视图菜单中包含分享和setting选项，用户可以通过分享选项通过其他应用（邮 件、短信等）将天气详细信息分享给别人。
5. 利用SQLite对天气预报数据进行持久化保存，如果网络不可用的情况下，从SQLite 中提取天气预报数据。
6. Web API使用He Weather， https://www.heweather.com/documents/api/s6/weather-forecast


Notes: Bugs with processing on the date value have not been handled yet, apps could collapse after launching.
