package cn.bobdeng.smartlock.lockuser;

import java.util.HashMap;
import java.util.Map;

public class LockUserRepositoryImpl implements LockUserRepository{
    private Map<LockUserId,LockUser> lockUserMap=new HashMap<>();
    @Override
    public LockUser find(String lockId, String userId) {
        return lockUserMap.get(new LockUserId(lockId,userId));
    }

    @Override
    public void save(LockUser lockUser) {
        lockUserMap.put(lockUser.id,lockUser);
    }
}
