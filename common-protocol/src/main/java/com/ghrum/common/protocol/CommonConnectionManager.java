/**
 * This file is part of Argentum Online.
 *
 * Copyright (c) 2014 Argentum Online <https://github.com/orgs/Argentum-Online/members>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ghrum.common.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Define the server implementation for any number of {@link Connection}
 */
public final class CommonConnectionManager {
    /**
     * The protocol for the session
     */
    private final Protocol protocol;
    /**
     * A list of the connections
     */
    private final ConcurrentMap<CommonConnection, Boolean> registry = new ConcurrentHashMap<>();
    /**
     * The channel group for the connections
     */
    private final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * The boss group for Netty
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * The worker group for Netty
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    /**
     * The bootstrap used to initialize Netty.
     */
    private final ServerBootstrap bootstrap = new ServerBootstrap();

    /**
     * Default constructor for {@link CommonConnectionManager}
     *
     * @param protocol the protocol of the connection
     */
    public CommonConnectionManager(Protocol protocol) {
        this.protocol = protocol;
    }

    /**
     * {@inheritDoc}
     */
    public void pulse() {
        registry.keySet().forEach(CommonConnection::pulse);
    }

    /**
     * Initialise the server connection
     *
     * @param configuration the configuration of the connection
     */
    public void initialise(Protocol configuration) {
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ProtocolChannelInitializer(protocol.getMessageService(), null))    // null -> handler
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    /**
     * Bind the connection to an address and a port
     *
     * @param address the address to bind to
     * @param port    the port to bind to
     * @return true if netty was able to bind the connection
     */
    public boolean bind(String address, int port) {
        try {
            group.add(bootstrap.bind(address, port).awaitUninterruptibly().channel());
        } catch (io.netty.channel.ChannelException ex) {
            // <TODO: Wolftein Use some common logger>
            return false;
        }
        return true;
    }

    /**
     * Stop the primary channel and all connected channels
     *
     * @param reason the reason for disconnection
     */
    public void stop(String reason) {
        // Disconnect all connections that are connected
        // to our channel, sending the kick message
        disconnect(reason);

        // Disconnect all connection that were left behind
        // and stop the Netty workers
        ChannelGroupFuture f = group.close();
        try {
            f.await();
        } catch (InterruptedException ex) {
            // <TODO: Wolftein Use some common logger>
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    /**
     * Disconnect all connections from this group
     *
     * @param reason the reason why the disconnection
     */
    public boolean disconnect(String reason) {
        return false;   // TODO
    }

    /**
     * Adds a new connection to the pool
     *
     * @param connection the connection to add to the pool
     */
    public void add(CommonConnection connection) {
        registry.put(connection, true);
    }

    /**
     * Removes a connection from the pool
     *
     * @param connection the connection to remove from the pool
     */
    public void remove(CommonConnection connection) {
        registry.remove(connection);
    }
}
