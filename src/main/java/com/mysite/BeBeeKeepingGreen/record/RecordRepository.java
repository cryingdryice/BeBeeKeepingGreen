package com.mysite.BeBeeKeepingGreen.record;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<HiveRecord, Integer> {
}
