package edu.lehigh.cse216.alb323.backend;

//Memcached
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.lang.InterruptedException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Memcached {

    private MemcachedClient mc;

    public Memcached() {
        List<InetSocketAddress> servers =
        AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS").replace(",", " "));
        AuthInfo authInfo =
        AuthInfo.plain(System.getenv("MEMCACHIER_USERNAME"),
                     System.getenv("MEMCACHIER_PASSWORD"));

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);

        // Configure SASL auth for each server
        for(InetSocketAddress server : servers) {
            builder.addAuthInfo(server, authInfo);
        }

        // Use binary protocol
        builder.setCommandFactory(new BinaryCommandFactory());
        // Connection timeout in milliseconds (default: )
        builder.setConnectTimeout(1000);
        // Reconnect to servers (default: true)
        builder.setEnableHealSession(true);
        // Delay until reconnect attempt in milliseconds (default: 2000)
        builder.setHealSessionInterval(2000);

        try {
            mc = builder.build();
        } catch (IOException ioe) {
            System.err.println("Couldn't create a connection to MemCachier: " +
                               ioe.getMessage());
          }
    }

    public boolean checkUser(String session_key, Database dataStore) {
        try {
            System.err.println("This is MC: " + mc);
            int loggedInUser = Integer.parseInt(mc.get(session_key));
            return dataStore.isUser(loggedInUser); 
        } catch (TimeoutException te) {
            System.err.println("Timeout during set or get: " + te.getMessage());
            return false;
        } catch (InterruptedException ie) {
            System.err.println("Interrupt during set or get: " + ie.getMessage());
            return false;
        } catch (MemcachedException me) {
            System.err.println("Memcached error during get or set: " + me.getMessage());
            return false;
        }
    }

    public String addUser(int id) {
        String session_key = new BigInteger(130, new SecureRandom()).toString(32);

        try {
            mc.set(session_key, 0, Integer.toString(id));
            return session_key;
        } catch (TimeoutException te) {
            System.err.println("Timeout during set or get: " + te.getMessage());
            return null;
        } catch (InterruptedException ie) {
            System.err.println("Interrupt during set or get: " + ie.getMessage());
            return null;
        } catch (MemcachedException me) {
            System.err.println("Memcached error during get or set: " + me.getMessage());
            return null;
        }
    }



    
}
