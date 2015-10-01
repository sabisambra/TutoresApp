package proyecto_moviles.tutoriasapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import Mundo.DBHelper;
import Mundo.Usuario;

public class misClases extends ActionBarActivity {

    private Usuario actual;
    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_clases);
        setTitle("Mis Clases");
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
        DBHelper db = new DBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
        ArrayList<String> materias = new ArrayList<String>();
        String consultaMaterias = "SELECT * FROM MATERIAS WHERE usuario='" + nombre + "'";
        Cursor cursor = datos.rawQuery(consultaMaterias,null);
        if(cursor.moveToFirst())
        {
            do{
                String materiaNueva = cursor.getString(cursor.getColumnIndex("materia"));
                materias.add(materiaNueva);
            }while(cursor.moveToNext());
        }
        cursor.close();
        datos.close();
        TextView temp = (TextView) findViewById(R.id.temporal);
        temp.setText("La cantidad de materias es: " + materias.size());
        mList = (ListView)findViewById(R.id.clases);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.lista_item,R.id.label,materias);
        mList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mis_clases, menu);
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
        else if(id == R.id.action_logout)
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

    public void agregarMateria(View v)
    {
        Intent intent = new Intent(this,AgregarMateria.class);
        startActivity(intent);
    }
}
