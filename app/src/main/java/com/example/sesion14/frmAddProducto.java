package com.example.sesion14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class frmAddProducto extends AppCompatActivity {
    private EditText pCod, pNom, pStock, pPV, pDet, pUnid, pEst;
    private Button BSelectImage;
    private ImageView IVPreviewImage;
    /* Ver utilidad de esto */
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_add_producto);
        pCod=findViewById(R.id.prodCod);
        pNom=findViewById(R.id.prodNom);
        pStock=findViewById(R.id.prodStock);
        pPV=findViewById(R.id.prodPV);
        pDet=findViewById(R.id.prodDet);
        pUnid=findViewById(R.id.prodUnid);
        pEst=findViewById(R.id.prodEst);
        // Para el boton imagen y su visualizacion
        BSelectImage=findViewById(R.id.BSelectImage);
        IVPreviewImage=findViewById(R.id.IVPreviewImage);
        // etc
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
    }

    public void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i,"Selecciona la foto"), SELECT_PICTURE);
    }

    // Esta funcion se activa cuando el usuario selecciona la imagen
    // desde la funcion imageChooser()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            if (resultCode == SELECT_PICTURE) {
                // Obtiene el url de la imagen desde la data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    //Actualiza la imagen del layout ImageView
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
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

    public void Insertar(View view) {
        try {
            Statement st = conexionBD().createStatement();
            st.executeUpdate("INSERT INTO productos values('"+pCod.getText().toString()+
                                                            "','"+pNom.getText().toString()+
                                                            "','"+pStock.getText().toString()+
                                                            "','"+pPV.getText().toString()+
                                                            "','"+pDet.getText().toString()+
                                                            "','"+pUnid.getText().toString()+
                                                            "','"+pEst.getText().toString()+"')");
            pNom.setText("");
            pStock.setText("");
            pPV.setText("");
            pDet.setText("");
            pUnid.setText("");
            pEst.setText("");
            pCod.setText("");
            Toast.makeText(getApplicationContext(),"Producto añadido",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}