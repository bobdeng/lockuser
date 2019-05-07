package cn.bobdeng.smartlock.lockuser;

public interface LockUserRepository {
    LockUser find(String lockId, String userId);

    void save(LockUser lockUser);
}
