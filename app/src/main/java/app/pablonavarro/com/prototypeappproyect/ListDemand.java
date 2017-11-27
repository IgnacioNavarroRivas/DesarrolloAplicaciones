package app.pablonavarro.com.prototypeappproyect;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDemand extends AppCompatActivity {

    ListView lv_list;
    ArrayList<String> listado = new ArrayList<String>();

    @Override
    protected void onPostResume() {
        super.onPostResume();
        chargeList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demand);
        lv_list = (ListView) findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(ListDemand.this, "Posicion: " + position, Toast.LENGTH_SHORT).show();
                int key = Integer.parseInt(listado.get(position).split(" ")[0]);
                String name = listado.get(position).split(" ")[1];
                String phone = listado.get(position).split(" ")[2];
                String description = listado.get(position).split(" ")[4];

                Intent intent = new Intent(ListDemand.this, ShowDemand.class );
                intent.putExtra("demand_id",key);
                intent.putExtra("demand_name",name);
                intent.putExtra("demand_phone",phone);
                intent.putExtra("demand_description",description);
                startActivity(intent);
            }
        });

        chargeList();

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void chargeList(){
        listado = demandList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listado);
        lv_list.setAdapter(adapter);
    }

    private ArrayList<String> demandList(){
        ArrayList<String> datos = new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this, "ServiHome", null, 1);
        SQLiteDatabase dboh = helper.getReadableDatabase();
        try {
            String sql = "SELECT demand_id, demand_name, demand_phone, demand_type, demand_description FROM demand";
            Cursor c = dboh.rawQuery(sql,null);
            if (c.moveToFirst()){
                do {
                    String linea = c.getInt(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(3) + " " + c.getString(4);
                    datos.add(linea);
                }while (c.moveToNext());
            }
            dboh.close();
        }catch (Exception e){
            Toast.makeText(this,"ERROR AL MOSTRAR: " + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return datos;
    }
}
