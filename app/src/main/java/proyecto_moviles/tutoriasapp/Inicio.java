package proyecto_moviles.tutoriasapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

import Mundo.DBHelper;

public class Inicio extends Activity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        if(db==null) {
            Log.i("Se creo la base de dato", "!!!!!!!!!");
            db = new DBHelper(this);
        }
        else
        {
            Log.i("No se creo la base", "XXXXXXXXXXXX");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_acerca_de) {
            Intent intent = new Intent(this,AcercaDe.class);
        }

        return super.onOptionsItemSelected(item);
    }

    public void ingresar(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        EditText usuarioText = (EditText) findViewById(R.id.nombreUsuario);
        EditText claveText = (EditText) findViewById(R.id.claveUsuario);
        String usuario = usuarioText.getText().toString();
        String clave = claveText.getText().toString();
        String selectQuery = "SELECT * FROM USUARIOS WHERE nombre='" + usuario + "'";
        SQLiteDatabase baseDatos = db.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            String usuarioDB = cursor.getString(cursor.getColumnIndex("nombre"));
            String claveDB =  cursor.getString(cursor.getColumnIndex("clave"));
            if(usuario.equals(usuarioDB) && clave.equals(claveDB))
            {
                intent.putExtra("Nombre",usuarioDB);
            }
        }
        cursor.close();
        baseDatos.close();
        startActivity(intent);
    }

    public void registrarse(View v)
    {
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }

    public DBHelper darDB()
    {
        return  db;
    }
}
