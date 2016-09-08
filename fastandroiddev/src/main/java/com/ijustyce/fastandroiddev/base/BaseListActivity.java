package com.ijustyce.fastandroiddev.base;

import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.irecyclerview.BindingInfo;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.fastandroiddev.irecyclerview.IRecyclerView;
import com.ijustyce.fastandroiddev.irecyclerview.PullToRefreshListener;
import com.ijustyce.fastandroiddev.net.IResponseList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2015/12/11 0011. 分页功能的activity
 */
public abstract class BaseListActivity<T> extends BaseActivity {

    public IRecyclerView mIRecyclerView;
    public View noData;

    public IAdapter<T> adapter;
    public List<T> data;

    public int pageNo = 1;
    public boolean showNoData = true;

    @Override
    public final void doResume() {

        if (handler == null) {

            init();
        }

        //  刷新数据
        if (mIRecyclerView != null) {
            mIRecyclerView.onRefresh();
        }
    }

    @Override
    protected final void doInit() {
        init();
    }

    @Override
    public void toolBarDoubleClick(){
        scrollToPosition(0);
    }

    @Override
    public int getLayoutId() {
        useDataBinding = false;
        return R.layout.fastandroiddev_activity_list_common;
    }

    public int getRecyclerViewId(){return R.id.list;}
    public int getNoDataId(){return R.id.noData;}

    public final void init() {

        mIRecyclerView = (IRecyclerView) findViewById(getRecyclerViewId());
        noData = findViewById(getNoDataId());

        if (noData != null && clickNoDataToRefresh()){
            noData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIRecyclerView.onRefresh();
                }
            });
        }

        handler = new Handler();
        pageNo = 1;
        mIRecyclerView.setHasMore(true);
        mIRecyclerView.setPullToRefreshListener(refreshListener);
        data = new ArrayList<>();
        BindingInfo bindingInfos = getBindingInfos();
        if (bindingInfos != null){
            adapter = new IAdapter<>(mContext, data, bindingInfos);
            mIRecyclerView.setAdapter(adapter);
        }else{
            ILog.e("===BaseListActivity===", "getBindingInfos() return null ... ");
        }
    }

    public abstract BindingInfo getBindingInfos();

    public abstract Class getType();

    public T getById(int position){

        if (position < 0 || position >= data.size()){
            return null;
        }
        return data.get(position);
    }

    @Override
    public void onFailed(int code, String msg, String taskId) {

        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        if (handler == null) return;
        handler.post(hasNoData);
    }

    @Override
    public final void onSuccess(Object result, String taskId) {
        if (handler == null) return;
        if (data == null) {
            handler.post(hasNoData);
            return;
        }
        if (pageNo == 1){
            data.clear();
        }

        beforeSuccess(result, taskId);
        if (result instanceof IResponseList){

            List<T> objectsList = ((IResponseList<T>)result).getData();
            if (objectsList != null && !objectsList.isEmpty()){
                data.addAll(objectsList);
                handler.post(newData);
            }else{
                handler.post(hasNoData);
            }
        }
        afterSuccess(result, taskId);
    }

    public void afterSuccess(Object object, String taskId){

    }

    public void beforeSuccess(Object object, String taskId){}

    private PullToRefreshListener refreshListener = new PullToRefreshListener() {
        @Override
        public void onRefresh() {

            if (noData == null) {  //  发生异常
                handler.post(hasNoData);
                return;
            }
            noData.setVisibility(View.GONE);  //  隐藏没有数据时，显示的view

            pageNo = 1;
            mIRecyclerView.setHasMore(true);
            if (!getMoreData()) {
                handler.post(hasNoData);
            }
        }

        @Override
        public void onLoadMore() {

            pageNo++;
            getMoreData();
        }
    };

    public boolean clickNoDataToRefresh(){
        return true;
    }

    //  获取更多数据
    public abstract boolean getMoreData();

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter = null;
        }
        if (data != null) {
            data = null;
        }if (mIRecyclerView != null){
            mIRecyclerView = null;
        }if (noData != null){
            noData = null;
        }
    }

    public final void setNoDataImg(int resId){

        if (noData == null) return;
        ImageView imgView = (ImageView) noData.findViewById(R.id.noDataImg);
        if (imgView != null)
            imgView.setImageBitmap(CommonTool.drawableToBitmap(getResources().getDrawable(resId)));
    }

    public final void setNoDataMsg(int resId){

        setNoDataMsg(getResString(resId));
    }

    public final void setNoDataMsg(String msg){

        if (noData == null) return;
        TextView msgView = (TextView) noData.findViewById(R.id.noDataMsg);
        if (msgView != null) msgView.setText(msg);
    }

    public final Runnable newData = new Runnable() {
        @Override
        public void run() {

            mIRecyclerView.onLoadEnd();
            mIRecyclerView.onRefreshEnd();

            if ((data == null || !data.isEmpty()) && noData != null) {
                noData.setVisibility(View.GONE);
            }
            if (mContext != null && adapter != null && data != null) {
                mIRecyclerView.notifyDataSetChanged();
            }
        }
    };

    /**
     * 滚动到某个item所在的位置
     * @param position item的位置
     */
    public final void scrollToPosition(int position){

        if (mIRecyclerView == null || mIRecyclerView.getRecyclerView() == null
                || data == null || data.isEmpty()) return;
        mIRecyclerView.getRecyclerView().smoothScrollToPosition(position);
    }

    public final Runnable hasNoData = new Runnable() {
        @Override
        public void run() {
            mIRecyclerView.onLoadEnd();
            mIRecyclerView.onRefreshEnd();
            mIRecyclerView.setHasMore(false);

            if (adapter != null){
                mIRecyclerView.notifyDataSetChanged();
            }

            if (showNoData && adapter != null && adapter.getItemCount() < 2 && noData != null) {
                noData.setVisibility(View.VISIBLE);
            }
        }
    };
}
