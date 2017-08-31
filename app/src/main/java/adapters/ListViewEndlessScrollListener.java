package adapters;

import android.widget.AbsListView;

public abstract class ListViewEndlessScrollListener implements AbsListView.OnScrollListener {

    private int mVisibleThreshold = 3;

    private int mCurrentPage = 0;

    private int previousTotalItemCount = 0;

    private boolean mLoading = true;

    private int mStartingPageIndex = 0;

    public ListViewEndlessScrollListener() {
    }

    public ListViewEndlessScrollListener(int visibleThreshold) {
        this.mVisibleThreshold = visibleThreshold;
    }

    public ListViewEndlessScrollListener(int visibleThreshold, int startingPageIndex) {
        this.mVisibleThreshold = visibleThreshold;
        this.mStartingPageIndex = startingPageIndex;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount < previousTotalItemCount) {
            this.mCurrentPage = this.mStartingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.mLoading = true;
            }
        }

        if (mLoading && (totalItemCount > previousTotalItemCount)) {
            mLoading = false;
            previousTotalItemCount = totalItemCount;
            mCurrentPage++;
        }

        if (!mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold)) {
            mLoading = onLoadMore(mCurrentPage + 1, totalItemCount);
        }
    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);
}
