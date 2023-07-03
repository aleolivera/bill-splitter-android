package com.example.billsplitter.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.billsplitter.entities.Expence;
import com.example.billsplitter.entities.Friend;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<List<Friend>> mFriends;

    public SharedViewModel() {
        mFriends = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Friend>> getFriends() {
        return mFriends;
    }

    public void addFriend(Friend friend){
        List<Friend> list = mFriends.getValue();

        if(friend != null && list != null){
            list.add(friend);
            mFriends.setValue(list);
        }
    }

    public void removeFriend(int index){
        List<Friend> list = (mFriends.getValue() != null)
                ? mFriends.getValue() : new ArrayList<>();

        if(index > -1 && index < mFriends.getValue().size()){
            list.remove(index);
            mFriends.setValue(list);
        }
    }
    public void setExpences(int friendIndex,List<Expence> expences){
        List<Friend> list = (mFriends.getValue() != null)
                ? mFriends.getValue() : new ArrayList<>();

        list.get(friendIndex).setExpences(expences);
        mFriends.setValue(list);
    }

    public void AddExpence(int friendIndex,Expence expence){
        List<Friend> list = (mFriends.getValue() != null)
                ? mFriends.getValue() : new ArrayList<>();

        list.get(friendIndex).getExpences().add(expence);
        mFriends.setValue(list);
    }

    public void setPaying(int friendIndex, boolean isPaying){
        List<Friend> list = (mFriends.getValue() != null)
                ? mFriends.getValue() : new ArrayList<>();

        list.get(friendIndex).setPaying(isPaying);
        mFriends.setValue(list);
    }
}