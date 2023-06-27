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
import com.airhubmaster.airhubmaster.adapter.ServiceAdapter;
import com.airhubmaster.airhubmaster.dto.game.PlaneServiceDto;
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

public class ServiceFragment extends Fragment {
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    private List<PlaneServiceDto> planeServiceDtoList = new ArrayList<>();
    // private List<PlaneDto> planeDtoList;
    private RecyclerView recyclerViewHorizontal;
    private CategoryAdapter categoryAdapter;
    private List<String> categories;
    UserLocalStore userLocalStore;
    private final Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontal);

        getServicePlanePersonnel();

        categories = new ArrayList<>();
        categories.add("mikro");
        categories.add("małe");
        categories.add("standardowe");
        categories.add("duże");
        categories.add("jumbo");

        adapter = new ServiceAdapter(planeServiceDtoList);
        recyclerView.setAdapter(adapter);


        // Set the category adapter
        categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterPlanesByCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(layoutManager);
        recyclerViewHorizontal.setAdapter(categoryAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    private void filterPlanesByCategory(String category) {
        if (category == null) {
            adapter.updatePlanes(planeServiceDtoList);
        } else {
            List<PlaneServiceDto> filteredPlaneServiceDto = new ArrayList<>();
            for (PlaneServiceDto planeServiceDto : planeServiceDtoList) {
                if (planeServiceDto.getCategoryName().equals(category)) {
                    filteredPlaneServiceDto.add(planeServiceDto);
                }
            }
            adapter.updatePlanes(filteredPlaneServiceDto);
        }
    }

    //==============================================================================================

    /**
     * The method responsible for retrieving personnel data assigned to a given user
     */
    private void getServicePlanePersonnel() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/workshop/planes";
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
                    Type listType = TypeToken.getParameterized(List.class, PlaneServiceDto.class).getType();
                    planeServiceDtoList = gson.fromJson(response.body().string(), listType);
                    getActivity().runOnUiThread(() -> {
                        adapter = new ServiceAdapter(planeServiceDtoList);
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
