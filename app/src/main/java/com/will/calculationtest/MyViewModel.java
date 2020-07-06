package com.will.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

public class MyViewModel extends AndroidViewModel {

    private SavedStateHandle mHandle;
    private static final String KEY_HIGH_SCORE = "key_high_score";
    private static final String KEY_LEFT_NUMBER = "key_left_number";
    private static final String KEY_RIGHT_NUMBER = "key_right_number";
    private static final String KEY_OPERATOR = "key_operator";
    private static final String KEY_ANSWER = "key_answer";
    private static final String KEY_CURRENT_SCORE = "key_current_score";
    private static final String SAVE_SHP_DATA_NAME = "save_shp_data_name";
    boolean winFlag = false;

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);

        if (!handle.contains(KEY_HIGH_SCORE)) {
            SharedPreferences shp = application.getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE, shp.getInt(KEY_HIGH_SCORE, 0));
            handle.set(KEY_LEFT_NUMBER, 0);
            handle.set(KEY_RIGHT_NUMBER, 0);
            handle.set(KEY_OPERATOR, "+");
            handle.set(KEY_ANSWER, 0);
            handle.set(KEY_CURRENT_SCORE, 0);

        }
        this.mHandle = handle;
    }

    public MutableLiveData<Integer> getLeftNumber() {
        return mHandle.getLiveData(KEY_LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNumber() {
        return mHandle.getLiveData(KEY_RIGHT_NUMBER);
    }

    public MutableLiveData<String> getOperator() {
        return mHandle.getLiveData(KEY_OPERATOR);
    }

    public MutableLiveData<Integer> getHighScore() {
        return mHandle.getLiveData(KEY_HIGH_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return mHandle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<Integer> getAnswer() {
        return mHandle.getLiveData(KEY_ANSWER);
    }

    public void generator() {
        int LEVEL = 20;
        Random random = new Random();
        int x, y;
        x = random.nextInt(LEVEL) + 1;
        y = random.nextInt(LEVEL) + 1;

        if (x%2==0) {
            getOperator().setValue("+");
            if (x>y) {
                getAnswer().setValue(x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x-y);
            } else {
                getAnswer().setValue(y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y-x);
            }
        } else {
            getOperator().setValue("-");
            if (x>y) {
                getAnswer().setValue(x-y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y);
            } else {
                getAnswer().setValue(y-x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void save(){
        SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_HIGH_SCORE,getHighScore().getValue());
        editor.apply();
    }

    @SuppressWarnings("ConstantConditions")
    public void answerCorrect(){
        getCurrentScore().setValue(getCurrentScore().getValue()+1);

        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            getHighScore().setValue(getCurrentScore().getValue());
            winFlag = true;
        }

        generator();
    }
}
