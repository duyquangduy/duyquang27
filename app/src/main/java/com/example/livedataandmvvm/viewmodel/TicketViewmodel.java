package com.example.livedataandmvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.livedataandmvvm.model.Ticket;
import com.example.livedataandmvvm.remote.RetroClass;

public class TicketViewmodel extends ViewModel {
    private Ticket ticket;
    private RetroClass retroClass = new RetroClass();
    private LiveData<Ticket> liveData;

    public Ticket getTicketVal(){
        if(ticket == null){
            ticket = retroClass.getTicket();
        }
        return ticket;
    }

    public LiveData<Ticket> getLiveData(){
        if(liveData == null){
            liveData = retroClass.getTicketLivedata();
        }
        return liveData;
    }

}
