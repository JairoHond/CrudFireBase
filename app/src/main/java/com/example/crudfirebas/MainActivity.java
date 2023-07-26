package com.example.crudfirebas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crudfirebas.modelos.usuarios;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<usuarios> listusu = new ArrayList<usuarios>();
    ArrayAdapter<usuarios> arrayAdapterusu;
    EditText txtForo,txtNombre,txtApellido,txtFecha;

    Button btnAdd,btnEdit,btnBorrar;
    Spinner genero;
    ListView listView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    usuarios usuarioSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtForo=findViewById(R.id.txtForo);
        txtNombre=findViewById(R.id.txtNombre);
        txtApellido=findViewById(R.id.txtApellido);
        txtFecha=findViewById(R.id.txtFecha);

        btnAdd=findViewById(R.id.btnAdd);
        btnBorrar=findViewById(R.id.btnEliminar);
        btnEdit=findViewById(R.id.btnEditar);

        genero=findViewById(R.id.pais);
        listView=findViewById(R.id.ListView);
        iniciarFirebase();
        listaDatos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSelected=(usuarios) parent.getItemAtPosition(position);
                txtForo.setText(usuarioSelected.getForo());
                txtNombre.setText(usuarioSelected.getNombre());
                txtApellido.setText(usuarioSelected.getApellido());
                txtFecha.setText(usuarioSelected.getFecha());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
    }

    public void iniciarFirebase()
    {
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference= firebaseDatabase.getReference();
    }
    public void listaDatos()
    {
        databaseReference.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listusu.clear();
                for (DataSnapshot objSnaptchot : snapshot.getChildren()){
                    usuarios u= objSnaptchot.getValue(usuarios.class);
                    listusu.add(u);
                    arrayAdapterusu=new ArrayAdapter<usuarios>(MainActivity.this, android.R.layout.simple_list_item_1,listusu);
                    listView.setAdapter(arrayAdapterusu);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void agregar()
    {
        String foro=txtForo.getText().toString();
        String nombre=txtNombre.getText().toString();
        String apellido=txtApellido.getText().toString();
        String fecha=txtFecha.getText().toString();
        String sexo=genero.getSelectedItem().toString();

        if(foro.equals("")||nombre.equals("")||apellido.equals("")||fecha.equals(""))
        {
            validacion();
        }
        else{
            usuarios u= new usuarios();
            u.setID(UUID.randomUUID().toString());
            u.setForo(foro);
            u.setApellido(apellido);
            u.setNombre(nombre);
            u.setGenero(sexo);
            u.setFecha(fecha);
            databaseReference.child("usuarios").child(u.getID()).setValue(u);
            Toast.makeText(this,"Dato agregado correctamente", Toast.LENGTH_LONG).show();
            Limpiar();
        }
    }

    private void Limpiar() {
        txtForo.setText("");
        txtFecha.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
    }

    public void editar()
    {
        usuarios u= new usuarios();
        u.setID(usuarioSelected.getID());
        u.setNombre(txtNombre.getText().toString().trim());
        u.setForo(txtForo.getText().toString().trim());
        u.setApellido(txtApellido.getText().toString().trim());
        u.setFecha(txtFecha.getText().toString().trim());
        u.setGenero(genero.getSelectedItem().toString().trim());
        databaseReference.child("usuarios").child(u.getID()).setValue(u);
        Toast.makeText(this,"Dato Actualizados correctamente", Toast.LENGTH_LONG).show();
        Limpiar();

    }

    public void eliminar()
    {
        usuarios u=new usuarios();
        u.setID(usuarioSelected.getID());
        databaseReference.child("usuarios").child(u.getID()).removeValue();
        Toast.makeText(this,"Dato Eliminados correctamente", Toast.LENGTH_LONG).show();
        Limpiar();
    }

    public void validacion()
    {
        String foro=txtForo.getText().toString();
        String nombre=txtNombre.getText().toString();
        String apellido=txtApellido.getText().toString();
        String fecha=txtFecha.getText().toString();
        if(foro.equals(""))
        {
            txtForo.setError("Campo Vacio");
        }
        else if(nombre.equals(""))
        {
            txtNombre.setError("Campo Vacio");
        }
        else if(apellido.equals(""))
        {
            txtApellido.setError("Campo Vacio");
        }
        else if(fecha.equals(""))
        {
            txtFecha.setError("Campo Vacio");
        }
    }

}