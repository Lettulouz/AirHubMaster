package com.airhubmaster.airhubmaster.gameFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.BuyPersonnelAdapter;
import com.airhubmaster.airhubmaster.adapter.BuyPlaneAdapter;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.dto.game.PersonnelShopDto;
import com.airhubmaster.airhubmaster.dto.game.PlaneShopDto;
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

public class ShopFragment extends Fragment {

    UserLocalStore userLocalStore;
    private RecyclerView recyclerViewShop;
    private RecyclerView recyclerViewCategoryShop;
    //  private List<PersonnelDto> personnelList;
    private List<PersonnelShopDto> personnelShopList = new ArrayList<>();
    // private List<PlaneDto> planeList;
    private List<PlaneShopDto> planeShopList = new ArrayList<>();
    private Button buttonShowPersonel;
    private Button buttonShowPlane;
    private List<String> categories;
    private CategoryAdapter categoryAdapter;
    private String selectedCategory = null;
    private final Gson gson = new Gson();

    public ShopFragment() {
    }

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getShopPersonnel();
        getShopPlanes();
//        personnelList = new ArrayList<>();
//        planeList = new ArrayList<>();
//        planeList.add(new PlaneDto("Samolot A", "Kategoria1", 0));
//        planeList.add(new PlaneDto("Samolot B", "Kategoria2", 0));
//        planeList.add(new PlaneDto("Samolot C", "Kategoria3", 0));
//        planeList.add(new PlaneDto("Samolot B", "Kategoria4", 0));
//        planeList.add(new PlaneDto("Samolot C", "Kategoria5", 0));
//        personnelList.add(new PersonnelDto("Grzegorz Floryda", "Pilot", 69, 10, 20, 30));
//        personnelList.add(new PersonnelDto("Jane Smith", "Stweardessa", 80, 50, 70, 90));
//        personnelList.add(new PersonnelDto("Derek Chauvin", "Personel Naziemny", 40, 30, 40, 50));
//        personnelList.add(new PersonnelDto("Bob Brown", "Personel Naziemny", 60, 80, 10, 20));
        categories = new ArrayList<>();
        categories.add("Pilot");
        categories.add("Stewardess");
        categories.add("Personel naziemny");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewShop = view.findViewById(R.id.recyclerViewShop);
        recyclerViewCategoryShop = view.findViewById(R.id.recyclerViewCategoryShop);
        buttonShowPersonel = view.findViewById(R.id.buttonShowPersonel);
        buttonShowPlane = view.findViewById(R.id.buttonShowPlane);

        recyclerViewShop.setHasFixedSize(true);
        recyclerViewShop.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterItemsByCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryShop.setLayoutManager(layoutManager);
        recyclerViewCategoryShop.setAdapter(categoryAdapter);

        BuyPersonnelAdapter buypersonnelAdapter = new BuyPersonnelAdapter(personnelShopList);
        recyclerViewShop.setAdapter(buypersonnelAdapter);

        buttonShowPersonel.setOnClickListener(v -> {
            categories.clear();
            categories.add("Pilot");
            categories.add("Stewardess");
            categories.add("Personel naziemny");
            categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterItemsByCategory);
            recyclerViewCategoryShop.setAdapter(categoryAdapter);
            recyclerViewShop.setAdapter(new BuyPersonnelAdapter(personnelShopList));
        });

        buttonShowPlane.setOnClickListener(v -> {
            categories.clear();
            categories.add("Mikro");
            categories.add("Małe");
            categories.add("Standardowe");
            categories.add("Duże");
            categories.add("Jumbo");
            categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterItemsByCategory);
            recyclerViewCategoryShop.setAdapter(categoryAdapter);
            recyclerViewShop.setAdapter(new BuyPlaneAdapter(planeShopList));
        });
    }

    private void filterItemsByCategory(String category) {
        selectedCategory = category;
        if (selectedCategory == null) {
            if (recyclerViewShop.getAdapter() instanceof BuyPersonnelAdapter) {
                ((BuyPersonnelAdapter) recyclerViewShop.getAdapter()).updatePersonnel(personnelShopList);
            } else if (recyclerViewShop.getAdapter() instanceof BuyPlaneAdapter) {
                ((BuyPlaneAdapter) recyclerViewShop.getAdapter()).updatePlanes(planeShopList);
            }
        } else {
            if (recyclerViewShop.getAdapter() instanceof BuyPersonnelAdapter) {
                List<PersonnelShopDto> filteredPersonnelList = new ArrayList<>();
                for (PersonnelShopDto personnel : personnelShopList) {
                    if (personnel.getCategoryName().equals(selectedCategory)) {
                        filteredPersonnelList.add(personnel);
                    }
                }
                ((BuyPersonnelAdapter) recyclerViewShop.getAdapter()).updatePersonnel(filteredPersonnelList);
            } else if (recyclerViewShop.getAdapter() instanceof BuyPlaneAdapter) {
                List<PlaneShopDto> filteredPlaneList = new ArrayList<>();
                for (PlaneShopDto plane : planeShopList) {
                    if (plane.getCategoryName().equals(selectedCategory)) {
                        filteredPlaneList.add(plane);
                    }
                }
                ((BuyPlaneAdapter) recyclerViewShop.getAdapter()).updatePlanes(filteredPlaneList);
            }
        }
    }

    //==============================================================================================

    /**
     * The method responsible for downloading aircraft data
     */
    private void getShopPlanes() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/shop/planes";
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
                    Type listType = TypeToken.getParameterized(List.class, PlaneShopDto.class).getType();
                    planeShopList = gson.fromJson(response.body().string(), listType);
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    //==============================================================================================

    /**
     * The method responsible for downloading personnel data
     */
    private void getShopPersonnel() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/shop/workers";
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
                    Type listType = TypeToken.getParameterized(List.class, PersonnelShopDto.class).getType();
                    personnelShopList = gson.fromJson(response.body().string(), listType);
                    getActivity().runOnUiThread(() -> {
                        BuyPersonnelAdapter buypersonnelAdapter = new BuyPersonnelAdapter(personnelShopList);
                        recyclerViewShop.setAdapter(buypersonnelAdapter);
                    });
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
