package proyecto_moviles.tutoriasapp;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.OutputStreamWriter;
import java.util.ArrayList;

import Mundo.DBHelper;
import Mundo.Usuario;

public class verTutores extends ActionBarActivity {


    private final int CONTACT_PICKER_RESULT = 1;
    private Usuario actual;
    private ListView mList;
    private ListView mListContactos;

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
        String busqueda = "SELECT * FROM MATERIAS INNER JOIN USUARIOS ON MATERIAS.usuario=USUARIOS.nombre WHERE materia='" + materia +"'";
        Cursor cursor = datos.rawQuery(busqueda,null);
        ArrayList<String> tutores = new ArrayList<String>();
        ArrayList<String> telefonos = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            do{
                String tutor = cursor.getString(cursor.getColumnIndex("usuario"));
                String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
                String[] PROJECTION = new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor c = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION,ContactsContract.CommonDataKinds.Phone.NUMBER + "='" + telefono + "'",null,null);
                Log.i("Resultados ", c.getCount() + "");
                if(c.moveToFirst())
                {
                    tutor = tutor + "       *";
                }
                else
                {
                    tutor = tutor + "        ";
                }
                telefonos.add(telefono);
                tutores.add(tutor);
            }while(cursor.moveToNext());
        }
        cursor.close();
        datos.close();

        for(int j=0;j<telefonos.size();j++)
        {
            Log.i("Telefono " + j, telefonos.get(j));
        }

        if(tutores.size()==0)
        {
            texto.setText("No se han encontrado tutores para " + materia + " para el dia " + dia + " a la hora " + hora);
        }
        else
        {
            texto.setText("");
            mList = (ListView) findViewById(R.id.tutores);
            //for(int j=0;j<telefonos.size();j++)
            //{
            //    String[] PROJECTION = new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            //    Cursor c = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION,ContactsContract.CommonDataKinds.Phone.NUMBER + "='" + telefonos.get(j) + "'",null,null);
            //    Log.i("Resultados ", c.getCount() + "");
            //    if(c.moveToFirst())
            //    {
            //        String tu = tutores.get(j);
            //        tu = tu + "       *";
            //    }
            //    else
            //    {
            //        String tu = tutores.get(j);
            //        tu = tu + "        ";
            //    }
            //}

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lista_item, R.id.label, tutores);
            mList.setAdapter(adapter);
            mList.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView parent, View view,
                                        int position, long id) {
                    String tutorSeleccionado = ((TextView) view).getText().toString();
                    String tutorSelect = tutorSeleccionado.substring(0,tutorSeleccionado.length()-8);
                    Intent j = new Intent(getApplicationContext(), InfoTutor.class);
                    j.putExtra("Tutor", tutorSelect);
                    j.putExtra("Nombre", actual.darNombre());
                    startActivity(j);
                }
            });
            //mListContactos = (ListView) findViewById(R.id.listViewContactos);
            //ArrayList<String> contactos = new ArrayList<String>();
            //for(int j=0;j<telefonos.size();j++)
            //{
            //    String[] PROJECTION = new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            //    Cursor c = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION,ContactsContract.CommonDataKinds.Phone.NUMBER + "='" + telefonos.get(j) + "'",null,null);
            //    Log.i("Resultados ", c.getCount() + "");
            //    if(c.moveToFirst())
            //    {
            //        contactos.add("*");
            //    }
            //    else
            //    {
            //        contactos.add(" ");
            //    }
            //}
            //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.lista_item, R.id.label, contactos);
            //mListContactos.setAdapter(adapter2);
        }
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
        else if(id==R.id.action_acerca_de)
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

    public void abrirContactos(View v)
    {
        Intent contentPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contentPickerIntent,CONTACT_PICKER_RESULT);
    }

}
