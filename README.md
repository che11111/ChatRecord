# ChatRecord

<img src="ChatRecord.png" width="310" height="300" alt="image">

## Plugin Functions

It records players' chat messages and private messages, and stores the chat messages of players in the game in the database.

For example, it would look like this (a form in the database):

| id   | name  | time                | message  |
| ---- | ----- | ------------------- | -------- |
| 1    | Steve | 2024-02-07 18:39:23 | hi~      |
| 2    | Alex  | 2024-02-07 18:40:13 | hello    |
| 3    | Jeb   | 2024-02-07 18:40:51 | 你们好！ |

## Plugin Information

**Tested Version**: 1.20.1

**Server Software**: Bukkit-based

**Database**: Only supports MySQL

**Database Version**: MySQL 8

## Configuration File

config.yml

```yaml
# Please restart the plugin after modifying this configuration.

# List of private message commands
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
