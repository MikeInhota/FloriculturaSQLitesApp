package br.com.etecia.florsqliteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Plantas_Activity extends AppCompatActivity {
    List<PlantasCad> plantasCadList;
    PlantaAdapter plantaAdapter;
    SQLiteDatabase myBD;
    ListView listViewPlantas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantas_layout);

        listViewPlantas = findViewById(R.id.listarPlantasView);
        plantasCadList = new ArrayList<>();

        myBD = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        verPlantasDatabase();
    }

    private void verPlantasDatabase() {
        Cursor cursorPlantas = myBD.rawQuery("SELECT * FROM cad_plantas", null);

        if (cursorPlantas.moveToFirst()) {
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

        plantaAdapter = new PlantaAdapter(this, R.layout.lista_view_plantas, plantasCadList, myBD);

        listViewPlantas.setAdapter(plantaAdapter);
    }

}
