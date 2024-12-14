# MessageRecord



## 插件功能

记录玩家聊天发言和私信，将玩家在游戏中的聊天发言储存在数据库中

例如这样：（数据库中的表单）

| id   | name  | time                | message  |
| ---- | ----- | ------------------- | -------- |
| 1    | Steve | 2024-02-07 18:39:23 | hi~      |
| 2    | Alex  | 2024-02-07 18:40:13 | hello    |
| 3    | Jeb   | 2024-02-07 18:40:51 | 你们好！ |


## 插件信息

已测试版本：   1.20.1

服务端： bukkit系

数据库：  仅支持mysql 

数据库版本：mysql8


## 配置文件

config.yml

```yaml
# 修改本配置后请重启插件

#私聊指令列表
msg-command-list:
  - msg
  - tell
  - w

mysql:
  host: 127.0.0.1
  port: 3306
  database: minecraft
  table: message_record
  user: root
  password: "123456"
  useSSL: false
```

