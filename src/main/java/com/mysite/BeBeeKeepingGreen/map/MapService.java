package com.mysite.BeBeeKeepingGreen.map;

import com.mysite.BeBeeKeepingGreen.record.HiveRecord;
import com.mysite.BeBeeKeepingGreen.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MapService {
    private final MapRepository mapRepository;

    public List<BeePlant> getConfirmedList(){
        return this.mapRepository.findAllByIsConfirmTrue();
    }

    public List<BeePlant> getWaitingList(){
        return this.mapRepository.findAllByIsConfirmFalse();
    }

    public BeePlant getPlant(int ID){
//        System.out.println(ackPlant);
//        return this.mapRepository.findFirstByPlantLocation(ackPlant);
//        System.out.println("getPlant"+x+y);
//        return this.mapRepository.findFirstByXAndY(x, y);
        System.out.println(ID);
        return this.mapRepository.findById(ID);
    }

    public void ackPlant(BeePlant bp){
        if (bp == null) {
            // 전달된 BeePlant 객체가 null인 경우 예외 처리 또는 로깅
            throw new IllegalArgumentException("전달된 BeePlant 객체가 null입니다.");
        }

        System.out.println("확인됨!!");
        bp.setConfirm(true);

        this.mapRepository.save(bp);
    }

    public void require(String plantLocation, String x, String y){
        BeePlant bp = new BeePlant();
        bp.setPlantLocation(plantLocation);
        bp.setX(x);
        bp.setY(y);
        bp.setConfirm(false);

        this.mapRepository.save(bp);
    }
}
