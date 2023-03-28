package com.tmhnry.swade.viewmodel;

import androidx.lifecycle.ViewModelStoreOwner;

public class HomeFragmentViewModel implements Factory.Product<HomeFragmentViewModel.Model> {

    public HomeFragmentViewModel(){}

    public Model getModel(ViewModelStoreOwner owner) {
        return Factory.get(owner, this, Model.class);
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T onCreate() {
        return (T) new Model("");
    }

    public static class Model extends androidx.lifecycle.ViewModel {
        String name;

        public Model(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}