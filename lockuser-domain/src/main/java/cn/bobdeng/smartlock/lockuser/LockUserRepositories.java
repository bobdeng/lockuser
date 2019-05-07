package cn.bobdeng.smartlock.lockuser;

public class LockUserRepositories {
    public static LockUserRepository lockUserRepository;

    public static LockUser find(String lockId, String userId) {
        return lockUserRepository.find(lockId,userId);
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
}
