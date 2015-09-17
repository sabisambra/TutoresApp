package proyecto_moviles.tutoriasapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import Mundo.DBHelper;
import Mundo.Usuario;

public class misClases extends ActionBarActivity {

    private Usuario actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_clases);
        setTitle("Mis Clases");
        Intent i = getIntent();
        String nombre = i.getStringExtra("Nombre");
        actual = new Usuario();
        actual.cambiarNombre(nombre);
        DBHelper db = new DBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
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
        if(id == R.id.action_acerca_de)
        {
            Intent intent = new Intent(this,AcercaDe.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void agregarMateria(View v)
    {
        Intent intent = new Intent(this,AgregarMateria.class);
        intent.putExtra("Nombre",actual.darNombre());
        startActivity(intent);
    }
}
