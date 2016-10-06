package komiskey.jacob.myfitkit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import komiskey.jacob.myfitkit.database.CustomerDbSchema.CustomerTable;

public class CustomerBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "customerBase.db";
    

    public CustomerBaseHelper (Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CustomerTable.NAME + "(" +
        CustomerTable.Cols.UUID + ", " +
        CustomerTable.Cols.CUSTOMER_NAME + ", " +
        CustomerTable.Cols.PHONE + ", " +
        CustomerTable.Cols.EMAIL +
        ")"
      );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
