package com.example.sesion14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private EditText txtUsu, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsu=findViewById(R.id.txtUsuario);
        txtPass=findViewById(R.id.txtPassword);
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

    public void Consulta(View view){
        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM usuarios where logeo='"+txtUsu.getText().toString()+"'");
            if(rs.next()){

                String tipoU=rs.getString(2);
                Toast.makeText(getApplicationContext(),"Conexion establecida "+rs.getString(3),Toast.LENGTH_SHORT).show();
                if(tipoU.compareTo("ADMIN")==0){ //admin
                    Intent x=new Intent(this,frmMainAdmin.class);
                    startActivity(x);
                }
                if(tipoU.compareTo("USER")==0){ //user
                    Intent x=new Intent(this,frmMainCliente.class);
                    startActivity(x);
                }
                if(tipoU.compareTo("GUEST")==0) { //guest
                    Intent x = new Intent(this, frmMainInvitado.class);
                    startActivity(x);
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}

