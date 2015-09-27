package proyecto_moviles.tutoriasapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import Mundo.DBHelper;

public class Registro extends ActionBarActivity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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

    public void realizarRegistro(View v)
    {
        Intent intent = new Intent(this,Inicio.class);
        EditText nombreText = (EditText) findViewById(R.id.nombreRegistro);
        EditText claveText = (EditText) findViewById(R.id.claveRegistro);
        EditText telefonoText = (EditText) findViewById(R.id.telefonoRegistro);
        String nombre = nombreText.getText().toString();
        String clave = claveText.getText().toString();
        Log.i("Telefono","EL telefono es:" + telefonoText.getText().toString());
        int telefono = Integer.parseInt(telefonoText.getText().toString());
        DBHelper db = new DBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
        String consultaExistencia = "SELECT * FROM USUARIOS WHERE nombre='" + nombre +"'";
        Cursor cursor = datos.rawQuery(consultaExistencia,null);
        if(!cursor.moveToFirst())
        {
            datos = db.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("nombre",nombre);
            valores.put("clave",clave);
            valores.put("telefono",telefono);
            datos.insert("USUARIOS",null,valores);
        }
        datos.close();
        startActivity(intent);
    }
}
