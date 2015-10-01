package proyecto_moviles.tutoriasapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import Mundo.DBHelper;
import Mundo.Usuario;

public class MainActivity extends ActionBarActivity {

    private Usuario actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView texto = (TextView)findViewById(R.id.textoBienvenida);
        actual = new Usuario();
        Intent i = getIntent();
        String nombre="";
        try {
            InputStream archivo = openFileInput(Inicio.DATOS);
            if(archivo!=null)
            {
                InputStreamReader temp = new InputStreamReader(archivo);
                BufferedReader lector = new BufferedReader(temp);
                nombre = lector.readLine();
                Log.i("El nombre archivo: ",nombre);
            }
            archivo.close();
        }
        catch(Exception e)
        {

        }
        actual.cambiarNombre(nombre);
        texto.setText("Hola " + nombre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        else if(id == R.id.action_acerca_de)
        {
            Intent intent = new Intent(this,AcercaDe.class);
            startActivity(intent);
        }
        else if(id==R.id.action_logout)
        {
            Intent intent = new Intent(this,Inicio.class);
            try
            {
                OutputStreamWriter impresora = new OutputStreamWriter(openFileOutput(Inicio.DATOS, 0));
                impresora.write("No hay sesion");
                impresora.close();
            }
            catch (Exception e)
            {
                Log.i("Archivo", "No se escribio");
            }
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void mostrarTutores(View v)
    {
        Spinner spinnerMateria = (Spinner)findViewById(R.id.spinnerMaterias);
        String materia = spinnerMateria.getSelectedItem().toString();
        Intent intent = new Intent(this,verTutores.class);
        intent.putExtra("Materia", materia);
        Spinner spinnerDia = (Spinner) findViewById(R.id.spinnerDia);
        String dia = spinnerDia.getSelectedItem().toString();
        intent.putExtra("Dia", dia);
        Spinner spinnerHora = (Spinner) findViewById(R.id.spinnerHoras);
        String hora = spinnerHora.getSelectedItem().toString();
        intent.putExtra("Hora",hora);
        intent.putExtra("Nombre", actual.darNombre());
        startActivity(intent);
    }

    public void misClases(View v)
    {
        Intent intent = new Intent(this,misClases.class);
        intent.putExtra("Nombre", actual.darNombre());
        startActivity(intent);
    }
}
