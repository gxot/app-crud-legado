package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "pessoas.db";
    public static final int DB_VERSION = 1;
    public static final String TABELA_PESSOA = "pessoa";
    public static final String COL_ID = "_id";
    public static final String COL_NOME = "nome";
    public static final String COL_EMAIL = "email";

    public DBHelper(Context ctx) { super(ctx, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE " + TABELA_PESSOA + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOME + " TEXT NOT NULL, " +
                COL_EMAIL + " TEXT NOT NULL)";
        db.execSQL(ddl);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PESSOA);
        onCreate(db);
    }
}
