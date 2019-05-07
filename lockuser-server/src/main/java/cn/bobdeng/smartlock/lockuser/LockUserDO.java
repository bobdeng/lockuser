package cn.bobdeng.smartlock.lock;

import cn.bobdeng.smartlock.domain.lock.LockUser;
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
    private long id;
    @Column(length = 36)
    private String userId;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String lockName;
    private int level;
    private Long start;
    private Long end;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lock_id",foreignKey = @javax.persistence.ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
    private LockDO lock;

    public static LockUserDO from(LockUser user) {
        return LockUserDO.builder()
                .lock(LockDO.fromEntity(user.getLock()))
                .start(user.getStart())
                .end(user.getEnd())
                .level(user.getLevel())
                .userId(user.getUserId())
                .id(user.getId())
                .name(user.getName())
                .lockName(user.getLockName())
                .build();
    }

    public LockUser toEntity() {
        return LockUser.builder()
                .start(this.getStart())
                .lockName(this.getLockName())
                .end(this.getEnd())
                .id(this.getId())
                .lock(lock.toEntity())
                .level(this.getLevel())
                .userId(this.getUserId())
                .name(this.getName())
                .build();
    }
}
