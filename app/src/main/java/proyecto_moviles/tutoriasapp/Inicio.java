package proyecto_moviles.tutoriasapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import Mundo.DBHelper;

public class Inicio extends Activity {

    public final static String DATOS = "datosCerebro.txt";

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerebro);
        db = new DBHelper(this);
        try
        {
            InputStream archivo = openFileInput(DATOS);
            if(archivo!=null)
            {
                InputStreamReader temp = new InputStreamReader(archivo);
                BufferedReader lector = new BufferedReader(temp);
                String linea = lector.readLine();
                if(!linea.equals("No hay sesion"))
                {
                    Intent i = new Intent(this,MainActivity.class);
                    startActivity(i);
                }
            }
            else
            {

            }
            archivo.close();

        }
        catch (Exception e)
        {

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
        boolean ingreso = false;
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
                try
                {
                    OutputStreamWriter impresora = new OutputStreamWriter(openFileOutput(DATOS, 0));
                    impresora.write(usuario);
                    impresora.close();
                    ingreso = true;
                }
                catch (Exception e)
                {
                    Log.i("Archivo", "No se escribio");
                }
            }
        }
        cursor.close();
        baseDatos.close();
        if(ingreso)
            startActivity(intent);
        else
        {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("El nombre de usuario y/o clave estan erradas").setNeutralButton("Cerrar", null).show();
            return;
        }
    }

    public void registrarse(View v)
    {
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }
}
