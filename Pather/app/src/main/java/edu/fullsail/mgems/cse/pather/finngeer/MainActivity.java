package edu.fullsail.mgems.cse.pather.finngeer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button creditsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        creditsBtn = findViewById(R.id.btnCredits);
        creditsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == creditsBtn){
            makeDialog("Credits", "Finn Geer\nMGEMS | CSE\n 10/20/2019");
        }

    }

    private void makeDialog(String title, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
