package com.example.danni.firebasetabs;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosAgrupadosFragment extends Fragment {

    private ListView listaProdAgrupados;

    private ArrayList<Pedidos_DataBase> productos_todos;
    private ArrayList<Pedidos_Agrupados> pedidos_tiendas;
    private FirebaseDatabase database;
    String clienteId;


    public PedidosAgrupadosFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedidos_agrupados, container, false);
        listaProdAgrupados=(ListView)view.findViewById(R.id.listaPedidosAgrupados);

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("SharedPreferences", getActivity().MODE_PRIVATE);
        clienteId = sharedPrefs.getString("clienteId","vacio");
        String a = sharedPrefs.getString("numPedidos","finco");
        Toast.makeText(getContext(),a,Toast.LENGTH_SHORT).show();

        //todos los pedidos son todos
        productos_todos = new ArrayList<Pedidos_DataBase>();
        //pedidos por tienda
        pedidos_tiendas = new ArrayList<Pedidos_Agrupados>();




        final Adapter adapter = new Adapter(getActivity(),pedidos_tiendas);
        listaProdAgrupados.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pedidos_Cliente").child(clienteId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //padre : id tienda, hijo : num pedido, hijo2: cosos del pedido
                for (DataSnapshot padre : dataSnapshot.getChildren()){

                    for (DataSnapshot hijo : padre.getChildren()){
                        productos_todos.clear();  //nuevo numero de pedido
                        int cantidadTotal=0,precioTotal=0;
                        String nombreTienda="vacio",numeroPedido="vacio",tiendaId="vacio";

                        for (DataSnapshot hijo2 : hijo.getChildren()){
                            //agrega todos los prod
                            productos_todos.add(hijo2.getValue(Pedidos_DataBase.class));
                        }//fin productos . Sigo en pedido, sumar cantidades
                        for(int i=0;i<productos_todos.size();i++) {
                            cantidadTotal += Integer.valueOf(productos_todos.get(i).getCantidad());
                            precioTotal += Integer.valueOf(productos_todos.get(i).getTotal());
                            nombreTienda = productos_todos.get(i).getNombreTienda();
                            numeroPedido = productos_todos.get(i).getNumPedido();
                            tiendaId = productos_todos.get(i).getTiendaId();



                        }//ahora crear los agrupados

                        pedidos_tiendas.add(new Pedidos_Agrupados(String.valueOf(cantidadTotal)
                                ,String.valueOf(precioTotal),nombreTienda,numeroPedido,tiendaId));

                    }//fin numero de pedido
                }//fin tienda
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listaProdAgrupados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getContext(),String.valueOf(position), Toast.LENGTH_SHORT).show();
                String nombre = pedidos_tiendas.get(position).getNombreTienda();
                String numeroPedido = pedidos_tiendas.get(position).getNumeroPedido();
                String tiendaId = pedidos_tiendas.get(position).getTiendaId();
                Intent intent = new Intent(getActivity(),PedidoActivity.class);
                intent.putExtra("TiendaId",tiendaId);
                intent.putExtra("TiendaNombre",nombre);
                intent.putExtra("NumeroPedido",numeroPedido);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });



        return view;
    }



    class Adapter extends ArrayAdapter<Pedidos_Agrupados> {
        public Adapter(Context context, ArrayList<Pedidos_Agrupados> pedidos) {
            super(context, R.layout.lista_pedidos_small,pedidos);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(getContext());
            View item =inflater.inflate(R.layout.lista_pedidos_small,null);

            Pedidos_Agrupados pedido = getItem(position);

            TextView tNombre=(TextView)item.findViewById(R.id.tCostoTotal);
            tNombre.setText(pedido.getTotal());
            TextView tNegocio=(TextView)item.findViewById(R.id.tNombre);
            tNegocio.setText(pedido.getNombreTienda());
            TextView tTiempoEnvio=(TextView)item.findViewById(R.id.tCantidad);
            tTiempoEnvio.setText(pedido.getCantidad());
            return item;
        }

    }

}
