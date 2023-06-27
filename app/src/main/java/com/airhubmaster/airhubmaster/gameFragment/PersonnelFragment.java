package com.airhubmaster.airhubmaster.gameFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.adapter.PersonnelAdapter;
import com.airhubmaster.airhubmaster.dto.game.PersonnelBoughtDto;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonnelFragment extends Fragment {

    private RecyclerView recyclerView;
    private PersonnelAdapter adapter;
    //  private List<PersonnelDto> personnelDtoList;
    private List<PersonnelBoughtDto> personnelBoughtDtoList = new ArrayList<>();
    private RecyclerView recyclerViewHorizontal;
    private CategoryAdapter categoryAdapter;
    private List<String> categories;
    UserLocalStore userLocalStore;
    private final Gson gson = new Gson();

    public PersonnelFragment() {
    }

    public static PersonnelFragment newInstance() {
        PersonnelFragment fragment = new PersonnelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHirePersonnel();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewHorizontal = getView().findViewById(R.id.recyclerViewHorizontal);
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categories = new ArrayList<>();
        categories.add("pilot");
        categories.add("stewardessa");
        categories.add("personel naziemny");

        categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterPersonnelByCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(layoutManager);
        recyclerViewHorizontal.setAdapter(categoryAdapter);

//        personnelDtoList = new ArrayList<>();
//        personnelDtoList.add(new PersonnelDto("Grzegorz Floryda", "Pilot", 69, 10, 20, 30));
//        personnelDtoList.add(new PersonnelDto("Jane Smith", "Stweardessa", 80, 50, 70, 90));
//        personnelDtoList.add(new PersonnelDto("Derek Chauvin", "Personel Naziemny", 40, 30, 40, 50));
//        personnelDtoList.add(new PersonnelDto("Bob Brown", "Personel Naziemny", 60, 80, 10, 20));

        adapter = new PersonnelAdapter(personnelBoughtDtoList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personnel, container, false);
    }

    private void filterPersonnelByCategory(String category) {
        if (category == null) {
            adapter.updatePersonnel(personnelBoughtDtoList);
        } else {
            List<PersonnelBoughtDto> filteredPersonnelBoughtDto = new ArrayList<>();
            for (PersonnelBoughtDto personnelBoughtDto : personnelBoughtDtoList) {
                if (personnelBoughtDto.getCategoryName().equals(category)) {
                    filteredPersonnelBoughtDto.add(personnelBoughtDto);
                }
            }
            adapter.updatePersonnel(filteredPersonnelBoughtDto);
        }
    }

    //==============================================================================================

    /**
     * The method responsible for retrieving personnel data assigned to a given user
     */
    private void getHirePersonnel() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/bought/workers";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", "Bearer " + userLocalStore.getJwtUserToken())
                .header("Connection", "close")
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenExpiredInterceptor(userLocalStore))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                        MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    Type listType = TypeToken.getParameterized(List.class, PersonnelBoughtDto.class).getType();
                    personnelBoughtDtoList = gson.fromJson(response.body().string(), listType);
                    getActivity().runOnUiThread(() -> {
                        adapter = new PersonnelAdapter(personnelBoughtDtoList);
                        recyclerView.setAdapter(adapter);
                    });
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
