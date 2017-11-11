package db.com.jack.dbdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 2017/11/9.
 * github:[https://github.com/jacky1234]
 *
 * @author jackyang
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "scxh1502.db"; // 数据库名
    private static final int DB_VERSION = 2; // 数据库版本号
    public static final String STUDENT_TABLE_NAME = DataColumn.Student.TABLE_NAME;

    private final String CREATE_STUDENT_TABLE = "create table " + STUDENT_TABLE_NAME + " ("
            + DataColumn.Student._ID
            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + DataColumn.Student.COLUMN_NAME_NAME + " TEXT, "
            + DataColumn.Student.COLUMN_NAME_NUMBER + " TEXT " + ")";

    //从 version 1 to 2 等升级语句: 在ID column 后加入 age,age 默认为20
    private final String UPDATE_SQL_V122 = "ALTER TABLE " + STUDENT_TABLE_NAME + " ADD COLUMN "
            + DataColumn.Student.COLUMN_NAME_AGE + " AFTER "
            + DataColumn.Student._ID + " INT NULL DEFAULT 20";


    private static SQLiteOpenHelper DB_HELPER = null;

    public static SQLiteOpenHelper getInstanceDatabaseHelper(Context context) {
        if (DB_HELPER == null) {
            DB_HELPER = new MyDatabaseHelper(context);
        }
        return DB_HELPER;
    }

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 数据库创建时执行
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    /**
     * 数据库版本发生变化
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            if (newVersion == 2) {   //add age column
                db.execSQL(UPDATE_SQL_V122);
                Log.v("MyDatabaseHelper", "upgrade success oldVersion:" + oldVersion + ",newVersion:" + newVersion);
            }
        }
    }
}