package cn.bobdeng.smartlock.lockuser;

import java.util.List;

public class LockUsers {
    private List<LockUser> lockUsers;

    public LockUsers(List<LockUser> lockUsers) {
        this.lockUsers = lockUsers;
    }

    public static LockUsers loadByLock(String lockId) {
        return new LockUsers(LockUserRepositories.listLockUserByLock(lockId));
    }

    public static void deleteAll(String lockId){
        LockUserRepositories.deleteByLockId(lockId);
    }

    public int size() {
        return lockUsers.size();
    }
}
