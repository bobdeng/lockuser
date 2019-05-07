package cn.bobdeng.smartlock.lockuser;

import java.util.List;
import java.util.Optional;

public interface LockUserRepository {
    LockUser find(LockUserId id);

    void save(LockUser lockUser);

    long currentTime();

    void remove(LockUser user);

    List<LockUser> listLockUsersByLock(String lockId);

    void deleteByLockId(String lockId);

    Optional<LockUser> findOwner(LockId lockId);
}
