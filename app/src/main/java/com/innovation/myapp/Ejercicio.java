package com.innovation.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ejercicio extends AppCompatActivity {

    EditText etCode, etDescription, etPrice;
    Button btnRegistrar, btn_Buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        etCode = (EditText) findViewById(R.id.etCode);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etPrice = (EditText) findViewById(R.id.etPrice);
        //vincular boton registrar

        btnRegistrar = (Button) findViewById(R.id.btn_registrar);

        btn_Buscar = (Button) findViewById(R.id.Btn_Buscar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });

        btn_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegister(v);
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

//metodo para asignar las funciones correspondientes a las funcones

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            Intent intent = new Intent(this, frutas.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.item2) {
            Intent intent = new Intent(this, animales.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.item3) {
            Intent intent = new Intent(this, colores.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void register(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String code = etCode.getText().toString();
        String description = etDescription.getText().toString();
        String price = etPrice.getText().toString();

        if(!code.isEmpty() && !description.isEmpty() && !price.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("code", code);
            registro.put("description", description);
            registro.put("price", price);

            BaseDeDatos.insert("articles", null, registro);
            BaseDeDatos.close();
            etCode.setText("");
            etDescription.setText("");
            etPrice.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    //get register for update

    public void getRegister(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String code = etCode.getText().toString();

        if(!code.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select description, price from articles where code = " + code, null);

            if(fila.moveToFirst()){
                etDescription.setText(fila.getString(0));
                etPrice.setText(fila.getString(1));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Debes introducir el código del artículo", Toast.LENGTH_SHORT).show();
        }
    }
}