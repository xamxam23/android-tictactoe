package com.example.template;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	ImageButton[][] buttons = new ImageButton[3][3];
	int[][] data = new int[3][3];
	LinearLayout[] layout_rows = new LinearLayout[3];

	boolean xo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		layout_rows[0] = (LinearLayout) findViewById(R.id.row0);
		layout_rows[1] = (LinearLayout) findViewById(R.id.row1);
		layout_rows[2] = (LinearLayout) findViewById(R.id.row2);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				View v = getLayoutInflater().inflate(R.layout.tile, null);
				buttons[i][j] = (ImageButton) v.findViewById(R.id.b);
				buttons[i][j].setOnClickListener(new MyOnClickListener(i, j));
				layout_rows[i].addView(v);
			}
		}
	}

	public void restart(View v) {
		finish();
		startActivity(new Intent(this, this.getClass()));
	}

	int getXO() {
		return (xo = !xo) ? R.drawable.oo : R.drawable.xx;
	}

	void win(List<int[]> ret, int i, int j) {
		ret.add(new int[] { i, j });
	}

	public boolean win(List<int[]> ret) {
		for (int i = 0; i < 3; i++) {
			if (win(data[0][i], data[1][i], data[2][i])) {
				win(ret, 0, i);
				win(ret, 1, i);
				win(ret, 2, i);
				return true;
			}

			if (win(data[i][0], data[i][1], data[i][2])) {
				win(ret, i, 0);
				win(ret, i, 1);
				win(ret, i, 2);
				return true;
			}
		}

		if (win(data[0][0], data[1][1], data[2][2])) {
			win(ret, 0, 0);
			win(ret, 1, 1);
			win(ret, 2, 2);
			return true;
		}

		if (win(data[0][2], data[1][1], data[2][0])) {
			win(ret, 0, 2);
			win(ret, 1, 1);
			win(ret, 2, 0);
			return true;
		}

		return false;
	}

	public boolean win(int r0, int r1, int r2) {
		return (r0 > 0 && r0 == r1 && r1 == r2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class MyOnClickListener implements OnClickListener {

		int i, j;

		public MyOnClickListener(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void onClick(View v) {
			((ImageButton) v).setImageResource(data[i][j] = getXO());
			v.setEnabled(false);
			List<int[]> ret = new ArrayList<int[]>();
			if (win(ret)) {
				for (int[] t : ret)
					buttons[t[0]][t[1]].setBackgroundResource(R.drawable.win);
			}
		}
	}
}