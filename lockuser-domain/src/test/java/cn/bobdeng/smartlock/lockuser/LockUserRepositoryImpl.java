package cn.bobdeng.smartlock.lockuser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LockUserRepositoryImpl implements LockUserRepository{
    public static long time;
    private Map<LockUserId,LockUser> lockUserMap=new HashMap<>();
    @Override
    public LockUser find(LockUserId id) {
        return lockUserMap.get(id);
    }

    @Override
    public void save(LockUser lockUser) {
        lockUserMap.put(lockUser.id,lockUser);
    }

    @Override
    public long currentTime() {
        return time;
    }

    @Override
    public void remove(LockUser user) {
        lockUserMap.remove(user.id);
    }

    @Override
    public List<LockUser> listLockUsersByLock(String lockId) {
        return lockUserMap.values()
                .stream()
                .filter(lockUser -> lockUser.id.lockId.id.equals(lockId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByLockId(String lockId) {

    }

    @Override
    public Optional<LockUser> findOwner(LockId lockId) {
        return lockUserMap.values()
                .stream()
                .filter(lockUser -> lockUser.id.lockId.id.equals(lockId))
                .filter(lockUser -> lockUser.is(UserLevel.OWNER))
                .findFirst();
    }
}
