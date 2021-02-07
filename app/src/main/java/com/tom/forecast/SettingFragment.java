package com.tom.forecast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tom.forecast.R;
import com.tom.forecast.bean.City;
import com.tom.forecast.bean.Setting;
import com.tom.forecast.utils.FileManage;
import com.tom.forecast.utils.SettingUpdate;
import com.tom.forecast.utils.WeatherAPI;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingFragment extends Fragment {

    SettingUpdate settingUpdate;

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.setting_list,container,false);
        recyclerView=view.findViewById(R.id.setting_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        settingUpdate=new SettingUpdate(getContext());
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onResume() {
        super.onResume();
        updateUi();
    }

    class ListHolder extends RecyclerView.ViewHolder{

        public TextView settingItemTitle,settingItemContent;
        public CheckBox settingCheckBox;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            this.settingCheckBox=itemView.findViewById(R.id.setting_checkbox);
            this.settingItemContent=itemView.findViewById(R.id.setting_content);
            this.settingItemTitle=itemView.findViewById(R.id.setting_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(getAdapterPosition());
                }
            });

        }
    }

    class ListAdapter extends RecyclerView.Adapter<ListHolder>{

        private List<Setting> settingList;

        public ListAdapter(List<Setting> s ) {
            this.settingList = s;
        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.setting_list_item, parent, false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            Setting setting=settingList.get(position);
            holder.settingItemTitle.setText(setting.getTitle());
            holder.settingItemContent.setText(setting.getDetail());
            if(setting.isCheckable()){
                holder.settingCheckBox.setVisibility(View.VISIBLE);
                holder.settingCheckBox.setChecked(setting.isChecked());
                holder.settingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        onCheckboxChanged();
                    }
                });
            }else{
                holder.settingCheckBox.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return settingList.size();
        }
    }

    private void onItemClick(int position) {

        switch (position) {
            case 0:
                Log.i("THIS TAG","!!!!!!!!!!!!!!!!!!!!!");
                showChooseCityDialog();
                break;
            case 1:
                showChooseUnitsDialog();
                Log.i("THIS TAG","!!!!!!!!!!?????!!!!!!!");
                break;
            case 2:
                onCheckboxChanged();
                Log.i("THIS TAG","!!!!!!!........!!!!!!!!");
                break;
        }
    }

    private void updateUi() {
        List<Setting> settingsList = settingUpdate.getSettingObject();
        ListAdapter adapter = new ListAdapter(settingsList);
        recyclerView.setAdapter(adapter);
    }

    private void showChooseCityDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Location");
        List<City> cityList=null;
        cityList = FileManage.getCityList(getContext(),cityList);
        String currentCity = settingUpdate.getCurrentCity();
        int index = 0;
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getCityId().equals(currentCity)) {
                index = i;
                break;
            }
        }
        List<String> finalCityList=new ArrayList<>();
        for(City city:cityList){
            finalCityList.add(city.getCityName());
        }
        String[] finalCity =new String[finalCityList.size()];
        finalCityList.toArray(finalCity);
        List<City> finalCityList1 = cityList;
        builder.setSingleChoiceItems(finalCity, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settingUpdate.changeCity(finalCityList.get(which));
                List<String> cityId=new ArrayList<>();
                cityId.add(finalCityList1.get(which).getCityId());
                FileManage.saveLocationCity(getContext(),new City(finalCityList1.get(which).getCityName(),cityId.get(0)));
                dialog.dismiss();
                updateUi();
            }
        });
        builder.setNeutralButton("add new city", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showAddCityDialog();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showAddCityDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add New City");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city, null);
        builder.setView(view);

        final EditText mCityName = view.findViewById(R.id.add_city_cityName);
        final Button mSubmit = view.findViewById(R.id.add_city_submit);
        final Button mCancel = view.findViewById(R.id.add_city_cancel);

        final AlertDialog dialog = builder.show();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = mCityName.getText().toString().trim();

                if (cityName.equals("")) {
                    Toast.makeText(getActivity(), "No Empty Input", Toast.LENGTH_SHORT).show();
                } else {

                    WeatherAPI weatherAPI=new WeatherAPI();
                    String id = null;
                    try {
                        id=weatherAPI.getLocationID(cityName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (id!=null && !id.equals("")) {
                        settingUpdate.changeCity(id);
                        List<City> cityList=null;
                        cityList = FileManage.getCityList(getContext(),cityList);
                        cityList.add(new City(cityName,id));
                        FileManage.saveCityList(getContext(),cityList);
                        dialog.dismiss();
                    }
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void showChooseUnitsDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Temperature Units");
        final String[] options = {"Metric", "Imperial"};
        boolean isCelsius=settingUpdate.isCelsius();
        int index;
        if(isCelsius){
            index=0;
        }else{
            index=1;
        }
        builder.setSingleChoiceItems(options, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = which == 0;
                settingUpdate.setCelsius(result);
                dialog.dismiss();
                updateUi();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void onCheckboxChanged(){
        boolean currentChoice=settingUpdate.isChecked();
        settingUpdate.setChecked(!currentChoice);
        FileManage.saveIsChecked(Objects.requireNonNull(this.getContext()),!currentChoice);
        updateUi();
    }

}
