package com.airhubmaster.airhubmaster.gameFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.app.Dialog;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.BuyPersonnelAdapter;
import com.airhubmaster.airhubmaster.adapter.BuyPlaneAdapter;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.dto.api.StandardMessageErrorDto;
import com.airhubmaster.airhubmaster.dto.game.PersonnelShopDto;
import com.airhubmaster.airhubmaster.dto.game.PlaneShopDto;
import com.airhubmaster.airhubmaster.dto.game.ShopConfirmedPurchaseDto;
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
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShopFragment extends Fragment {

    int idPersonnel;
    int idPlane;
    BuyPlaneAdapter buyPlaneAdapter;
    BuyPersonnelAdapter buyPersonnelAdapter;
    Dialog dialogPlane;
    TextView textViewBodyPlane;
    Button buttonPlaneAccept;
    Button buttonPlanetCancel;
    Dialog dialogPersonnel;
    TextView textViewBodyPersonnel;
    Button buttonPersonnelAccept;
    Button buttonPersonnelCancel;
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
    StandardMessageErrorDto standardMessageErrorDto;
    ShopConfirmedPurchaseDto shopConfirmedPurchaseDto;
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
        categories.add("pilot");
        categories.add("stewardessa");
        categories.add("personel naziemny");
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

        buyPersonnelAdapter = new BuyPersonnelAdapter(personnelShopList);
        recyclerViewShop.setAdapter(buyPersonnelAdapter);

        buttonShowPersonel.setOnClickListener(v -> {
            categories.clear();
            categories.add("pilot");
            categories.add("stewardessa");
            categories.add("personel naziemny");
            categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterItemsByCategory);
            recyclerViewCategoryShop.setAdapter(categoryAdapter);
            buyPersonnelAdapter = new BuyPersonnelAdapter(personnelShopList);
            recyclerViewShop.setAdapter(buyPersonnelAdapter);

            buyPersonnelAdapter.setOnAcceptListener((name, idItem, price) -> {
                idPersonnel= idItem;
                dialogPersonnel.show();
                textViewBodyPersonnel.setText("Czy na pewno chcesz zatrudnić pracownika " + name + " za cene " + price + "?");
            });
        });

        buttonShowPlane.setOnClickListener(v -> {
            categories.clear();
            categories.add("mikro");
            categories.add("małe");
            categories.add("standardowe");
            categories.add("duże");
            categories.add("jumbo");
            categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterItemsByCategory);
            recyclerViewCategoryShop.setAdapter(categoryAdapter);
            buyPlaneAdapter = new BuyPlaneAdapter(planeShopList);
            recyclerViewShop.setAdapter(buyPlaneAdapter);

            buyPlaneAdapter.setOnAcceptListener((name, idItem, price) -> {
                idPlane = idItem;
                dialogPlane.show();
                textViewBodyPlane.setText("Czy na pewno chcesz kupić samolot " + name + " za cene " + price + "?");
            });
        });

        setDialogHireWorker();
        setDialogBuyPlane();
        buttonPlaneAccept = dialogPlane.findViewById(R.id.buttonMarketPlaneAccept);
        buttonPlanetCancel = dialogPlane.findViewById(R.id.buttonMarketPlaneCancel);
        textViewBodyPlane = dialogPlane.findViewById(R.id.textViewMarketPlaneBody);
        buttonPlanetCancel.setOnClickListener(v -> dialogPlane.dismiss());
        buttonPlaneAccept.setOnClickListener(v1 -> buyShopPlane(idPlane));

        buttonPersonnelAccept = dialogPersonnel.findViewById(R.id.buttonMarketPersonnelAccept);
        buttonPersonnelCancel = dialogPersonnel.findViewById(R.id.buttonMarketPersonnelCancel);
        textViewBodyPersonnel = dialogPersonnel.findViewById(R.id.textViewMarketPersonnelBody);
        buttonPersonnelCancel.setOnClickListener(v -> dialogPersonnel.dismiss());
        buttonPersonnelAccept.setOnClickListener(v1 -> buyShopWorker(idPersonnel));
    }

    @Override
    public void onPause() {
        super.onPause();
        dialogPlane.dismiss();
        dialogPersonnel.dismiss();
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
                        buyPersonnelAdapter = new BuyPersonnelAdapter(personnelShopList);
                        recyclerViewShop.setAdapter(buyPersonnelAdapter);
                    });
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    //==============================================================================================

    /**
     * The method responsible for purchase and assigning the aircraft to the user account
     */
    private void buyShopPlane(int planeId) {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/shop/buy/plane/" + planeId;
        RequestBody body = RequestBody.create("", null);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
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
                if (response.code() == 201) {
                    shopConfirmedPurchaseDto = gson.fromJson(response.body().string(), ShopConfirmedPurchaseDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            shopConfirmedPurchaseDto.getMessage(), Toast.LENGTH_SHORT).show());
                    dialogPlane.dismiss();
                    replaceFragment(new PlaneFragment());
                } else if (response.code() == 400) {
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show());
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    //==============================================================================================

    /**
     * The method responsible for hire and assigning the worker to the user account
     */
    private void buyShopWorker(int workerId) {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/shop/buy/worker/" + workerId;
        RequestBody body = RequestBody.create("", null);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
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
                if (response.code() == 201) {
                    shopConfirmedPurchaseDto = gson.fromJson(response.body().string(), ShopConfirmedPurchaseDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            shopConfirmedPurchaseDto.getMessage(), Toast.LENGTH_SHORT).show());
                    dialogPersonnel.dismiss();
                    replaceFragment(new PersonnelFragment());
                } else if (response.code() == 400) {
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show());
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    //==============================================================================================

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMenu, fragment);
        fragmentTransaction.commit();
    }

    //==============================================================================================

    /**
     * The method responsible for create confirmed plane buy pop up dialog
     */
    public void setDialogBuyPlane() {
        dialogPlane = new Dialog(getActivity());
        dialogPlane.setContentView(R.layout.dialog_market_confirm_plane);
        dialogPlane.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.background_dialog));
        dialogPlane.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        InsetDrawable inset = new InsetDrawable(getActivity().getDrawable(R.drawable.background_dialog), 32, 0, 32, 0);
        dialogPlane.getWindow().setBackgroundDrawable(inset);
        dialogPlane.setCancelable(true);
    }

    //==============================================================================================

    /**
     * The method responsible for create confirmed worker hire pop up dialog
     */
    public void setDialogHireWorker() {
        dialogPersonnel = new Dialog(getActivity());
        dialogPersonnel.setContentView(R.layout.dialog_market_confirm_personnel);
        dialogPersonnel.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.background_dialog));
        dialogPersonnel.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        InsetDrawable inset = new InsetDrawable(getActivity().getDrawable(R.drawable.background_dialog), 32, 0, 32, 0);
        dialogPersonnel.getWindow().setBackgroundDrawable(inset);
        dialogPersonnel.setCancelable(true);
    }
}
