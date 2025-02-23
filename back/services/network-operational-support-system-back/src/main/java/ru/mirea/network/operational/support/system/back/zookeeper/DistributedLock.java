package ru.mirea.network.operational.support.system.back.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.data.Stat;
import ru.mirea.network.operational.support.system.back.exception.LockExceptionException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class DistributedLock implements AutoCloseable {
    private final InterProcessMutex lock;

    public DistributedLock(CuratorFramework client, String lockPath, Duration waitTime) throws Exception {
        lock = new InterProcessMutex(client, lockPath);
        if (!lock.acquire(0, TimeUnit.SECONDS)) {
            Stat stat = client.checkExists().forPath(lockPath);

            LocalDateTime creationTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(stat.getCtime()), ZoneId.systemDefault());

            if (LocalDateTime.now().isAfter(creationTime.plus(waitTime))) {
                client.delete().deletingChildrenIfNeeded().forPath(lockPath);
                if (!lock.acquire(0, TimeUnit.SECONDS)) {
                    throw new LockExceptionException("Задача заблокирована другим процессом");
                }
            } else {
                throw new LockExceptionException("Задача заблокирована другим процессом");
            }
        }
    }

    @Override
    public void close() {
        try {
            lock.release();
        } catch (Exception ignored) {
        }
    }
}