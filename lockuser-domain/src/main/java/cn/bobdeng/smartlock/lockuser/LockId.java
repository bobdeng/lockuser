package cn.bobdeng.smartlock.lockuser;

import java.util.Objects;

class LockId {
    String id;

    public LockId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LockId lockId = (LockId) o;
        return Objects.equals(id, lockId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
