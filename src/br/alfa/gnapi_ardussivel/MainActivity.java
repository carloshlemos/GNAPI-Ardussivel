package br.alfa.gnapi_ardussivel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.persistence.ComandoDataSource;

public class MainActivity extends Activity {

	private static ComandoDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.datasource = new ComandoDataSource(this);

		setContentView(R.layout.activity_main);

		Toast.makeText(getApplicationContext(), "Plugin instalado", Toast.LENGTH_SHORT).show();

		getPackageManager().setComponentEnabledSetting(
				new ComponentName(this, getPackageName() + ".MainActivity-Alias"),
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		finish();
	}

	public static ComandoDataSource getDatasource() {
		return MainActivity.datasource;
	}
}
