package adapters;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import domain.Repository;
import xing.activities.R;

/**
 * Adapter Class to show list item as we want.
 */
public class RepositoryAdapter extends BaseAdapter {

    private List<Repository> gitHubRepositories = new ArrayList<>();

    @Override
    public int getCount() {
        return gitHubRepositories.size();
    }

    @Override
    public Repository getItem(int position) {
        return gitHubRepositories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getItemURL(int position) {
        return getItem(position).getHtmlUrlRepo();
    }

    public String getOwnerURL(int position) {
        return getItem(position).getHtmlUrlOwner();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final GitHubRepoViewHolder viewHolder = (GitHubRepoViewHolder) view.getTag();
        viewHolder.setGitHubRepo(getItem(position));
        return view;
    }

    public void setRepositories(@Nullable List<Repository> repositories) {
        if (repositories == null) {
            return;
        }
        gitHubRepositories.addAll(repositories);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.repo_item_list, parent, false);
        final GitHubRepoViewHolder viewHolder = new GitHubRepoViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    /**
     * Class to implement ViewHolder Pattern.
     */
    private static class GitHubRepoViewHolder {
        private TextView textRepoName;
        private TextView textRepoDescription;
        private TextView textRepoOwnerLogin;
        private RelativeLayout layoutFork;

        public GitHubRepoViewHolder(View view) {
            textRepoName = (TextView) view.findViewById(R.id.text_repo_name);
            textRepoDescription = (TextView) view.findViewById(R.id.text_repo_description);
            textRepoOwnerLogin = (TextView) view.findViewById(R.id.text_repo_owner_login);
            layoutFork = (RelativeLayout) view.findViewById(R.id.relative_layout);
        }

        public void setGitHubRepo(Repository repository) {
            textRepoName.setText(repository.getName());
            textRepoDescription.setText(repository.getDescription());
            textRepoOwnerLogin.setText("Owner: " + repository.getOwnerLogin());
            if (!repository.isFork())
                layoutFork.setBackgroundColor(Color.rgb(69, 196, 76));
        }
    }
}
