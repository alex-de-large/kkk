package com.example.morina.ui.booking;

public class HistoryEntry {
    private final String status;
    private final String date;
    private final String from;
    private final String to;
    private final String time;
    private final String tarif;
    private final String card;

    public HistoryEntry(String status, String date, String from, String to, String time, String tarif, String card) {
        this.status = status;
        this.date = date;
        this.from = from;
        this.to = to;
        this.time = time;
        this.tarif = tarif;
        this.card = card;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getTime() {
        return time;
    }

    public String getTarif() {
        return tarif;
    }

    public String getCard() {
        return card;
    }
}
