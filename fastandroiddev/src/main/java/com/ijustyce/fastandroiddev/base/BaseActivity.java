package com.ijustyce.fastandroiddev.base;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.callback.CallBackManager;
import com.ijustyce.fastandroiddev.manager.AppManager;
import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.net.VolleyUtils;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * base Activity for all Activity
 *
 * @author yc
 */
public abstract class BaseActivity<Bind extends ViewDataBinding> extends AutoLayoutActivity {

    public Activity mContext;
    public Handler handler;

    public String TAG ;
    private static final int SHORT_DELAY = 1000;
    private boolean clicked;
    public Bind contentView;
    public boolean useDataBinding = true;

    /**
     * onCreate .
     */
    @Override
    protected final void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        if (!beforeCreate()){
            finish();
            return;
        }

        int layoutId = getLayoutId();
        if (useDataBinding){
            contentView = DataBindingUtil.setContentView(this, layoutId);
        }else{
            setContentView(layoutId);
        }
        TAG = getClass().getName();
        mContext = this;

        View topMenu = findViewById(R.id.topMenu);
        if (topMenu != null) {
            topMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clicked){
                        toolBarDoubleClick();
                    }else{
                        clicked = true;
                        toolBarClick();
                        handler.postDelayed(resetClick, SHORT_DELAY);
                    }
                }
            });
        }

        View back = findViewById(R.id.back);
        if (back != null){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backPress();
                }
            });
        }

        AppManager.pushActivity(this);

        handler = new Handler();
        doInit();
        afterCreate();
        CallBackManager.onCreate(this);
    }

    public String getExtras(String key){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey(key)) return null;
        return bundle.getString(key);
    }

    public void setTitle(String title){
        TextView label = (TextView) findViewById(R.id.label);
        if (label != null) label.setText(title);
    }

    private Runnable resetClick = new Runnable() {
        @Override
        public void run() {
            clicked = false;
        }
    };

    public void toolBarClick(){

    }

    public void toolBarDoubleClick(){

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    protected void doInit(){}

    @Override
    protected void onStop() {
        super.onStop();
        VolleyUtils.getVolleyRequestQueue(mContext).cancelAll(TAG);
        CallBackManager.onStop(this);
    }

    /**
     *  onCreate 之前调用，返回值决定代码是否向下执行
     * @return  true 代码继续执行 false finish 并且 return
     */
    public boolean beforeCreate(){
        return true;
    }

    public void afterCreate() {
    }

    public void doResume() {
    }

    @Override
    protected final void onResume() {
        super.onResume();
        CallBackManager.onResume(this);
        doResume();
    }

    public abstract int getLayoutId();

    @Override
    protected void onPause() {
        super.onPause();
        if (mContext != null && TAG != null && VolleyUtils
                .getVolleyRequestQueue(mContext) != null){
            VolleyUtils.getVolleyRequestQueue(mContext).cancelAll(TAG);
        }
        CallBackManager.onPause(this);
    }

    public String getTAG() {
        return TAG;
    }

    public String getResString(int resId){

        return getResources().getString(resId);
    }

    @Override
    protected void onDestroy() {

        if (mContext != null && TAG != null && VolleyUtils.getVolleyRequestQueue(mContext) != null){
            VolleyUtils.getVolleyRequestQueue(mContext).cancelAll(TAG);
        }
        CallBackManager.onDestroy(this);
        super.onDestroy();
        AppManager.moveActivity(this);
        if (mContext != null) mContext = null;
        if (httpListener != null) httpListener = null;
        if (TAG != null) TAG = null;
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    public final void newActivity(Intent intent, Bundle bundle){

        if (intent == null){
            return;
        }
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_out, R.anim.push_right_out);
    }

    public final void newActivity(Intent intent) {

        newActivity(intent, null);
    }

    public final void newActivity(Class<? extends Activity> gotoClass) {

        newActivity(new Intent(mContext, gotoClass), null);
    }

    public final void newActivity(Class<? extends Activity> gotoClass, Bundle bundle) {

        newActivity(new Intent(this, gotoClass), bundle);
    }

    public HttpListener httpListener = new HttpListener() {

        @Override
        public void fail(int code, String msg, String taskId) {

            if (mContext == null) {
                return;
            }
            onFailed(code, msg, taskId);
        }

        @Override
        public void success(Object object, String taskId) {

            if (mContext == null) {
                return;
            }
            onSuccess(object, taskId);
        }
    };

    public void onSuccess(Object object, String taskId){

    }

    public void onFailed(int code, String msg, String taskId) {

        //  ToastUtil.show(mContext, msg);
    }

    public void beforeSendRequest(HttpParams params){

    }

    public void backPress() {

        this.finish();
    }

    public String getResText(int id){

        return getResources().getString(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //first saving my state, so the bundle wont be empty.
        //https://code.google.com/p/android/issues/detail?id=19917
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            backPress();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
