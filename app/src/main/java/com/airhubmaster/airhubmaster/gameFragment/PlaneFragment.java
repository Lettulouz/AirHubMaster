package com.airhubmaster.airhubmaster.gameFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.adapter.PlaneAdapter;
import com.airhubmaster.airhubmaster.dto.game.PlaneBoughtDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaneFragment extends Fragment {
    private RecyclerView recyclerView;
    private PlaneAdapter adapter;
    //private List<PlaneDto> planeDtoList;
    private List<PlaneBoughtDto> planeBoughtDtoList = new ArrayList<>();
    private RecyclerView recyclerViewHorizontal;
    private CategoryAdapter categoryAdapter;
    private List<String> categories;
    UserLocalStore userLocalStore;
    private final Gson gson = new Gson();

    public PlaneFragment() {
    }

    public static PlaneFragment newInstance() {
        PlaneFragment fragment = new PlaneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBoughtPlane();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewHorizontal = getView().findViewById(R.id.recyclerViewHorizontal);
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categories = new ArrayList<>();
        categories.add("mikro");
        categories.add("małe");
        categories.add("standardowe");
        categories.add("duże");
        categories.add("jumbo");

        categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterPlanesByCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(layoutManager);
        recyclerViewHorizontal.setAdapter(categoryAdapter);

//        planeBoughtDtoList = new ArrayList<>();
//        planeBoughtDtoList.add(new PlaneDto("Samolot A", "Kategoria1", 0));
//        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria2", 0));
//        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria3", 0));
//        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria4", 0));
//        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria5", 0));
//        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria1", 0));
//        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria2", 0));
//        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria3", 0));
//        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria4", 0));
//        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria5", 0));
//        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria1", 0));

        adapter = new PlaneAdapter(planeBoughtDtoList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plane, container, false);
    }

    private void filterPlanesByCategory(String category) {
        if (category == null) {
            adapter.updatePlanes(planeBoughtDtoList);
        } else {
            List<PlaneBoughtDto> filteredPlaneBoughtDto = new ArrayList<>();
            for (PlaneBoughtDto planeBoughtDto : planeBoughtDtoList) {
                if (planeBoughtDto.getCategoryName().equals(category)) {
                    filteredPlaneBoughtDto.add(planeBoughtDto);
                }
            }
            adapter.updatePlanes(filteredPlaneBoughtDto);
        }
    }

    //==============================================================================================

    /**
     * The method responsible for retrieving plane data assigned to a given user
     */
    private void getBoughtPlane() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/bought/planes";
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
                    Type listType = TypeToken.getParameterized(List.class, PlaneBoughtDto.class).getType();
                    planeBoughtDtoList = gson.fromJson(response.body().string(), listType);
                    getActivity().runOnUiThread(() -> {
                        adapter = new PlaneAdapter(planeBoughtDtoList);
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
