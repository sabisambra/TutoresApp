package proyecto_moviles.tutoriasapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Mundo.DBHelper;

public class verComentarios extends ActionBarActivity {

    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_comentarios);
        Intent i = getIntent();
        String nombre = i.getStringExtra("nombre");

        DBHelper db = new DBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
        ArrayList<String> comentarios = new ArrayList<String>();
        String consultaComentarios = "SELECT * FROM COMENTARIOS WHERE usuario='" + nombre + "'";
        Cursor cursor = datos.rawQuery(consultaComentarios,null);
        if(cursor.moveToFirst())
        {
            do{
                String comentariosNuevo = cursor.getString(cursor.getColumnIndex("comentario"));
                comentarios.add(comentariosNuevo);
            }while(cursor.moveToNext());
        }
        cursor.close();
        datos.close();
        mList = (ListView)findViewById(R.id.comentarios);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.lista_item,R.id.label,comentarios);
        mList.setAdapter(adapter);
    }


}
