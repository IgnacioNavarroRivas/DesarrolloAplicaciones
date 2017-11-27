package app.pablonavarro.com.prototypeappproyect;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txt_name, txt_phone, txt_description;
    Spinner sp_type;
    Button btn_insert, btn_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Created by Andrea Ardións
        *  Edited by Pablo on 11/26/2017
        *  */
        Spinner spinner = (Spinner) findViewById(R.id.sp_type);
        String[] letra = {"Gafiteria","Plomeria","Electricista","Cortador de Leña","Otro"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_phone = (EditText) findViewById(R.id.txt_phone);
        txt_description = (EditText) findViewById(R.id.txt_description);
        sp_type = (Spinner) findViewById(R.id.sp_type);

        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_select = (Button) findViewById(R.id.btn_select);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar(txt_name.getText().toString(),txt_phone.getText().toString(), sp_type.getSelectedItem().toString(), txt_description.getText().toString());
            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListDemand.class));
            }
        });
    }

    /* Created by gonzaloperez on 04/21/2016.
    *  Edited by pablo on 25/11/2017
    *   */
    private void guardar(String nombre , String telefono , String tipo , String descripcion){
        BaseHelper helper = new BaseHelper(this, "ServiHome", null, 1);
        SQLiteDatabase dboh = helper.getWritableDatabase();
        try {
            ContentValues c = new ContentValues();
            c.put("demand_name", nombre);
            c.put("demand_phone", telefono);
            c.put("demand_type", tipo);
            c.put("demand_description", descripcion);
            dboh.insert("demand", null, c);
            dboh.close();
            Toast.makeText(this,"Requerimiento enviado",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this,"ERROR AL ENVIAR: " + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
