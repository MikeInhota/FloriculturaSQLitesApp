package br.com.etecia.florsqliteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PlantaAdapter extends BaseAdapter {

    Context mCtx;
    int listaLayoutRes;
    List<PlantasCad> plantasCadList;
    SQLiteDatabase myBD;

    public PlantaAdapter(Context mCtx, int listaLayoutRes, List<PlantasCad> plantasCadList, SQLiteDatabase myBD) {
        this.mCtx = mCtx;
        this.listaLayoutRes = listaLayoutRes;
        this.plantasCadList = plantasCadList;
        this.myBD = myBD;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listaLayoutRes, null);

        final PlantasCad plantasCad = plantasCadList.get(position);

        TextView txtViewnome = view.findViewById(R.id.txtNomePlantaView);
        TextView txttViewTipo = view.findViewById(R.id.txtTipoView);
        TextView txtViewPreco = view.findViewById(R.id.txtPrecoPlantaView);
        TextView txtViewDataRegistro = view.findViewById(R.id.txtRegistroview);

        txtViewnome.setText(plantasCad.getNome());
        txttViewTipo.setText(plantasCad.getTipo());
        txtViewPreco.setText(String.valueOf(plantasCad.getPreco()));
        txtViewDataRegistro.setText((int) plantasCad.getDataRegistro());

        Button btnExcluir = view.findViewById(R.id.btnExcluirViewPlanta);
        Button btnEditar = view.findViewById(R.id.btnEditarViewPlanta);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarPlantasCad(plantasCad);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Tem certeza de Excluir?");
                builder.setIcon(android.R.drawable.ic_input_delete);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM cad_plantas WHERE ID = ?";
                        myBD.execSQL(sql, new Integer[]{Integer.valueOf(plantasCad.getId())});
                        recarregarPlantasCadBD();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    private void alterarPlantasCad(final PlantasCad plantasCad) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.box_alterar_planta,null);
        builder.setView(view);

        final Spinner spnAlterarTipo = view.findViewById(R.id.spnAlterarTipo);
        final EditText txtAlterarNomePlanta = view.findViewById(R.id.txtAlterarNomePlanta);
        final EditText txtAlterarPreco = view.findViewById(R.id.txtAlterarPreco);

        txtAlterarNomePlanta.setText(plantasCad.getNome());
        txtAlterarPreco.setText(String.valueOf(plantasCad.getPreco()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnAlterarPlantas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipo = spnAlterarTipo.getSelectedItem().toString().trim();
                String nome = txtAlterarNomePlanta.getText().toString().trim();
                String preco = txtAlterarPreco.getText().toString().trim();

                if (nome.isEmpty()) {
                    txtAlterarNomePlanta.setError("Nome da planta não pode ser vazio");
                    txtAlterarNomePlanta.requestFocus();
                    return;
                }
                if (preco.isEmpty()) {
                    txtAlterarPreco.setError("Preço da planta não pode ser vazio");
                    txtAlterarPreco.requestFocus();
                    return;
                }

                String sql = "UPDATE cad_plantas SET tipo = ?, nome = ?, preco = ? WHERE id = ?";
                myBD.execSQL(sql,
                        new String[]{tipo, nome, preco, String.valueOf(plantasCad.getId())});
                Toast.makeText(mCtx, "Planta alterada !", Toast.LENGTH_LONG).show();
                recarregarPlantasCadBD();

                dialog.dismiss();
            }
        });

    }

    public void  recarregarPlantasCadBD(){
        Cursor cursorPlantas = myBD.rawQuery("SELECT * FROM cad_plantas", null);
        if (cursorPlantas.moveToFirst()) {
            plantasCadList.clear();
            do {
                plantasCadList.add(new PlantasCad(
                        cursorPlantas.getInt(0),
                        cursorPlantas.getString(1),
                        cursorPlantas.getString(2),
                        cursorPlantas.getString(3),
                        cursorPlantas.getDouble(4)
                ));
            } while (cursorPlantas.moveToNext());
        }
        cursorPlantas.close();
        notifyDataSetChanged();
    }
}
