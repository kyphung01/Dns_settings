package vn.com.thaipm56.dnssettings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.InetAddress;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dnsSetting();


    }


    public void dnsSetting(){
        WifiConfiguration wifiConf = null;
        final WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration conf : configuredNetworks){
            if (conf.networkId == connectionInfo.getNetworkId()){
                wifiConf = conf;
                break;
            }
        }
        DhcpInfo info = wifiManager.getDhcpInfo();

        int int_gg1 = 134744072;
        int int_gg2 = 67373064;
        final String  dns1 = "8.8.8.8";
        final String  dns2 = "8.8.4.4";

        if(info.dns1!=int_gg1 && info.dns1!=int_gg2) {
            AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
            b.setTitle("Đổi DNS?");
            b.setMessage("Bạn có muốn thay đổi cấu hình DNS để đọc truyện với chất lượng tốt hơn");

            final WifiConfiguration finalWifiConf = wifiConf;
            b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Setting_DNS myDNS = new Setting_DNS();
                        myDNS.setIpAssignment("STATIC", finalWifiConf); //or "DHCP" for dynamic setting
                        myDNS.setDNS(InetAddress.getByName(dns1), InetAddress.getByName(dns2), finalWifiConf);
                        wifiManager.updateNetwork(finalWifiConf); //apply the setting
                        wifiManager.saveConfiguration(); //Save it
                        wifiManager.disconnect();
                        wifiManager.reconnect();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                }
            });
            b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                @Override

                public void onClick(DialogInterface dialog, int which)

                {
                    dialog.cancel();
                }

            });
            b.create().show();
        }
//
        Log.d("Connections 123123: ", info.toString());
//
//
    }
}
