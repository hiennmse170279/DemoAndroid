package com.example.demoandroidapi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewAdapterForBook.OnItemClickListener {
    RecyclerView recyclerViewForBook;
    RecyclerViewAdapterForBook adapterForBook;
    List<Book> listBooks;
    ProgressDialog progressDialog;
    EditText etName, etDescription, etPrice;
    CheckBox cbIsSold;
    Button btnAdd, btnUpdate, btnDelete;
    int index = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding View
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        cbIsSold = findViewById(R.id.cbIsSold);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerViewForBook = findViewById(R.id.recyclerView);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        //Connect with Database
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //Initialize Data for adapter
        setupDataForListBooks();
    }


    public void showMessage(String message) {
        // Update UI after connection attempt
        runOnUiThread(() -> {
            progressDialog.dismiss(); // Hide loading dialog
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        // Add Book
        if (id == R.id.btnAdd) {
            addBookToDatabase();
            // UpdateBook
        } else if (id == R.id.btnUpdate) {
            updateBookToDatabase();
            // Delete Book
        } else {
            deleteBookFromDatabase();
        }
    }

    // CRUD to Database functions
    // Get All Books
    public void setupDataForListBooks() {
        progressDialog.setMessage("Connecting to SQL Server...");
        progressDialog.show(); // Show loading dialog
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
                MyApiEndpointInterface.myRetrofitApi.getBooks().enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        runOnUiThread(() -> {
                            listBooks = response.body();
                            adapterForBook = new RecyclerViewAdapterForBook(MainActivity.this, listBooks);
                            recyclerViewForBook.setAdapter(adapterForBook);
                            recyclerViewForBook.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            showMessage("Get Data successfully");
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        Log.d("TAGTAGTAG", "onFailure: " + t.toString());
                        listBooks = new ArrayList<>();
                        runOnUiThread(() -> {
                            adapterForBook = new RecyclerViewAdapterForBook(MainActivity.this, listBooks);
                            recyclerViewForBook.setAdapter(adapterForBook);
                            recyclerViewForBook.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            showMessage("Connection Failed: " + t.toString());
                        });
                    }
                });
        });
    }

    // Add Book
    public void addBookToDatabase() {
        progressDialog.setMessage("Connecting to SQL Server...");
        progressDialog.show(); // Show loading dialog
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                if (name.isEmpty() || description.isEmpty()) {
                    showMessage("Please input all fields of the book!");
                    return;
                }
                boolean isSold = cbIsSold.isChecked();

                int price = Integer.parseInt(etPrice.getText().toString());
                if (price <= 0) {
                    showMessage("Please input price higher than 0!");
                    return;
                }
                BookRequestDTO bookRequest = new BookRequestDTO(name, description, price, isSold);
                MyApiEndpointInterface.myRetrofitApi.addBook(bookRequest).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Book bookAdded = new Book(response.body(), bookRequest.getName(), bookRequest.getDescription(), bookRequest.getPrice(), bookRequest.isSold());
                        listBooks.add(bookAdded);
                        runOnUiThread(() -> {
                            adapterForBook.notifyDataSetChanged();
                            etName.setText("");
                            etDescription.setText("");
                            etPrice.setText("");
                            cbIsSold.setChecked(false);
                            index = -1;
                            showMessage("Add successfully");
                        });
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        showMessage("Connection Failed: " + t.toString());
                    }
                });
            } catch (NumberFormatException e) {
                showMessage("Please input an Integer Price!");
            } catch (Exception e) {
                showMessage("Error: " + e.getMessage());
            }
        });

    }

    // Update Book
    public void updateBookToDatabase() {
        if (index == -1) {
            showMessage("Please choose a book to update!");
            return;
        }
        progressDialog.setMessage("Connecting to SQL Server...");
        progressDialog.show(); // Show loading dialog
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                int idBookUpdated = listBooks.get(index).getId();
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                if (name.isEmpty() || description.isEmpty()) {
                    showMessage("Please input all fields of the book!");
                    return;
                }
                boolean isSold = cbIsSold.isChecked();
                int price = Integer.parseInt(etPrice.getText().toString());
                if (price <= 0) {
                    showMessage("Please input price higher than 0!");
                    return;
                }
                BookRequestDTO bookRequest = new BookRequestDTO(name, description, price, isSold);
                MyApiEndpointInterface.myRetrofitApi.updateBook(idBookUpdated, bookRequest).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Book bookUpdated = new Book(idBookUpdated, bookRequest.getName(), bookRequest.getDescription(), bookRequest.getPrice(), bookRequest.isSold());
                        listBooks.set(index, bookUpdated);
                        runOnUiThread(() -> {
                            adapterForBook.notifyDataSetChanged();
                            showMessage("Update successfully");
                        });
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        showMessage("Connection Failed: " + t.toString());
                    }
                });
            } catch (NumberFormatException e) {
                showMessage("Please input an Integer Price!");
            } catch (Exception e) {
                showMessage("Error: " + e.getMessage());
            }
        });
    }

    // Delete Book
    public void deleteBookFromDatabase() {
        if (index == -1) {
            showMessage("Please choose a book to delete!");
            return;
        }
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        progressDialog.setMessage("Connecting to SQL Server...");
                        progressDialog.show(); // Show loading dialog
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(() -> {
                                MyApiEndpointInterface.myRetrofitApi.deleteBook(listBooks.get(index).getId()).enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        listBooks.remove(index);
                                        index = -1;
                                        runOnUiThread(() -> {
                                            adapterForBook.notifyDataSetChanged();
                                            etName.setText("");
                                            etDescription.setText("");
                                            etPrice.setText("");
                                            cbIsSold.setChecked(false);
                                            showMessage("Delete successfully");
                                        });
                                    }
                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        showMessage("Connection Failed: " + t.toString());
                                    }
                                });
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure to delete this book?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener);
        builder.show();
    }

    //Handle Item click in RecyclerView
    @Override
    public void onItemClick(int position) {
        index = position;
        etName.setText(listBooks.get(position).getName());
        etDescription.setText(listBooks.get(position).getDescription());
        etPrice.setText("" + listBooks.get(position).getPrice());
        if (listBooks.get(position).isSold()) {
            cbIsSold.setChecked(true);
        } else {
            cbIsSold.setChecked(false);
        }
    }
}