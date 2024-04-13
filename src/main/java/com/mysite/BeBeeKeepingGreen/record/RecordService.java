package com.mysite.BeBeeKeepingGreen.record;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;

    public List<HiveRecord> getList(){
        return this.recordRepository.findAll();
    }

    public void create(String content){
        HiveRecord hr = new HiveRecord();
        hr.setContent(content);
        this.recordRepository.save(hr);
    }
}
