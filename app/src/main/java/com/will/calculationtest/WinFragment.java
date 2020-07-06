package com.will.calculationtest;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.calculationtest.databinding.FragmentWinBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinFragment} factory method to
 * create an instance of this fragment.
 */
public class WinFragment extends Fragment {


    public WinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyViewModel myViewModel = new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),requireActivity())).get(MyViewModel.class);

        FragmentWinBinding binding;

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_win,container,false);

        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());

        binding.button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_winFragment_to_titleFragment);
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}