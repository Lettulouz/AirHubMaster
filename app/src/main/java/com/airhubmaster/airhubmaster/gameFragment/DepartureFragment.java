package com.airhubmaster.airhubmaster.gameFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.app.Dialog;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.adapter.SendPlaneAdapter;
import com.airhubmaster.airhubmaster.dto.api.StandardMessageErrorDto;
import com.airhubmaster.airhubmaster.dto.game.PlaneSendResponseDto;
import com.airhubmaster.airhubmaster.dto.game.PlaneSetRoutDto;
import com.airhubmaster.airhubmaster.dto.game.RoutePlaneRequestDto;
import com.airhubmaster.airhubmaster.dto.game.RoutePlaneResponseDto;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.airhubmaster.airhubmaster.utils.Constans;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DepartureFragment extends Fragment {

    int idPlaneSend;
    ProgressBar currentProgress;
    ProgressBar beforeProgress;
    TextView isLvlDialog;
    TextView balanceDialog;
    TextView arrivalDataDialog;
    TextView currentLvlDialog;
    TextView nextLvlDialog;
    TextView expGainDialog;
    TextView cashGainDialog;
    Button buttonAcceptDialog;
    Button buttonCancelDialog;
    Dialog dialogSendPlane;
    private RecyclerView recyclerViewDeparture;
    private SendPlaneAdapter sendPlaneAdapter;
    private ArrayList<PlaneSetRoutDto> planeDtoList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerViewHorizontal;
    private List<String> categories;
    StandardMessageErrorDto standardMessageErrorDto;
    PlaneSendResponseDto planeSendResponseDto;
    RoutePlaneResponseDto routePlaneResponseDto;
    UserLocalStore userLocalStore;
    private final Gson gson = new Gson();

    public DepartureFragment() {
    }

    public static DepartureFragment newInstance(String param1, String param2) {
        DepartureFragment fragment = new DepartureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReadyPlane();
        setDialogSendPlane();
        recyclerViewDeparture = view.findViewById(R.id.recyclerViewDeparture);
        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewDepartureCategory);
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

        recyclerViewDeparture.setLayoutManager(new LinearLayoutManager(getContext()));
        sendPlaneAdapter = new SendPlaneAdapter(planeDtoList, getContext());
        recyclerViewDeparture.setAdapter(sendPlaneAdapter);

        buttonAcceptDialog = dialogSendPlane.findViewById(R.id.accept_button_departure);
        buttonCancelDialog = dialogSendPlane.findViewById(R.id.cancel_button_departure);

        buttonCancelDialog.setOnClickListener(v -> {
            cancelSendPlaneTrip(idPlaneSend);
            dialogSendPlane.dismiss();
        });

        buttonAcceptDialog.setOnClickListener(v -> sendPlaneTrip(idPlaneSend));


        currentLvlDialog = dialogSendPlane.findViewById(R.id.current_level_departure);
        nextLvlDialog = dialogSendPlane.findViewById(R.id.next_level_departure);

        cashGainDialog = dialogSendPlane.findViewById(R.id.cash_gain_departure);
        expGainDialog = dialogSendPlane.findViewById(R.id.xp_gain_departure);

        balanceDialog = dialogSendPlane.findViewById(R.id.account_balance_departure);
        arrivalDataDialog = dialogSendPlane.findViewById(R.id.arrival_time_departure);

        currentProgress = dialogSendPlane.findViewById(R.id.level_progress_current);
        beforeProgress = dialogSendPlane.findViewById(R.id.level_progress_additional);
        isLvlDialog = dialogSendPlane.findViewById(R.id.next_lvl_departure);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_departure, container, false);
    }


    private void filterPlanesByCategory(String category) {
        if (category == null) {
            sendPlaneAdapter.updatePlanes(routePlaneResponseDto.getPlanes());
        } else {
            List<PlaneSetRoutDto> filteredPlaneSetRoutDto = new ArrayList<>();
            for (PlaneSetRoutDto planeSetRoutDto : routePlaneResponseDto.getPlanes()) {
                if (planeSetRoutDto.getPlaneCategory().equals(category)) {
                    filteredPlaneSetRoutDto.add(planeSetRoutDto);
                }
            }
            sendPlaneAdapter.updatePlanes(filteredPlaneSetRoutDto);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dialogSendPlane.dismiss();
    }

    //==============================================================================================

    /**
     * The method responsible for create confirmed plane buy pop up dialog
     */
    public void setDialogSendPlane() {
        dialogSendPlane = new Dialog(getActivity());
        dialogSendPlane.setContentView(R.layout.activity_custom_dialog);
        dialogSendPlane.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.background_dialog));
        dialogSendPlane.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        InsetDrawable inset = new InsetDrawable(getActivity().getDrawable(R.drawable.background_dialog), 32, 0, 32, 0);
        dialogSendPlane.getWindow().setBackgroundDrawable(inset);
        dialogSendPlane.setCancelable(false);
    }

    //==============================================================================================

    /**
     * The method responsible for retrieving aircraft data with assigned personnel
     */
    private void getReadyPlane() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/game/planes/routes";
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
                    routePlaneResponseDto = gson.fromJson(response.body().string(), RoutePlaneResponseDto.class);
                    getActivity().runOnUiThread(() -> {
                        planeDtoList = routePlaneResponseDto.getPlanes();
                        sendPlaneAdapter = new SendPlaneAdapter(planeDtoList, getContext());
                        recyclerViewDeparture.setAdapter(sendPlaneAdapter);
                        sendPlaneAdapter.setOnSendPlaneListener((planeId, crew, routeId) -> {
                            idPlaneSend = planeId;
                            dialogSendPlane.show();
                            getRoutInfo(planeId, crew, routeId);
                        });
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
     * The method responsible for returning flight data
     */
    private void getRoutInfo(int planeId, List<Integer> crew, int routeId) {
        userLocalStore = UserLocalStore.getInstance(getActivity());
        RoutePlaneRequestDto routePlaneRequestDto = new RoutePlaneRequestDto(planeId, crew, routeId);

        RequestBody body = RequestBody.create(gson.toJson(routePlaneRequestDto), Constans.JSON);
        String url = URL_SERVER + "api/v1/game/stats";
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
                if (response.code() == 200) {
                    planeSendResponseDto = gson.fromJson(response.body().string(), PlaneSendResponseDto.class);
                    getActivity().runOnUiThread(() -> {
                        currentLvlDialog.setText(String.valueOf(planeSendResponseDto.getCurrentLevel()));
                        nextLvlDialog.setText((String.valueOf(planeSendResponseDto.getCurrentLevel() + 1)));
                        isLvlDialog.setVisibility(View.INVISIBLE);
                        cashGainDialog.setText("+" + String.valueOf(planeSendResponseDto.getPrize()) + "$");
                        expGainDialog.setText("+" + String.valueOf(planeSendResponseDto.getAddedExp()) + "xp");
                        balanceDialog.setText("Stan konta: " + String.valueOf(planeSendResponseDto.getAccountDeposit()) + "$");

                        DateTimeFormatter formatterFirts = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            formatterFirts = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                        }
                        ZonedDateTime zonedDateTime = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            zonedDateTime = ZonedDateTime.parse(planeSendResponseDto.getArrival(), formatterFirts);
                        }

                        DateTimeFormatter formatter = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        }
                        String formattedDateTime = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            formattedDateTime = zonedDateTime.format(formatter);
                        }
                        beforeProgress.setMax((int) planeSendResponseDto.getToLevel());
                        beforeProgress.setProgress((int) planeSendResponseDto.getCurrentExp());

                        currentProgress.setMax((int) planeSendResponseDto.getToLevel());
                        currentProgress.setProgress((int) planeSendResponseDto.getAddedExp() + (int) planeSendResponseDto.getCurrentExp());
                        if (planeSendResponseDto.isUpgraded()) {
                            isLvlDialog.setVisibility(View.VISIBLE);
                            currentProgress.setProgress((int) planeSendResponseDto.getToLevel());
                        }
                        arrivalDataDialog.setText("Przylot: " + formattedDateTime);
                    });
                } else if (response.code() == 404) {
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
     * The method responsible for sending the plane on a trip
     */
    private void sendPlaneTrip(int planeId) {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/game/plane/send/" + planeId;
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
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show());
                    replaceFragment(new ArrivalFragment());
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
     * The method responsible for cancel sending the plane on a trip
     */
    private void cancelSendPlaneTrip(int planeId) {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/game/plane/revert/" + planeId;
        RequestBody body = RequestBody.create("", null);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
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
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show());
                    replaceFragment(new ArrivalFragment());
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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
