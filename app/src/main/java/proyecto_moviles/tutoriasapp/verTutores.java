package proyecto_moviles.tutoriasapp;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;

import Mundo.DBHelper;
import Mundo.Usuario;

public class verTutores extends ActionBarActivity {


    private final int CONTACT_PICKER_RESULT = 1;

    private Usuario actual;

    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tutores);
        Intent i = getIntent();
        actual = new Usuario();
        actual.cambiarNombre(i.getStringExtra("Nombre"));
        String materia = i.getStringExtra("Materia");
        String dia = i.getStringExtra("Dia");
        String hora = i.getStringExtra("Hora");
        TextView texto = (TextView) findViewById(R.id.texto);
        setTitle("Tutores");
        DBHelper db = new DBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
        String busqueda = "SELECT * FROM MATERIAS WHERE materia='" + materia +"'";
        Cursor cursor = datos.rawQuery(busqueda,null);
        ArrayList<String> tutores = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            do{
                String tutor = cursor.getString(cursor.getColumnIndex("usuario"));
                tutores.add(tutor);
            }while(cursor.moveToNext());
        }
        cursor.close();
        datos.close();
        texto.setText(materia + "\n" + dia + "\n" + hora + "\n" + "La busqueda dio como respuesta " + tutores.size());
        texto.setText("");
        mList = (ListView)findViewById(R.id.tutores);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.lista_item,R.id.label,tutores);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
                String tutorSeleccionado = ((TextView) view).getText().toString();
                Intent j = new Intent(getApplicationContext(), InfoTutor.class);
                j.putExtra("Tutor", tutorSeleccionado);
                j.putExtra("Nombre",actual.darNombre());
                startActivity(j);
            }
        });
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
