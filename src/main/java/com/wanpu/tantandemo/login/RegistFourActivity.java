package com.wanpu.tantandemo.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wanpu.tantandemo.R;
import com.wanpu.tantandemo.databinding.ActivityRegistFourBinding;
import com.wanpu.tantandemo.util.ImageUtil;

import java.io.IOException;

public class RegistFourActivity extends AppCompatActivity {
    public static final int TAKE_GALLERY = 0x52;
    public static final int Reuqest_Code_Permisser = 0x1001;
    ActivityRegistFourBinding binding;
    private Uri uri;
    private Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist_four);
        binding.setRegist(new RegistFourViewModel(binding));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_GALLERY:
                    if (data == null) {
                        return;
                    }
                     uri = data.getData();
                    if (uri!=null)
                        new FindFaceTask().execute();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Reuqest_Code_Permisser:
                if (grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限请求成功的操作
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    intent.setType("image/*");
                    startActivityForResult(intent, RegistFourActivity.TAKE_GALLERY);
                } else {
                    // 权限请求失败的操作
                    Toast.makeText(this,"未授权", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /**
     * 检测图像中的人脸需要一些时间，所以放到AsyncTask中去执行
     * @author yubo
     *
     */
    private class FindFaceTask extends AsyncTask<Void, Void,  Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            //最关键的就是下面三句代码
            int realFaceNum = 0;
            int MAX_FACE_NUMBER = 5;
            try {
                bm = ImageUtil.getBitmapFormUri(RegistFourActivity.this, uri);
                binding.setImg(bm);
                bm = bm.copy(Bitmap.Config.RGB_565, true);
                FaceDetector faceDetector = new FaceDetector(bm.getWidth(), bm.getHeight(), MAX_FACE_NUMBER);
                FaceDetector.Face[] faces = new FaceDetector.Face[MAX_FACE_NUMBER];
                realFaceNum = faceDetector.findFaces(bm, faces);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return realFaceNum;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result!=1)
                showIconErrorDialog(result);
        }
    }


    private void showIconErrorDialog(int faceNumber){
        final Dialog iconErrorDialog = new Dialog(this,R.style.activity_dialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_icon_error_layout,null);
        iconErrorDialog.setContentView(view);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.personImg);
        simpleDraweeView.setImageBitmap(bm);
        view.findViewById(R.id.btnIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconErrorDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setType("image/*");
                startActivityForResult(intent, RegistFourActivity.TAKE_GALLERY);
            }
        });
        if (faceNumber>1) {
            TextView tip = (TextView) view.findViewById(R.id.tvIconTip);
            tip.setText("个人头像");
        }
        iconErrorDialog.show();
    }

}
