package cn.bobdeng.smartlock.lockuser;

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
}
