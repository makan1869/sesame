package ir.serenade.sesame.repository;

import ir.serenade.sesame.domain.entity.Device;
import ir.serenade.sesame.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAll();

    @Modifying
    @Transactional
    @Query("delete from Device u where u.uuid = ?1")
    void deleteByUuid(String uuid);

    Device findDeviceByUserAndUuid(User user, String deviceId);

    Device findDeviceByUuid(String uuid);
}
