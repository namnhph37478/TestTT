package com.example.testtt.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtt.R;

public class SearchActivity extends AppCompatActivity {

    private EditText edtSearch;
    private ImageView btnClear;
    private ProgressBar progressBar;
    private RecyclerView rcvResults;

    private SearchViewModel viewModel;
    private LocationAdapter adapter;

    private String currentQuery = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        // --- Init views ---
        edtSearch   = findViewById(R.id.edtSearch);
        btnClear    = findViewById(R.id.btnClear);
        progressBar = findViewById(R.id.progressBar);
        rcvResults  = findViewById(R.id.rcvResults);
        View root = findViewById(R.id.root);

        // --- RecyclerView ---
        adapter = new LocationAdapter(this);
        rcvResults.setLayoutManager(new LinearLayoutManager(this));
        rcvResults.setAdapter(adapter);

        // --- ViewModel ---
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // --- Observe UI state ---
        viewModel.uiState.observe(this, state -> {
            if (state == null) return;
            switch (state.getStatus()) {
                case IDLE:
                    progressBar.setVisibility(View.GONE);
                    adapter.submitList(null, currentQuery);
                    break;
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    adapter.submitList(state.getResults(), currentQuery);
                    break;
                case EMPTY:
                    progressBar.setVisibility(View.GONE);
                    adapter.submitList(null, currentQuery);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, state.getErrorMessage() != null ? state.getErrorMessage() : "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        // --- Search input listeners ---
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s != null ? s.toString() : "";
                btnClear.setVisibility(currentQuery.isEmpty() ? View.GONE : View.VISIBLE);
                viewModel.onQueryChanged(currentQuery);
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        // Action "Search" trên keyboard
        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean isSearchAction = actionId == EditorInfo.IME_ACTION_SEARCH
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            if (isSearchAction) {
                viewModel.onQueryChanged(edtSearch.getText().toString());
                return true;
            }
            return false;
        });

        // Nút clear
        btnClear.setOnClickListener(v -> {
            edtSearch.setText("");
            adapter.submitList(null, "");
        });

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
