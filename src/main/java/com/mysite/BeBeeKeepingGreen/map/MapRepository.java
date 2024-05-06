package com.mysite.BeBeeKeepingGreen.map;

import com.mysite.BeBeeKeepingGreen.record.HiveRecord;
import com.mysite.BeBeeKeepingGreen.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapRepository extends JpaRepository<BeePlant, Integer> {
    List<BeePlant> findAll();

    // isConfirm이 true인 BeePlant 객체들만 가져오는 메서드 추가
    List<BeePlant> findAllByIsConfirmTrue();

    List<BeePlant> findAllByIsConfirmFalse();

    BeePlant findFirstByPlantLocation(String plantLocation);
}