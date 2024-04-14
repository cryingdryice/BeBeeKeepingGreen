package com.mysite.BeBeeKeepingGreen.record;

import com.mysite.BeBeeKeepingGreen.DataNotFoundException;
import com.mysite.BeBeeKeepingGreen.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;

    public List<HiveRecord> getList(SiteUser owner){
        return this.recordRepository.findAllByOwner(owner);
    }

    public void create(String content, SiteUser owner){
        HiveRecord hr = new HiveRecord();
        hr.setContent(content);
        hr.setOwner(owner);
        this.recordRepository.save(hr);
    }

    public void delete(HiveRecord hiveRecord){
        this.recordRepository.delete(hiveRecord);
    }

    public HiveRecord getHiveRecord(Integer id){
        Optional<HiveRecord> record = this.recordRepository.findById(id);
        if(record.isPresent()){
            return record.get();
        }else{
            throw new DataNotFoundException("기록 존재하지 않음");
        }
    }
}
