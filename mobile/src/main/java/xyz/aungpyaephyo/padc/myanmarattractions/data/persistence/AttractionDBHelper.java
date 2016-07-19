package xyz.aungpyaephyo.padc.myanmarattractions.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract.AttractionEntry;
import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract.AttractionImageEntry;
import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract.LoginUserEntry;

/**
 * Created by aung on 7/9/16.
 */
public class AttractionDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "attractions.db";

    private static final String SQL_CREATE_ATTRACTION_TABLE = "CREATE TABLE " + AttractionEntry.TABLE_NAME + " (" +
            AttractionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AttractionEntry.COLUMN_TITLE + " TEXT NOT NULL, "+
            AttractionEntry.COLUMN_DESC + " TEXT NOT NULL, "+

            " UNIQUE (" + AttractionEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_ATTRACTION_IMAGE_TABLE = "CREATE TABLE " + AttractionImageEntry.TABLE_NAME + " (" +
            AttractionImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AttractionImageEntry.COLUMN_ATTRACTION_TITLE + " TEXT NOT NULL, "+
            AttractionImageEntry.COLUMN_IMAGE + " TEXT NOT NULL, "+

            " UNIQUE (" + AttractionImageEntry.COLUMN_ATTRACTION_TITLE + ", "+
            AttractionImageEntry.COLUMN_IMAGE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_LOGIN_USER_TABLE = "CREATE TABLE " + LoginUserEntry.TABLE_NAME + " (" +
            LoginUserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LoginUserEntry.COLUMN_NAME + " TEXT NOT NULL, "+
            LoginUserEntry.COLUMN_EMAIL + " TEXT NOT NULL, "+
            LoginUserEntry.COLUMN_ACCESS_TOKEN + " TEXT NOT NULL, "+
            LoginUserEntry.COLUMN_DATE_OF_BIRTH + " TEXT NOT NULL, "+
            LoginUserEntry.COLUMN_COUNTRY + " TEXT NOT NULL, "+
            LoginUserEntry.COLUMN_REGISTERED_DATE + " TEXT NOT NULL, "+
            LoginUserEntry.COLUMN_LAST_USED_DATE + " TEXT, "+

            " UNIQUE (" + LoginUserEntry.COLUMN_EMAIL + ") ON CONFLICT REPLACE" +
            " );";

    public AttractionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ATTRACTION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ATTRACTION_IMAGE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LOGIN_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AttractionImageEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AttractionEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LoginUserEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
