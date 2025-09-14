package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import model.Pessoa;

public class PessoaDao {

    private final DBHelper helper;

    public PessoaDao(Context context) {
        this.helper = new DBHelper(context.getApplicationContext());
    }

    public long inserir(Pessoa p) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COL_NOME, p.getNome());
            cv.put(DBHelper.COL_EMAIL, p.getEmail());
            return db.insertOrThrow(DBHelper.TABELA_PESSOA, null, cv);
        } finally {
            db.close();
        }
    }

    public int atualizar(Pessoa p) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COL_NOME, p.getNome());
            cv.put(DBHelper.COL_EMAIL, p.getEmail());
            String where = DBHelper.COL_ID + " = ?";
            String[] args = { String.valueOf(p.getId()) };
            return db.update(DBHelper.TABELA_PESSOA, cv, where, args);
        } finally {
            db.close();
        }
    }

    public int excluir(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String where = DBHelper.COL_ID + " = ?";
            String[] args = { String.valueOf(id) };
            return db.delete(DBHelper.TABELA_PESSOA, where, args);
        } finally {
            db.close();
        }
    }

    public Pessoa buscarPorId(long id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(
                    DBHelper.TABELA_PESSOA,
                    new String[]{DBHelper.COL_ID, DBHelper.COL_NOME, DBHelper.COL_EMAIL},
                    DBHelper.COL_ID + " = ?",
                    new String[]{ String.valueOf(id) },
                    null, null, null
            );
            if (c.moveToFirst()) {
                Pessoa p = new Pessoa();
                p.setId(c.getLong(0));
                p.setNome(c.getString(1));
                p.setEmail(c.getString(2));
                return p;
            }
            return null;
        } finally {
            if (c != null) c.close();
            db.close();
        }
    }

    public List<Pessoa> listarTudo() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = null;
        List<Pessoa> lista = new ArrayList<>();
        try {
            c = db.query(
                    DBHelper.TABELA_PESSOA,
                    new String[]{DBHelper.COL_ID, DBHelper.COL_NOME, DBHelper.COL_EMAIL},
                    null, null, null, null,
                    DBHelper.COL_NOME + " COLLATE NOCASE ASC"
            );
            while (c.moveToNext()) {
                Pessoa p = new Pessoa();
                p.setId(c.getLong(0));
                p.setNome(c.getString(1));
                p.setEmail(c.getString(2));
                lista.add(p);
            }
            return lista;
        } finally {
            if (c != null) c.close();
            db.close();
        }
    }
}
