package cn.bobdeng.smartlock.lockuser;

import java.util.List;
import java.util.Optional;

public class LockUserRepositories {
    public static LockUserRepository lockUserRepository;

    public static LockUser find(LockUserId id) {
        return lockUserRepository.find(id);
    }
    public static void saveLockUser(LockUser lockUser){
        lockUserRepository.save(lockUser);
    }

    public static long currentTime() {
        return lockUserRepository.currentTime();
    }

    public static void remove(LockUser user) {
        lockUserRepository.remove(user);
    }

    public static List<LockUser> listLockUserByLock(String lockId) {
        return lockUserRepository.listLockUsersByLock(lockId);
    }

    public static void deleteByLockId(String lockId) {
        lockUserRepository.deleteByLockId(lockId);
    }
    public static Optional<LockUser> findOwner(LockId lockId){
        return lockUserRepository.findOwner(lockId);
    }
}
