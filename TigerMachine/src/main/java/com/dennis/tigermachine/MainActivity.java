package com.dennis.tigermachine;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private GridView gridView;
	// 未开始抽奖时的图片
	private int[] imgs1 = { R.drawable.m1, R.drawable.m2, R.drawable.m3,
			R.drawable.m4, R.drawable.m5,
			R.drawable.m6, R.drawable.m7, R.drawable.m8,R.drawable.m9,
			R.drawable.m10,R.drawable.m11,R.drawable.m12,
            R.drawable.m13, R.drawable.m14, R.drawable.m15,
            R.drawable.m16, R.drawable.m17,
            R.drawable.m18, R.drawable.m19, R.drawable.m20,R.drawable.m21,
            R.drawable.m22,R.drawable.m23,R.drawable.m24

	};
	// 开始抽奖时的图片
	private int[] imgs2 = { R.drawable.n1, R.drawable.n2, R.drawable.n3,
			R.drawable.n4,  R.drawable.n5,
			R.drawable.n6, R.drawable.n7, R.drawable.n8,R.drawable.n9,
			R.drawable.n10,R.drawable.n11,R.drawable.n12,
            R.drawable.n13, R.drawable.n14, R.drawable.n15,
            R.drawable.n16,  R.drawable.n17,
            R.drawable.n18, R.drawable.n19, R.drawable.n20,R.drawable.n21,
            R.drawable.n22,R.drawable.n23,R.drawable.n24

	};
	// 对应转盘id的数组
	private int[] array = {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,20,21,22,23};
	// Runnable接口
	private MyRunnable mMyRunnable;
	// 代表从0到8的9个图片序号
	private int num;

	// 开始的时间
	private int startTime;
	// 结束的时间
	private int stopTime;
	private Adpter adpter;
	//开始的按钮 
	private Button start_btn;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
    	initview(); // 初始化
      
		start_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//定义一个随机数最为结束的时间，这里是2到6秒
				stopTime = new Random().nextInt(1000 * 5) + 2000;
				//开启线程 
				mMyRunnable = new MyRunnable();
				new Thread(mMyRunnable).start();
 		}
		});

	}

	private void initview() {
		start_btn = (Button) findViewById(R.id.start_btn);
		gridView = (GridView) findViewById(R.id.gridview); 
	 	
		adpter = new Adpter();
		gridView.setAdapter(adpter);
		
		
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Change(array[num]);  //改变背景色
			num++;                 //依次下一个
			//如果到了最后一个item，则循环
			if (num >= 23) {
				num = 0;
			}

		};
	};

	class MyRunnable implements Runnable {
		@Override
		public void run() {
			handler.sendEmptyMessage(0);  //发送消息
			//如果到达指定的时间,则停止
			if (startTime >= stopTime) {
				handler.removeCallbacks(mMyRunnable);
				//提示中奖消息 
				if (array[num] < 4) {
					String text = array[num] + 1 + "";
					Toast.makeText(context, "恭喜第" + (text)+"位点赞的小伙伴中奖", 0).show();
				} else {
					Toast.makeText(context, "恭喜第" + (array[num]+1)+"位点赞的小伙伴中奖", 0).show();
				}
                
				startTime = 0;
				stopTime = 0;
				return;
			}
			//每隔100毫秒运行一次
			handler.postDelayed(mMyRunnable, 100);
			startTime += 100;
		}
	}

	private void Change(int id) {
		for (int i = 0; i < gridView.getChildCount(); i++) {
			if (i == id) {
 			//如果是选中的，则改变图片为数组2中的图片
			((ImageView) (gridView.getChildAt(i).findViewById(R.id.img))).setBackgroundResource(imgs2[id]);
			} else if (i == 4) {
 		    //如果是到了中间那个，则跳出
				continue;
			} else {
 		    //未选中的就设置为数组1中的图片
				((ImageView) (gridView.getChildAt(i).findViewById(R.id.img))).setBackgroundResource(imgs1[i]);
			}
		}
	}

	class Adpter extends BaseAdapter {

		@Override
		public int getCount() {
			return imgs1.length;
		}

		@Override
		public Object getItem(int position) {
			return imgs1[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
     		View view = View.inflate(MainActivity.this, R.layout.item,null);
			ImageView img = (ImageView) view.findViewById(R.id.img);
        	img.setBackgroundResource(imgs1[position]);
			return view;
		}

	}
}
