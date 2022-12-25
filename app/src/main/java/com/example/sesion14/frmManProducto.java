package com.example.sesion14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class frmManProducto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_man_producto);
    }

    public void addProducto(View view){
        Intent x = new Intent(this,frmAddProducto.class);
        startActivity(x);
    }

    public void buscarProducto(View view){
        Intent x = new Intent(this,frmBuscarProducto.class);
        startActivity(x);
    }
}