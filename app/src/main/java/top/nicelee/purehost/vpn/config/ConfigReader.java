package top.nicelee.purehost.vpn.config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.nicelee.purehost.MainActivity;


public class ConfigReader {
	final static Pattern patternConfig = Pattern.compile("^[ ]*([0-9]+.[0-9]+.[0-9]+.[0-9]+) ([^ ]+.*$)");
	final static Pattern patternDNS = Pattern.compile(":[ ]*\\[([0-9]+.[0-9]+.[0-9]+.[0-9]+)\\]");
    // 记录本地host解析表
    public static HashMap<String, String>  domainIpMap = new HashMap<>();
	public static List<String> dnsList = new ArrayList<String>();

	public static List<String> initDNS(Context context){
		ConnectivityManager mConnectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();

		int netType = info.getType();
		int netSubtype = info.getSubtype();

		if (netType == ConnectivityManager.TYPE_WIFI) {  //WIFI
			System.out.println("当前通过Wifi 上网");
			getWifiNetInfo(context);
		} else if (netType == ConnectivityManager.TYPE_MOBILE) {   //MOBILE
			System.out.println("当前通过流量 上网");
			getLocalDNS();
		}
		if(dnsList.isEmpty()){
			dnsList.add("114.114.114.114");
		}
		for(String dns : dnsList){
			System.out.println("DNS 服务器: " + dns);
		}
		return dnsList;

	}
	public static List<String> getLocalDNS(){
		Process cmdProcess = null;
		BufferedReader reader = null;
		dnsList.clear();
		try {
			String[] cmd = new String[]{"sh","-c","getprop | grep dns"};
			cmdProcess = Runtime.getRuntime().exec(cmd);
			reader = new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));
			String dnsIP = reader.readLine();
			while(dnsIP != null){
				Matcher matcher = patternDNS.matcher(dnsIP);
				if(matcher.find()){
					dnsList.add(matcher.group(1));
				}
				dnsIP = reader.readLine();
            }
			return dnsList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
			}
			cmdProcess.destroy();
		}
	}
	public static void getWifiNetInfo(Context context){
		dnsList.clear();
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if(wifi  != null){
			DhcpInfo info = wifi.getDhcpInfo();
			String dns = intToIp(info.dns1);
			if(!"0.0.0.0".equals(dns)){
				dnsList.add(dns);
			}
		}
	}

	public static String intToIp(int addr) {
		return  ((addr & 0xFF) + "." +
				((addr >>>= 8) & 0xFF) + "." +
				((addr >>>= 8) & 0xFF) + "." +
				((addr >>>= 8) & 0xFF));
	}
	public static void writeHost(EditText textHost) {
		BufferedWriter buWriter = null;
		try {
			File folder = new File(MainActivity.path);
			File file = new File(folder, "host");
			//System.out.println(MainActivity.path);
			buWriter = new BufferedWriter(new FileWriter(file, false));
            buWriter.write(textHost.getText().toString());
			buWriter.flush();
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				buWriter.close();
			} catch (Exception e) {
			}
		}
	}

	public static void initHost() {
		BufferedWriter buWriter = null;
		try {
			File folder = new File(MainActivity.path);
			File file = new File(folder, "host");
			//System.out.println(MainActivity.path);
			buWriter = new BufferedWriter(new FileWriter(file, false));
			buWriter.write("#127.0.0.1 localhost\r\n");
			buWriter.write("47.105.98.249 nicelee.top\r\n");
			buWriter.flush();
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				buWriter.close();
			} catch (Exception e) {
			}
		}
	}
	public static void readHost(EditText textHost) {
		// 先初始化默认值
		BufferedReader buReader = null;
		System.out.println("----Config init begin...----");
		try {
			File folder = new File(MainActivity.path);
			File file = new File(folder, "host");
			buReader = new BufferedReader(new FileReader(file));
            textHost.setText("");
			String config;
			while ((config = buReader.readLine()) != null) {
				textHost.append(config);
				textHost.append("\r\n");
				//System.out.println(config);
				Matcher matcher = patternConfig.matcher(config);
				if (matcher.find()) {
					//System.setProperty(matcher.group(1), matcher.group(2).trim());
					domainIpMap.put(matcher.group(2), matcher.group(1).trim());
					System.out.printf("  key-->value:  %s --> %s\r\n", matcher.group(2), matcher.group(1));
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			try {
				buReader.close();
			} catch (Exception e) {
			}
		}
		System.out.println("----Config ini end...----");
	}
}
