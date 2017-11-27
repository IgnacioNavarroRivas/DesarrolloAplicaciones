package app.pablonavarro.com.prototypeappproyect;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ShowDemand extends AppCompatActivity {

    EditText txt_name, txt_phone, txt_description;
    Spinner sp_type;
    Button btn_alter, btn_drop;

    int id;
    String nombre, telefono, tipo, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_demand);

        Spinner spinner = (Spinner) findViewById(R.id.sp_type);
        String[] letra = {"Gafiteria","Plomeria","Electricista","Cortador de Leña","Otro"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getInt("demand_id");
            nombre = bundle.getString("demand_name");
            telefono = bundle.getString("demand_phone");
            descripcion = bundle.getString("demand_description");
        }

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_phone = (EditText) findViewById(R.id.txt_phone);
        txt_description = (EditText) findViewById(R.id.txt_description);
        sp_type = (Spinner) findViewById(R.id.sp_type);

        txt_name.setText(nombre);
        txt_phone.setText(telefono);
        txt_description.setText(descripcion);

        btn_alter = (Button) findViewById(R.id.btn_alter);
        btn_drop = (Button) findViewById(R.id.btn_drop);

        btn_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(id);
                onBackPressed();
            }
        });

        btn_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(id,txt_name.getText().toString(),txt_phone.getText().toString(),sp_type.getSelectedItem().toString(),txt_description.getText().toString());
                onBackPressed();
            }
        });
    }

    private void modificar(int id, String nombre, String telefono, String tipo , String descripcion){
        BaseHelper helper = new BaseHelper(this, "ServiHome", null, 1);
        SQLiteDatabase dboh = helper.getWritableDatabase();

        String sql = "UPDATE demand SET demand_name = '" + nombre + "' , demand_phone = '" + telefono +
                "' , demand_type = '" + tipo + "' , demand_description = '" + descripcion +
                "' WHERE demand_id = " + id;
        dboh.execSQL(sql);
        dboh.close();
    }

    private void eliminar(int id){
        BaseHelper helper = new BaseHelper(this, "ServiHome", null, 1);
        SQLiteDatabase dboh = helper.getWritableDatabase();

        String sql = "DELETE FROM demand WHERE demand_id = " + id;
        dboh.execSQL(sql);
        dboh.close();
    }
}
