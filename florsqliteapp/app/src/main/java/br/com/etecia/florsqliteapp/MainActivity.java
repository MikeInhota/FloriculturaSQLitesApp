package br.com.etecia.florsqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "plantas.db";

    Spinner spnTipo;
    EditText txtNomePlanta, txtPreco;
    Button btnAdicionarPlanta, btnVerListaPlantas;
    SQLiteDatabase myBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnTipo = findViewById(R.id.spnTipo);
        txtNomePlanta = findViewById(R.id.txtNomePlanta);
        txtPreco = findViewById(R.id.txtPreco);

        btnAdicionarPlanta = findViewById(R.id.btnAdicionarPlanta);
        btnVerListaPlantas = findViewById(R.id.btnVerListaPlantas);

        btnAdicionarPlanta.setOnClickListener(this);
        btnVerListaPlantas.setOnClickListener(this);

        myBD = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        criarTabelaCad_Plantas();
    }

    private void criarTabelaCad_Plantas() {
        myBD.execSQL(
                "CREATE TABLE IF NOT EXISTS cad_plantas ("+
                        "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "tipo VARCHAR(100),"+
                        "nome VARCHAR(200),"+
                        "preco DOUBLE,"+
                        "dataRegistro DATETIME);"
        );
    }

    private boolean verificarEntrada(String nome, String preco) {
        if (nome.isEmpty()) {
            txtNomePlanta.setError("O nome da planta é obrigatório");
            txtNomePlanta.requestFocus();
            return false;
        }

        if (preco.isEmpty() || Integer.parseInt(preco) <= 0) {
            txtPreco.setError("O preço da planta é obrigatório");
            txtPreco.requestFocus();
            return false;
        }
        return true;
    }

    private void adicionarPlanta(){
        String tipoDaPlanta = spnTipo.getSelectedItem().toString();
        String nomePlanta = txtNomePlanta.getText().toString().trim();
        String precoPlanta = txtPreco.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String dataRegistro = simpleDateFormat.format(calendar.getTime());

        if(verificarEntrada(nomePlanta, precoPlanta)){
            String insertSQL = "INSERT INTO cad_plantas ("+
                    "tipo,"+
                    "nome,"+
                    "preco,"+
                    "dataRegistro)"+
                    "VALUES(?,?,?,?);";
            myBD.execSQL(insertSQL, new String[]{tipoDaPlanta, nomePlanta, precoPlanta,dataRegistro});
            Toast.makeText(getApplicationContext(), "Planta cadastrada !",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdicionarPlanta:
                adicionarPlanta();
                break;
            case R.id.btnVerListaPlantas:
                startActivity(new Intent(getApplicationContext(), Plantas_Activity.class));
                break;
        }

    }
}