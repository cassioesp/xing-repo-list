package xing.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import adapters.ListViewEndlessScrollListener;
import adapters.RepositoryAdapter;
import domain.Repository;
import rest.RepoClient;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MainActivity Class.
 */
public class MainActivity extends AppCompatActivity {

    private ListView listOfRepos;

    private RepositoryAdapter adapter;

    private final int ITEMS_PER_PAGE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfRepos = (ListView) findViewById(R.id.repo_list);

        adapter = new RepositoryAdapter();

        listOfRepos.setAdapter(adapter);
        listOfRepos.setOnScrollListener(new ListViewEndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                getRequest(getString(R.string.user_name), page, ITEMS_PER_PAGE);
                return true;
            }
        });

        listOfRepos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                RepositoryAdapter adapter = (RepositoryAdapter) listOfRepos.getAdapter();
                Uri uri = Uri.parse(adapter.getItemURL(position));
                Uri ownerUri = uri.parse(adapter.getOwnerURL(position));
                showDialog(MainActivity.this, uri, ownerUri);
                return true;
            }
        });
        getRequest(getString(R.string.user_name), 1, ITEMS_PER_PAGE);
    }

    private void showDialog(final Context context, final Uri uri, final Uri ownerUri) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Go to GitHub Website ")
                .setMessage("Do you want access the GitHub Repo/User page?")
                .setPositiveButton("Go to Repository page", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                uri);
                        startActivity(browserIntent);
                    }
                })
                .setNegativeButton("Go to user page", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                ownerUri);
                        startActivity(browserIntent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void getRequest(String username, int page, int itemsPerPage) {
        RepoClient.getInstance()
                .getRepositories(username, page, itemsPerPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        adapter.setRepositories(repositories);
                    }
                });
    }

}
