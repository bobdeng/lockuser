package cn.bobdeng.smartlock.lockuser;

import java.util.Objects;

class LockUserId {
    String lockId;
    String userId;

    public LockUserId(String lockId, String userId) {
        this.lockId = lockId;
        this.userId = userId;
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
