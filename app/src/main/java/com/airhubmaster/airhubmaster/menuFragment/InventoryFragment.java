package com.airhubmaster.airhubmaster.menuFragment;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;
import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER_CSharp;

import android.app.Dialog;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.InsetDrawable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.api.InventoryResponseDto;
import com.airhubmaster.airhubmaster.dto.api.ProfileResponseDto;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InventoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private InventoryAdapter inventoryAdapter;
    private List<InventoryResponseDto> inventoryList;

    UserLocalStore userLocalStore;
    Dialog dialog_send_card;
    ProfileResponseDto profileResponseDto;
    private final Gson gson = new Gson();

    public InventoryFragment() {
    }

    public static InventoryFragment newInstance() {
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize inventory list
        inventoryList = new ArrayList<>();
        inventoryAdapter = new InventoryAdapter(inventoryList, this::onMarketButtonClick);
        recyclerView.setAdapter(inventoryAdapter);

        // Fetch inventory data
        getDataProfile();

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showSendCardDialog(long cardId) {
        dialog_send_card = new Dialog(getActivity());
        dialog_send_card.setContentView(R.layout.dialog_send_card); // Tutaj dodaj swoją layout dla dialogu
        dialog_send_card.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_dialog));
        dialog_send_card.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        InsetDrawable inset = new InsetDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_dialog), 32, 0, 32, 0);
        dialog_send_card.getWindow().setBackgroundDrawable(inset);

        dialog_send_card.setCancelable(true);

        Button confirmButton = dialog_send_card.findViewById(R.id.buttonCardAccept);
        Button cancelButton = dialog_send_card.findViewById(R.id.buttonCardCancel);

        confirmButton.setOnClickListener(v -> {
            sendCardToMarket(cardId);
            dialog_send_card.dismiss();
        });

        cancelButton.setOnClickListener(v -> {
            dialog_send_card.dismiss();
        });

        dialog_send_card.show();
    }

    private void onMarketButtonClick(InventoryResponseDto card) {
        showSendCardDialog(card.getId());
    }

    //==============================================================================================


    /**
     * The method responsible for downloading user inventory data
     */
    private void sendCardToMarket(long cardId) {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String baseUrl = URL_SERVER_CSharp + "api/user/sendMarket";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addQueryParameter("cardId", String.valueOf(cardId));
        String urlWithParams = urlBuilder.build().toString();

        RequestBody emptyBody = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url(urlWithParams)
                .post(emptyBody)
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
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            "Karta została wysłana na rynek.", Toast.LENGTH_SHORT).show());
                    getDataProfile();
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });

    }

    //==============================================================================================

    /**
     * The method responsible for downloading user profile data
     */
    private void getDataProfile() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/account/details";
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
                if (response.isSuccessful()) {
                    profileResponseDto = gson.fromJson(response.body().string(), ProfileResponseDto.class);
                    loadInventoryData(profileResponseDto.getId());
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    //==============================================================================================

    /**
     * The method responsible for downloading user inventory data
     */
    private void loadInventoryData(int userId) {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String baseUrl = URL_SERVER_CSharp + "api/user/inventoryMobile";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addQueryParameter("userId", String.valueOf(userId));
        String urlWithParams = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(urlWithParams)
                .get()
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
                    // Parse the inventory data from the API response
                    InventoryResponseDto[] inventoryData = gson.fromJson(response.body().string(), InventoryResponseDto[].class);

                    // Convert the array into a list and update the adapter
                    inventoryList.clear();
                    inventoryList.addAll(Arrays.asList(inventoryData));
                    getActivity().runOnUiThread(() -> inventoryAdapter.notifyDataSetChanged());
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });

    }

    public static class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

        private List<InventoryResponseDto> inventoryList;
        private InventoryViewHolder.OnMarketClickListener onMarketClickListener;

        public InventoryAdapter(List<InventoryResponseDto> inventoryList, InventoryViewHolder.OnMarketClickListener onMarketClickListener) {
            this.inventoryList = inventoryList;
            this.onMarketClickListener = onMarketClickListener;
        }

        @Override
        public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_inventory, parent, false);
            return new InventoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(InventoryViewHolder holder, int position) {
            InventoryResponseDto card = inventoryList.get(position);
            holder.cardName.setText(card.getCardName());
            holder.cardType.setText(card.getDisplayNameType());

            Map<String, Integer> attributes = card.getAttributes();
            if (attributes != null && !attributes.isEmpty()) {
                List<String> attributeList = new ArrayList<>(attributes.keySet()); // Lista kluczy (attribute names)

                // Ustawienie pierwszych 6 atrybutów w TextView
                int i = 0;
                for (String key : attributeList) {
                    Integer value = attributes.get(key);
                    switch (i) {
                        case 0:
                            holder.attribute1.setText(key + ": " + value);  // Używamy value (Integer) bezpośrednio
                            break;
                        case 1:
                            holder.attribute2.setText(key + ": " + value);
                            break;
                        case 2:
                            holder.attribute3.setText(key + ": " + value);
                            break;
                        case 3:
                            holder.attribute4.setText(key + ": " + value);
                            break;
                        case 4:
                            holder.attribute5.setText(key + ": " + value);
                            break;
                        case 5:
                            holder.attribute6.setText(key + ": " + value);
                            break;
                        default:
                            break;
                    }
                    i++;
                    if (i >= 6) break; // Wyświetlamy tylko pierwsze 6 atrybutów
                }
            }

            holder.inventoryStatsLayout.setVisibility(View.GONE);  // Domyślnie ukryte
            holder.expandButton.setOnClickListener(v -> {
                if (holder.inventoryStatsLayout.getVisibility() == View.GONE) {
                    holder.inventoryStatsLayout.setVisibility(View.VISIBLE);
                    holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
                } else {
                    holder.inventoryStatsLayout.setVisibility(View.GONE);
                    holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
                }
            });

            switch (card.getRarity()) {
                case 0:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_0_bg));
                    holder.cardName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_0_text));
                    break;
                case 1:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_1_bg));
                    holder.cardName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_1_text));
                    break;
                case 2:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_2_bg));
                    holder.cardName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_2_text));
                    break;
                case 3:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_3_bg));
                    holder.cardName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_3_text));
                    break;
                default:
                    holder.cardName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.rarity_0_text));
                    break;
            }

            holder.marketButton.setOnClickListener(v -> onMarketClickListener.onMarketClick(card));
        }

        @Override
        public int getItemCount() {
            return inventoryList.size();
        }

        public static class InventoryViewHolder extends RecyclerView.ViewHolder {
            TextView cardName, cardType, attribute1, attribute2, attribute3, attribute4, attribute5, attribute6;
            Button marketButton;
            ImageButton expandButton;
            ConstraintLayout inventoryStatsLayout;

            public InventoryViewHolder(View itemView) {
                super(itemView);
                cardName = itemView.findViewById(R.id.cardName);
                cardType = itemView.findViewById(R.id.cardType);
                attribute1 = itemView.findViewById(R.id.attribute_1);
                attribute2 = itemView.findViewById(R.id.attribute_2);
                attribute3 = itemView.findViewById(R.id.attribute_3);
                attribute4 = itemView.findViewById(R.id.attribute_4);
                attribute5 = itemView.findViewById(R.id.attribute_5);
                attribute6 = itemView.findViewById(R.id.attribute_6);
                expandButton = itemView.findViewById(R.id.expand_button);
                inventoryStatsLayout = itemView.findViewById(R.id.inventory_stats_layout);
                marketButton = itemView.findViewById(R.id.marketButton);
            }

            public interface OnMarketClickListener {
                void onMarketClick(InventoryResponseDto card);
            }
        }
    }
}
