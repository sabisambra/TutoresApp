package proyecto_moviles.tutoriasapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import Mundo.DBHelper;
import Mundo.Usuario;

public class agregarComentario extends ActionBarActivity {

    private Usuario actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_comentario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        String nombre = i.getStringExtra("nombre");
        actual = new Usuario();
        actual.cambiarNombre(nombre);
    }

    public void subirComentario(View v) {
        EditText comentario = (EditText) findViewById(R.id.nuevoComentario);
        String comment = comentario.getText().toString();
        if (!comment.equals("")){
            Spinner spin = (Spinner) findViewById(R.id.spinCalificacion);
            String calificacion = spin.getSelectedItem().toString();
            int calif = Integer.parseInt(calificacion);
            DBHelper db = new DBHelper(this);
            SQLiteDatabase datos = db.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("usuario", actual.darNombre());
            valores.put("comentario", comment);
            valores.put("calificacion", calif);
            datos.insert("COMENTARIOS", null, valores);
            datos.close();
        }
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
