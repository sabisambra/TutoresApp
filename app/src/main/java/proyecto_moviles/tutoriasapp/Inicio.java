package proyecto_moviles.tutoriasapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Inicio extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
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
        String usuario = usuarioText.toString();
        String clave = claveText.toString();
        startActivity(intent);
    }

    public void registrarse(View v)
    {
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }
}
