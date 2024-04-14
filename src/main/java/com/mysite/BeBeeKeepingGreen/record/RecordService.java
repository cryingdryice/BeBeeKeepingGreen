package com.mysite.BeBeeKeepingGreen.record;

import com.mysite.BeBeeKeepingGreen.user.SiteUser;
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

    public void create(String content, SiteUser owner){
        HiveRecord hr = new HiveRecord();
        hr.setContent(content);
        hr.setOwner(owner);
        this.recordRepository.save(hr);
    }
}
