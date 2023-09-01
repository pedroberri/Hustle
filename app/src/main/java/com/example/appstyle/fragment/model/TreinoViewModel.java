package com.example.appstyle.fragment.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TreinoViewModel extends ViewModel {
    private MutableLiveData<String> quote = new MutableLiveData<>();
    private MutableLiveData<String> treinoDoDia = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> treinos = new MutableLiveData<>();

    public LiveData<ArrayList<String>> getTreinos() {
        return treinos;
    }

    public LiveData<String> getTreinoDoDia() {
        return treinoDoDia;
    }

    public void setTreinoDoDia(String treino) {
        treinoDoDia.setValue(treino);
    }

    public void setTreinos(ArrayList<String> treinosArray) {
        treinos.setValue(treinosArray);
    }



    public LiveData<String> getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote.setValue(quote);
    }
}
