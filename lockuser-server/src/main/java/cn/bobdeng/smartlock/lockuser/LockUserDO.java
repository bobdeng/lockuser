package cn.bobdeng.smartlock.lockuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_lock_user")
public class LockUserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 36)
    private String userId;
    @Column(length = 36)
    private String lockId;
    @Column(length = 20)
    private String name;
    private int level;
    private Long start;
    private Long end;

    public static LockUserDO from(LockUser user) {
        return LockUserDO.builder()
                .start(user.assign.privilege.timeRange.start)
                .end(user.assign.privilege.timeRange.end)
                .level(user.assign.privilege.level.level.value)
                .userId(user.id.userId.id)
                .name(user.assign.userName.name)
                .build();
    }

    public LockUser toEntity() {
        LockUserId id = new LockUserId(lockId, userId);
        UserName userName = new UserName(name);
        UserLevel userLevel = UserLevel.of(level);
        TimeRange timeRange = new TimeRange(start, end);
        LockPrivilege lockPrivilege = LockPrivilege.newPrivilege(userLevel, timeRange);
        return LockUserFactory.newInstance(id, userName, lockPrivilege);
    }
}
