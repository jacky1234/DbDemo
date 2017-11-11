## 功能示例
1. ContentProvider 权限等问题
2. Database 基本操作，升级等。

温故而知新可以为师矣。

### 常见问题
**1. SQLiteOpenHelper#onCreate什么时候会回调**

在调getReadableDatabase或getWritableDatabase时，会判断指定的数据库是否存在，不存在则调SQLiteDatabase.create创建

**2. ContentProvider 注册示例,通知示例**