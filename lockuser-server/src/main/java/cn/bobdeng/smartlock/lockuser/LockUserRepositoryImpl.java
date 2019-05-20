package cn.bobdeng.smartlock.lockuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LockUserRepositoryImpl implements LockUserRepository {
    @Autowired
    LockUserDAO lockUserDAO;

    @PostConstruct
    public void init() {
        LockUserRepositories.lockUserRepository = this;
    }

    @Override
    public LockUser find(LockUserId id) {
        return lockUserDAO.findByLockIdAndUserId(id.lockId.id, id.userId.id)
                .map(LockUserDO::toEntity)
                .orElse(null);
    }

    @Override
    public void save(LockUser lockUser) {
        LockUserDO lockUserDO = LockUserDO.from(lockUser);
        lockUserDAO.findByLockIdAndUserId(lockUser.id.lockId.id, lockUser.id.userId.id)
                .ifPresent(lockUserDO1 -> lockUserDO.setId(lockUserDO1.getId()));
        LockUserDO saved = lockUserDAO.save(lockUserDO);
        lockUser.uniqueId = new UniqueId(saved.getId());

    }

    @Override
    public long currentTime() {
        return System.currentTimeMillis();
    }

    @Override
    public void remove(LockUser lockUser) {
        lockUserDAO.findByLockIdAndUserId(lockUser.id.lockId.id, lockUser.id.userId.id)
                .ifPresent(lockUserDO -> lockUserDAO.delete(lockUserDO));
    }

    @Override
    public List<LockUser> listLockUsersByLock(String lockId) {
        return lockUserDAO.findByLockId(lockId).map(LockUserDO::toEntity).collect(Collectors.toList());
    }

    @Override
    public void deleteByLockId(String lockId) {
        lockUserDAO.deleteByLockId(lockId);
    }

    @Override
    public Optional<LockUser> findOwner(LockId lockId) {
        return lockUserDAO.findLockOwner(lockId.id).map(LockUserDO::toEntity);
    }
}
