package Mundo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    /**
     * Constante que muestra la version de la base de datos
     */
    private static final int DATA_BASE_VERSION = 1;

    /**
     * El nombre de la base de datos
     */
    private static  final String DATA_BASE_NAME = "usuarios.db";

    /**
     * Metodo constructor de la base de datos
     * @param context
     */
    public DBHelper(Context context)
    {
        super(context,DATA_BASE_NAME,null,DATA_BASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        String createTableUsuarios = "CREATE TABLE USUARIOS (nombre TEXT PRIMARY KEY, clave TEXT, telefono INTEGER)";
        String createTableMaterias = "CREATE TABLE MATERIAS (id INTEGER PRIMARY KEY AUTOINCREMENT, materia TEXT, usuario TEXT)";
        String createTableComentarios = "CREATE TABLE COMENTARIOS (id INTEGER PRIMARY KEY AUTOINCREMENT, usuario TEXT, comentario TEXT, calificacion INTEGER)";
        db.execSQL(createTableUsuarios);
        db.execSQL(createTableMaterias);
        db.execSQL(createTableComentarios);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST USUARIOS");
        db.execSQL("DROP TABLE IF EXIST MATERIAS");
        db.execSQL("DROP TABLE IF EXIST COMENTARIOS");
        onCreate(db);
    }
}
