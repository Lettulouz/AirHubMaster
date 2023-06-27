package com.airhubmaster.airhubmaster.gameFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.ArrivalAdapter;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.dto.game.PlaneArrivalDto;
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

public class ArrivalFragment extends Fragment {

    private RecyclerView recyclerViewArrival;
    private ArrivalAdapter arrivalAdapter;
    private List<PlaneArrivalDto> planeArrivalDtoList = new ArrayList<>();
    private List<String> categories;
    private RecyclerView recyclerViewHorizontal;
    private CategoryAdapter categoryAdapter;
    UserLocalStore userLocalStore;
    private final Gson gson = new Gson();

    public ArrivalFragment() {
    }

    public static ArrivalFragment newInstance(String param1, String param2) {
        ArrivalFragment fragment = new ArrivalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewArrival = view.findViewById(R.id.recyclerViewArrival);
        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontalArrival);
        recyclerViewArrival.setLayoutManager(new LinearLayoutManager(getContext()));

        getPlaneInTrip();

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


        arrivalAdapter = new ArrivalAdapter(getContext(), planeArrivalDtoList);
        recyclerViewArrival.setAdapter(arrivalAdapter);

        recyclerViewArrival.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int position = rv.getChildAdapterPosition(child);
                    View recyclerView = child.findViewById(R.id.personnel_recycler_view);
                    if (recyclerView != null) {
                        if (action == MotionEvent.ACTION_MOVE) {
                            rv.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_arrival, container, false);
    }

    private void filterPlanesByCategory(String category) {
        if (category == null) {
            arrivalAdapter.updatePlanes(planeArrivalDtoList);
        } else {
            List<PlaneArrivalDto> filteredPlaneArrivalDto = new ArrayList<>();
            for (PlaneArrivalDto planeArrivalDto : planeArrivalDtoList) {
                if (planeArrivalDto.getCategoryName().equals(category)) {
                    filteredPlaneArrivalDto.add(planeArrivalDto);
                }
            }
            arrivalAdapter.updatePlanes(filteredPlaneArrivalDto);
        }
    }

    //==============================================================================================

    /**
     * The method responsible for retrieving plane data assigned to a given user
     */
    private void getPlaneInTrip() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/game/planes/flight";
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
                    Type listType = TypeToken.getParameterized(List.class, PlaneArrivalDto.class).getType();
                    planeArrivalDtoList = gson.fromJson(response.body().string(), listType);
                    getActivity().runOnUiThread(() -> {
                        arrivalAdapter = new ArrivalAdapter(getContext(), planeArrivalDtoList);
                        recyclerViewArrival.setAdapter(arrivalAdapter);
                    });
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
