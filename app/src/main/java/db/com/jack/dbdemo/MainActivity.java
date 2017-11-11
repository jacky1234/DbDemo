package db.com.jack.dbdemo;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private int count;
    private ContentObserver mContentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getSharedPreferences("config", 0);
        count = sharedPreferences.getInt("count", 0);

        //update
        final Uri studentUri = Uri.parse("content://" + StudentProvider.AUTHORITY + "/" + StudentProvider.STUDENT_PATH);
        for (int i = 0; i < 5; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataColumn.Student.COLUMN_NAME_NAME, "jack" + ++count);
            contentValues.put(DataColumn.Student.COLUMN_NAME_NUMBER, count * 10000);
            getContentResolver().insert(studentUri, contentValues);
        }

        sharedPreferences.edit().putInt("count", count).apply();

        mContentObserver = new MyDataObserver(new Handler(Looper.getMainLooper()));
        getContentResolver().registerContentObserver(studentUri, true, mContentObserver);

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DataColumn.Student.COLUMN_NAME_NAME, "jack" + ++count);
                contentValues.put(DataColumn.Student.COLUMN_NAME_NUMBER, count * 10000);
                getContentResolver().insert(studentUri, contentValues);

                sharedPreferences.edit().putInt("count", count).apply();
            }
        });

        findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int delete = getContentResolver().delete(studentUri, DataColumn.Student.COLUMN_NAME_NAME + " like 'jack1%'", null);
                Log.v("MainActivity", "delete count:" + delete);
            }
        });

        findViewById(R.id.tv_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DataColumn.Student.COLUMN_NAME_NUMBER, 100000000);

                final int update = getContentResolver().update(studentUri, contentValues, DataColumn.Student.COLUMN_NAME_NAME + " like 'jack2%'", null);
                Log.v("MainActivity", "update count:" + update);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mContentObserver != null) {
            getContentResolver().unregisterContentObserver(mContentObserver);
        }
    }

    private class MyDataObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyDataObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            Log.v("MainActivity", "onChange, selfChange:" + selfChange);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.v("MainActivity", "onChange, selfChange:" + selfChange + ",uri:" + uri.toString());
        }
    }
}
