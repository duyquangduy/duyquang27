package com.example.livedataandmvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.livedataandmvvm.model.Ticket;
import com.example.livedataandmvvm.viewmodel.TicketViewmodel;

public class MainActivity extends AppCompatActivity {

    private TicketViewmodel ticketViewmodel;
    TextView txtid, txtname, txtdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtid = findViewById(R.id.txtid);
        txtname = findViewById(R.id.txtname);
        txtdesc = findViewById(R.id.txtdesc);

        ticketViewmodel = ViewModelProviders.of(this).get(TicketViewmodel.class);
        //ticketViewmodel.getTicketVal();  //khong dung livedata
        ticketViewmodel.getLiveData();   //dung livedata
    }

    public void getTokenView(View view) {
        String ticketIdVal = ticketViewmodel.getTicketVal().getTicketId().toString();
        txtid.setText(ticketIdVal);

        String ticketNameVal = ticketViewmodel.getTicketVal().getTicketName().toString();
        txtname.setText(ticketNameVal);

        String ticketDescVal = ticketViewmodel.getTicketVal().getTicketDesc().toString();
        txtdesc.setText(ticketDescVal);
    }

//    public void getTokenView(View view){
//        ticketIdVal = ticketViewmodel.getLiveData().getValue().getTicketId();
//        txtid.setText(ticketIdVal);
//
//        ticketNameVal = ticketViewmodel.getLiveData().getValue().getTicketName();
//        txtname.setText(ticketNameVal);
//
//        ticketDescVal = ticketViewmodel.getLiveData().getValue().getTicketDesc();
//        txtdesc.setText(ticketDescVal);
//    }
}
