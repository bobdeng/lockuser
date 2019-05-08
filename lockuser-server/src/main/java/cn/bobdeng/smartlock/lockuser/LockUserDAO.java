package cn.bobdeng.smartlock.lockuser;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

public interface LockUserDAO extends CrudRepository<LockUserDO, Long> {
    Optional<LockUserDO> findByLockIdAndUserId(String lockId, String userId);

    Stream<LockUserDO> findByLockId(String lockId);

    @Query("delete from LockUserDO as a where a.lock.id=:lockId")
    @Modifying
    @Transactional
    void deleteByLockId(@Param("lockId") String lockId);

    @Query("select a from LockUserDO as a where a.lockId=:lockId and a.level=1000")
    Optional<LockUserDO> findLockOwner(@Param("lockId") String lockId);
}
