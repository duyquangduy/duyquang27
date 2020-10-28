package com.example.livedataandmvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {

    @SerializedName("ticketid")
    @Expose
    public String ticketId;

    @SerializedName("ticketname")
    @Expose
    public String ticketName;

    @SerializedName("ticketdesc")
    @Expose
    public String ticketDesc;

    public Ticket() {
    }

    public Ticket(String ticketId, String ticketName, String ticketDesc) {
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.ticketDesc = ticketDesc;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketDesc() {
        return ticketDesc;
    }

    public void setTicketDesc(String ticketDesc) {
        this.ticketDesc = ticketDesc;
    }
}
