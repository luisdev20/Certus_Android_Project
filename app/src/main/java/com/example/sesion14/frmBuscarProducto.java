package com.example.sesion14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class frmBuscarProducto extends AppCompatActivity {
    private TextView pCod, pNom, pStock, pPV, pDet,pUnid, pEst;
    private EditText pBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_buscar_producto);
        pBuscar=findViewById(R.id.txtBuscar);
        pCod=findViewById(R.id.prodCod1);
        pNom=findViewById(R.id.prodNom1);
        pStock=findViewById(R.id.prodStock1);
        pPV=findViewById(R.id.prodPV1);
        pDet=findViewById(R.id.prodDet1);
        pUnid=findViewById(R.id.prodUnid1);
        pEst=findViewById(R.id.prodEst1);
    }

    public Connection conexionBD(){
        Connection cnn=null;
        try {
            StrictMode.ThreadPolicy pol=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(pol);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            cnn= DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.11:1433/DBCarrito;"+
                    "instance=MSSQLSERVER;user=sa;password=12345");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return cnn;
    }

    public void ConsultaLibro(View view) {
        try {
            Statement st = conexionBD().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM productos WHERE codigo='" + pBuscar.getText().toString() + "'");
            if (rs.next()) {
                pCod.setText(rs.getString(1));
                pNom.setText(rs.getString(2));
                pStock.setText(rs.getString(3));
                pPV.setText(rs.getString(4));
                pDet.setText(rs.getString(5));
                pUnid.setText(rs.getString(6));
                pEst.setText(rs.getString(7));
            } else {
                Toast.makeText(getApplicationContext(), "Producto "+pBuscar.getText().toString()+" no encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}