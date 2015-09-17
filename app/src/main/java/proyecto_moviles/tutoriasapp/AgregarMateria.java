package proyecto_moviles.tutoriasapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import Mundo.DBHelper;
import Mundo.Usuario;

public class AgregarMateria extends ActionBarActivity {

    private Usuario actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_materia);
        Intent i = getIntent();
        String nombre = i.getStringExtra("Nombre");
        actual = new Usuario();
        actual.cambiarNombre(nombre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_materia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void agregarMateria(View v)
    {
        DBHelper db = new DBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
        Spinner materiaSelec = (Spinner)findViewById(R.id.spinnerAgregarMaterias);
        String materia = materiaSelec.getSelectedItem().toString();
        String consultaExistencia = "SELECT * FROM MATERIAS WHERE usuario='" + actual.darNombre() + "' AND materia='" + materia + "'";
        Cursor cursor = datos.rawQuery(consultaExistencia,null);
        if(!cursor.moveToFirst())
        {
            datos = db.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("usuario",actual.darNombre());
            valores.put("materia",materia);
            datos.insert("MATERIAS",null,valores);
        }
        cursor.close();
        datos.close();
        Intent intent = new Intent(this,misClases.class);
        intent.putExtra("Nombre",actual.darNombre());
        startActivity(intent);
    }
}
