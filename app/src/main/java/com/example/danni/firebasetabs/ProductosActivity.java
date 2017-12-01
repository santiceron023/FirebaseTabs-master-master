package com.example.danni.firebasetabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProductosActivity extends AppCompatActivity {

    private ListView listaProductos;
    private ArrayList<Productos> productos;
    String tiendaId,tiendaNombre,clienteId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef1,myRef2,myRef3;
    //ArrayList<Integer> ProductosArray = new ArrayList<Integer>();
    String numPedidos = "0";
    int[] ProductosArray = new int[20]; ///numero maximo de prod en pedido


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        listaProductos = (ListView) findViewById(R.id.listaProductos);

        //desde el main
        Bundle extras = getIntent().getExtras();
        tiendaId = extras.getString("TiendaId");
        tiendaNombre = extras.getString("TiendaNombre");

        //preferencias compartidas
        SharedPreferences sharedPrefs = getSharedPreferences("SharedPreferences", this.MODE_PRIVATE);
        numPedidos = sharedPrefs.getString("numPedidos", "1");
        clienteId = sharedPrefs.getString("clienteId","vacio");

        //list aproductos
        productos = new ArrayList<Productos>();
        final Adapter adapter = new Adapter(getApplicationContext(), productos);
        listaProductos.setAdapter(adapter);


        myRef1 = database.getReference("Productos").child(tiendaId);
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    productos.add(datasnapshot.getValue(Productos.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }


    class Adapter extends ArrayAdapter<Productos> {
        public Adapter(Context context, ArrayList<Productos> productos) {
            super(context, R.layout.lista_productos, productos);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.lista_productos, null);

            final Productos productos = getItem(position);

            TextView tNombre = (TextView) item.findViewById(R.id.tNombre);
            tNombre.setText(productos.getNombre());
            TextView tNegocio = (TextView) item.findViewById(R.id.tCantidadPresentacion);
            tNegocio.setText(productos.getTamaño());
            TextView tTiempoEnvio = (TextView) item.findViewById(R.id.tMarca);
            tTiempoEnvio.setText(productos.getMarca());
            TextView tPedidoMin = (TextView) item.findViewById(R.id.tCostoProducto);
            tPedidoMin.setText(productos.getPrecio());

            Button add = (Button) item.findViewById(R.id.bAddProduct);
            Button prodOk = (Button) item.findViewById(R.id.bProductosOk);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getContext(),"CLicked :"+String.valueOf(position),Toast.LENGTH_SHORT).show();
                    //ProductosArray.add(numPedidos, position);
                    ProductosArray[position] += 1;
                }
            });
            return item;
        }

    }
    public void ok(View view) {

        //auemntar pedidos
        Integer aux_cont = Integer.valueOf(numPedidos);
        aux_cont++;
        numPedidos = String.valueOf(aux_cont);

        //Toast.makeText(getApplicationContext(), String.valueOf(ProductosArray[1]), Toast.LENGTH_SHORT).show();
        for (int factor = 0; factor < 20; factor++) {
            //lee la cantida en el arreglo
            if (ProductosArray[factor] > 0) {
                funcion(factor,ProductosArray[factor]);
                funcion2(factor,ProductosArray[factor]);
            }
        }



        SharedPreferences sharedPrefs = getSharedPreferences("SharedPreferences", this.MODE_PRIVATE);
        SharedPreferences.Editor editorSP = sharedPrefs.edit();
        editorSP.putString("numPedidos",numPedidos);
        editorSP.commit();
        finish();
    }
    private void funcion(final int pos,final int cantidad){
        String Nombre = productos.get(pos).getNombre();
        String precio = productos.get(pos).getPrecio();
        int total = Integer.valueOf(precio);
        total *= cantidad;
        // tiendaNombre
        Pedidos_DataBase pedido_database = new Pedidos_DataBase(String.valueOf(cantidad),
                String.valueOf(total),Nombre,tiendaNombre,numPedidos,tiendaId);
        myRef2 = database.getReference("Pedidos_Cliente").child(clienteId).child(tiendaId).
                child(numPedidos).child(Nombre);
        myRef2.setValue(pedido_database);


    }
    private void funcion2(final int pos,final int cantidad){
        String Nombre = productos.get(pos).getNombre();
        myRef3 = database.getReference("Pedidos_Tienda").child(tiendaId).child(clienteId).child(numPedidos);
        myRef3.child(Nombre).setValue(cantidad);
    }
}
