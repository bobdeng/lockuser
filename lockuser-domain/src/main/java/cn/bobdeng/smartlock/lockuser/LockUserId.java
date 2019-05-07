package cn.bobdeng.smartlock.lockuser;

import java.util.Objects;

public class LockUserId {
    LockId lockId;
    UserID userId;

    public LockUserId(String lockId, String userId) {
        this.lockId = new LockId(lockId);
        this.userId = new UserID(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LockUserId that = (LockUserId) o;
        return Objects.equals(lockId, that.lockId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lockId, userId);
    }
}
