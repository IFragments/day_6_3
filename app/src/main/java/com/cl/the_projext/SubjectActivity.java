package com.cl.the_projext;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cl.adapter.SubjectAdapter;
import com.cl.base.BaseMvpActivity;
import com.cl.data.BaseInfo;
import com.cl.data.SpecialtyChooseEntity;
import com.cl.frame.ApiConfig;
import com.cl.frame.constants.ConstantKey;
import com.cl.model.LauchModel;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SubjectActivity extends BaseMvpActivity<LauchModel> {

    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SubjectAdapter mAdapter;

    @Override
    public LauchModel setModel() {
        return new LauchModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_subject;
    }

    @Override
    public void setUpView() {
        titleContent.setText(getString(R.string.select_subject));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubjectAdapter(mListData,this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        if (SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST) != null) {
            mListData.addAll(SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST));
            mAdapter.notifyDataSetChanged();
        } else
            mPresenter.getData(ApiConfig.SUBJECT);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.SUBJECT:
                BaseInfo<List<SpecialtyChooseEntity>> info = (BaseInfo<List<SpecialtyChooseEntity>>) pD[0];
                mListData.addAll(info.result);
                mAdapter.notifyDataSetChanged();
                SharedPrefrenceUtils.putSerializableList(this,ConstantKey.SUBJECT_LIST,mListData);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.putObject(this,ConstantKey.SUBJECT_SELECT,mApplication.getSelectedInfo());
    }

    @OnClick(R.id.back_image)
    public void onViewClicked() {
        finish();
    }
}
