package com.mysite.BeBeeKeepingGreen.record;

import com.mysite.BeBeeKeepingGreen.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<HiveRecord, Integer> {
    List<HiveRecord> findAllByOwner(SiteUser owner);
}
