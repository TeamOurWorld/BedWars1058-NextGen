package org.earthstudio.bedwars1058nextgen.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.earthstudio.bedwars1058nextgen.NextGen.plugin;

public class MapEditor {

    private volatile Session currentSession;
    private final Object lock = new Object();

    public MapEditor() {
        currentSession = new Session();
    }

    public static class BlockInfo {
        public Location location;
        public Material material;
        public byte extraData;

        public BlockInfo(Location location, Material material, byte extraData) {
            this.location = location;
            this.material = material;
            this.extraData = extraData;
        }

        /**
         * Use with caution, use for give material and extraData only.
         */
        public BlockInfo(Material material, byte extraData) {
            this.location = null;
            this.material = material;
            this.extraData = extraData;
        }
    }

    private static class Session {
        public AtomicInteger runningTask;
        public Queue<BlockInfo> blockQueue;

        public Session() {
            runningTask = new AtomicInteger(0);
            blockQueue = new ConcurrentLinkedQueue<>();
        }

        public void destroy() {
            blockQueue = null;
            runningTask = null;
        }
    }

    public void prepare(BlockInfo block) {
        Session session;
        synchronized (lock) {
            session = currentSession;
        }
        if (session == null)
            return;

        session.runningTask.addAndGet(1);
        session.blockQueue.offer(block);
        session.runningTask.decrementAndGet();
    }

    public void prepare(Collection<BlockInfo> blockList) {
        Session session;
        synchronized (lock) {
            session = currentSession;
        }
        if (session == null)
            return;

        session.runningTask.addAndGet(1);
        session.blockQueue.addAll(blockList);
        session.runningTask.decrementAndGet();
    }

    public void prepareCube(Location location1, Location location2, BlockInfo block) {
        Session session;
        synchronized (lock) {
            session = currentSession;
        }
        if (session == null)
            return;
        if (location1.getWorld() != location2.getWorld())
            return;

        session.runningTask.addAndGet(1);
        prepareCube(location1.getWorld(), location1.getBlockX(), location1.getBlockY(), location1.getBlockZ(), location2.getBlockX(), location2.getBlockY(), location2.getBlockZ(), block);
        session.runningTask.decrementAndGet();
    }

    public void prepareCube(World world, int x1, int y1, int z1, int x2, int y2, int z2, BlockInfo block) {
        Session session;
        synchronized (lock) {
            session = currentSession;
        }
        if (session == null)
            return;

        session.runningTask.addAndGet(1);

        new BukkitRunnable() {
            @Override
            public void run() {
                int minX = min(x1, x2), minY = min(y1, y2), minZ = min(z1, z2);
                int maxX = max(x1, x2), maxY = max(y1, y2), maxZ = max(z1, z2);
                List<BlockInfo> blockList = new ArrayList<>();

                for (int x = minX; x <= maxX; x++) {
                    for (int y = minY; y <= maxY; y++) {
                        for (int z = minZ; z <= maxZ; z++) {
                            blockList.add(new BlockInfo(new Location(world, x, y, z), block.material, block.extraData));
                        }
                    }
                }

                session.blockQueue.addAll(blockList);
                session.runningTask.decrementAndGet();
            }
        }.runTaskAsynchronously(plugin);
    }

    public void placeAll() {
        Session session;
        synchronized (lock) {
            session = currentSession;
            currentSession = new Session();
        }
        if (session == null)
            return;
        if (session.blockQueue.isEmpty())
            return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (session.runningTask.get() != 0)
                    return;

                cancel();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        while (!session.blockQueue.isEmpty()) {
                            BlockInfo block = session.blockQueue.poll();
                            block.location.getBlock().setType(block.material);
                            if (block.extraData != (byte) -1)
                                block.location.getBlock().setData(block.extraData);
                        }
                        session.destroy();
                    }
                }.runTask(plugin);
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 1L);
    }

    public void shutdown() {
        synchronized (lock) {
            currentSession.destroy();
            currentSession = null;
        }
    }

}