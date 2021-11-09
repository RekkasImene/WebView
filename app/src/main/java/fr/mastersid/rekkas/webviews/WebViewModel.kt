package fr.mastersid.rekkas.webviews


import androidx.lifecycle.ViewModel

import androidx.lifecycle.MutableLiveData
class WebViewModel : ViewModel() {

    val _loadInside: MutableLiveData<String> = MutableLiveData(DEFAULT_URL)



    companion object{
        const val DEFAULT_URL="http://mastersid.univ-rouen.fr"
    }
}