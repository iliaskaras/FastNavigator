package gr.onetouchaway.findeverything.fast_navigator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Model.Dustbin;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdd,btnDelete,btnEdit;
    EditText editUser;
    Dustbin dustbinSelected = null;
    List<Dustbin> dustbins = new ArrayList<Dustbin>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        editUser = (EditText) findViewById(R.id.editUsername);

    }


}
