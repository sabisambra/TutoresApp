package proyecto_moviles.tutoriasapp;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class verTutores extends ActionBarActivity {


    private final int CONTACT_PICKER_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tutores);
        Intent i = getIntent();
        String materia = i.getStringExtra("Materia");
        String dia = i.getStringExtra("Dia");
        String hora = i.getStringExtra("Hora");
        TextView texto = (TextView) findViewById(R.id.texto);
        texto.setText(materia + "\n" + dia + "\n" + hora);
        setTitle("Tutores");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_tutores, menu);
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

    public void abrirContactos(View v)
    {
        Intent contentPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contentPickerIntent,CONTACT_PICKER_RESULT);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data)
    {

    }
}
