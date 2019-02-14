package top.nicelee.purehost.vpn.server;

public class NATSession {
	public int RemoteIP;
    public short RemotePort;
    public String RemoteHost;
    public int BytesSent;
    public int PacketSent;
    public long LastNanoTime;
    
    @Override
    public boolean equals(Object obj) {
    	if( obj instanceof NATSession) {
    		NATSession session = (NATSession)obj;
    		if(this.RemoteIP == session.RemoteIP && this.RemotePort == session.RemotePort) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	int hash = RemotePort * 31 + RemoteIP;
    	return hash;
    }
}
