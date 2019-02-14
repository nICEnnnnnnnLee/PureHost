package top.nicelee.purehost.vpn.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.nicelee.purehost.vpn.LocalVpnService;
import top.nicelee.purehost.vpn.ip.CommonMethods;

public class TwinsChannel {
	
	final static Pattern patternURL = Pattern.compile("^/([^:]+):(.*)$");
	Selector selector;

	SocketChannel localSc;
	SocketChannel remoteSc;
	
	public TwinsChannel(SocketChannel localSc, Selector selector) {
		this.localSc = localSc;
		this.selector = selector;
	}
	
	SocketChannel connectRemoteSc() throws IOException {
		if (remoteSc != null) {
			return remoteSc;
		}

		remoteSc = SocketChannel.open();
		remoteSc.configureBlocking(false);
        //System.out.println("VpnService Socket Protect成功: "+ LocalVpnService.Instance.protect(remoteSc.socket()));
        LocalVpnService.Instance.protect(remoteSc.socket());

		Matcher matcher = patternURL.matcher(localSc.getRemoteAddress().toString());
		matcher.find();
		NATSession session = NATSessionManager.getSession((short)Integer.parseInt(matcher.group(2)));
		//System.out.println("VpnService Socket Session: key Port: "+ (short)Integer.parseInt(matcher.group(2)));
//		if(session != null){
//
//			System.out.println("VpnService Socket Session: ip : "+ CommonMethods.ipIntToString(session.RemoteIP));
//			System.out.println("VpnService Socket Session: port : "+ (int)(session.RemotePort));
//		}
		//System.out.println("VpnService Socket RemoteIP:  192.168.1.103");
		//System.out.println("VpnService Socket RemotePort:  7778");
		//建立远程连接
		InetSocketAddress soAddr = new InetSocketAddress(CommonMethods.ipIntToString(session.RemoteIP), (session.RemotePort));
		//System.out.println("VpnService Socket 正在连接中... "+ soAddr.toString());
		remoteSc.connect(soAddr);
		remoteSc.register(selector, SelectionKey.OP_READ, this);
		//System.out.println("VpnService Socket 已经注册... "+ soAddr.toString());
		return remoteSc;
	}
}
