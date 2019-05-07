package cn.bobdeng.smartlock.lockuser;

public interface LockUserRepository {
    LockUser find(LockUserId id);

    void save(LockUser lockUser);

    long currentTime();

    void remove(LockUser user);
}
