package proyecto_moviles.tutoriasapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStreamWriter;

import Mundo.DBHelper;
import Mundo.Usuario;

public class InfoTutor extends ActionBarActivity {

    private Usuario actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_tutor);
        Intent i = getIntent();
        String nombreTutor = i.getStringExtra("Tutor");
        TextView nombreTutorText = (TextView)findViewById(R.id.nombreTutor);
        TextView telefonoTutorText = (TextView)findViewById(R.id.telefonoTutor);
        DBHelper db = new DBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
        String busqueda = "SELECT * FROM USUARIOS WHERE nombre='" + nombreTutor + "'";
        Cursor cursor = datos.rawQuery(busqueda,null);
        if(cursor.moveToFirst())
        {
            String nombreTutorQuery = cursor.getString(cursor.getColumnIndex("nombre"));
            String telefonoQuery = cursor.getString(cursor.getColumnIndex("telefono"));
            nombreTutorText.setText(nombreTutorQuery);
            telefonoTutorText.setText(telefonoQuery);
        }
        cursor.close();
        datos.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_tutor, menu);
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

    public void enviarMensaje(View v)
    {
        SmsManager manager = SmsManager.getDefault();
        TextView telefonoText = (TextView) findViewById(R.id.telefonoTutor);
        String telefono = telefonoText.getText().toString();
        EditText mensaje = (EditText) findViewById(R.id.mensajeTexto);
        String mensajeTexto = mensaje.getText().toString();
        manager.sendTextMessage(telefono,null,mensajeTexto,null,null);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Nombre",actual.darNombre());
        startActivity(intent);

    }

    public void verComentarios(View v)
    {

    }

    public void agregarComentario(View w)
    {

    }

    public void llamar(View v)
    {
        Intent i = new Intent(Intent.ACTION_CALL);
        TextView telefonoTutor = (TextView) findViewById(R.id.telefonoTutor);
        String telefono = telefonoTutor.getText().toString();
        i.setData(Uri.parse("tel:"+telefono));
        try
        {
            startActivity(i);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
