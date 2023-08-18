package com.example.appstyle.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TreinoViewModel extends ViewModel {
    private MutableLiveData<String> treinoDoDia = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> treinos = new MutableLiveData<>();

    public LiveData<String> getTreinoDoDia() {
        return treinoDoDia;
    }

    public LiveData<ArrayList<String>> getTreinos() {
        return treinos;
    }

    public void setTreinoDoDia(String treino) {
        treinoDoDia.setValue(treino);
    }

    public void setTreinos(ArrayList<String> treinosArray) {
        treinos.setValue(treinosArray);
    }
}
